package com.exercise.electricitybill.repository;

import com.exercise.electricitybill.dto.response.UsageHistoryResponse;
import com.exercise.electricitybill.entity.UsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsageHistoryRepository extends JpaRepository<UsageHistory,Integer> {
    @Query("SELECT u FROM UsageHistory u WHERE u.user.userId= :userId")
    List<UsageHistory> findByUser_UserId( @Param("userId") Integer userId);

}
