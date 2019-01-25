package com.xuannam.quartzcheduler.demo.job;

import com.xuannam.quartzcheduler.demo.model.User;
import com.xuannam.quartzcheduler.demo.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SimpleJob implements Job
{
    @Autowired private UserService userService;

    @Override
    public void execute(JobExecutionContext context) {

        List<User> users = userService.getUsers();
        System.out.println("This is a quartz job! " + new Date() + users);
    }
}
