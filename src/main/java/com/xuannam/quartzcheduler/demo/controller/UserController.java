package com.xuannam.quartzcheduler.demo.controller;

import com.xuannam.quartzcheduler.demo.config.SpringJobFactory;
import com.xuannam.quartzcheduler.demo.job.SimpleJob;
import com.xuannam.quartzcheduler.demo.model.User;
import com.xuannam.quartzcheduler.demo.service.UserService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired private UserService userService;
    @Autowired private ApplicationContext applicationContext;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsers()
    {
        List<User> users = userService.getUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody User user)
    {
        user = userService.createUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    private JobFactory jobFactory(ApplicationContext applicationContext) {
        SpringJobFactory jobFactory = new SpringJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    private JobDetail createJobDetail(String jobName, Class<? extends Job> jobClass)
    {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobName)
                .build();
    }

    @RequestMapping(value = "api/job/users", method = RequestMethod.POST)
    public ResponseEntity<Object> runJobCreateUser(@RequestBody User user)
    {
        try {
            JobDetail job = createJobDetail("Job 1", SimpleJob.class);
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(5).repeatForever())
                    .build();

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.setJobFactory(jobFactory(applicationContext));
            scheduler.start();
        }
        catch (SchedulerException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
