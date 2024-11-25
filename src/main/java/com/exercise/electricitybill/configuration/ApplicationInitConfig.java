package com.exercise.electricitybill.configuration;

import com.exercise.electricitybill.entity.User;
import com.exercise.electricitybill.repository.UserRepository;
import com.exercise.electricitybill.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationInitConfig {
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("electrician").isEmpty()){
                PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
                User electrician =User.builder()
                        .username("electrician")
                        .password(passwordEncoder.encode("electrician"))
                        .role(Role.ELECTRICIAN.name())
                        .build();
                userRepository.save(electrician);
                log.info("electrician create successful with username: electrician and password: electrician");
            }
        };
    }
}
