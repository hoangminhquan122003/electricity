package com.exercise.electricitybill.configuration;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QuartzConfig {

    @Bean
    public Trigger invalidTokenTrigger(){
        return TriggerBuilder.newTrigger()
                .withIdentity("invalid token")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 * * ?"))
                .build();
    }

    @Bean
    public JobDetail invalidTokenJobDetail(){
        return JobBuilder.newJob(InvalidTokenJob.class)
                .withIdentity("invalid job detail")
                .storeDurably()
                .build();
    }
    @Bean
    public Scheduler scheduler(Trigger invalidTokenTrigger, JobDetail invalidTokenJobDetail) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(invalidTokenJobDetail, invalidTokenTrigger);
        return scheduler;
    }
}
