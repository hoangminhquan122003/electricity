package com.exercise.electricitybill.configuration;

import com.exercise.electricitybill.repository.InvalidatedTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
public class InvalidTokenJob implements Job {
    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date=new Date();
        int deleteToken=invalidatedTokenRepository.deleteByExpirationTimeBefore(date);
        log.info("delete {} expiration token",deleteToken);
    }
}
