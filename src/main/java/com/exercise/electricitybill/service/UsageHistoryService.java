package com.exercise.electricitybill.service;

import com.exercise.electricitybill.dto.request.UsageHistoryRequest;
import com.exercise.electricitybill.dto.response.UsageHistoryResponse;
import com.exercise.electricitybill.dto.response.UserResponse;
import com.exercise.electricitybill.entity.Config;
import com.exercise.electricitybill.entity.UsageHistory;
import com.exercise.electricitybill.entity.User;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.mapper.UsageHistoryMapper;
import com.exercise.electricitybill.repository.ConfigRepository;
import com.exercise.electricitybill.repository.UsageHistoryRepository;
import com.exercise.electricitybill.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UsageHistoryService {
    private static final Logger log = LoggerFactory.getLogger(UsageHistoryService.class);
    UsageHistoryRepository usageHistoryRepository;
    ConfigRepository configRepository;
    UsageHistoryMapper usageHistoryMapper;
    UserService userService;
    UserRepository userRepository;

    @PreAuthorize("hasRole('ELECTRICIAN')")
    public UsageHistoryResponse createUsageHistory(UsageHistoryRequest request){
        log.info("begin create usage");
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITED));
        if("ELECTRICIAN".equals(user.getRole())){
            throw new AppException(ErrorCode.USER_IS_ELICTRICIAN);
        }
        UsageHistory usageHistory= new UsageHistory();
        usageHistory.setDate(request.getDate());
        usageHistory.setKwhUsed(request.getKwhUsed());
        usageHistory.setTotalCost(calculatorCost(request.getKwhUsed()));
        usageHistory.setUser(user);
        usageHistoryRepository.save(usageHistory);
        log.info("Usage history create successful with id: {}",usageHistory.getHistoryId());

        return usageHistoryMapper.toUsageHistoryResponse(usageHistory);
    }

    private long  calculatorCost(int kwhUsed){
        var configs=configRepository.findAll();
        double totalCost=0;
        int remaining=kwhUsed;
        for(Config config: configs){
            if(remaining <=0)
                break;
            int gt=(config.getMaxKwh()==null)?remaining:(config.getMaxKwh()-config.getMinKwh()+1);
            if(remaining >= gt){
                totalCost+=gt*config.getPricePerKwh();
            }else{
                totalCost+=remaining*config.getPricePerKwh();
            }
            remaining-=gt;
        }
        log.info("totalCost: {}",totalCost);
        return Math.round(totalCost);
    }
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public List<UsageHistoryResponse> getAllUsageHistory(){
        var usageHistory=usageHistoryRepository.findAll();
        return usageHistory.stream().map(usageHistoryMapper::toUsageHistoryResponse).toList();
    }
    @PreAuthorize("hasRole('ELECTRICIAN')")
     public void deleteUsageHistory(Integer historyId){
         log.info("Delete usage history: {} successful",historyId);
         usageHistoryRepository.deleteById(historyId);
     }
     @PreAuthorize("hasRole('USER')")
    public List<UsageHistoryResponse> getUserUsageHistory(){
         UserResponse currentUser=userService.getMyInfo();
         Integer currentId =currentUser.getUserId();
         log.info("Fetching usage history for userId: {}", currentId);
         var historyList= usageHistoryRepository.findByUser_UserId(currentId);
         return historyList.stream().map(usageHistory -> usageHistoryMapper.toUsageHistoryResponse(usageHistory)).toList();
     }
}
