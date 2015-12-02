package com.nishadh.mailfy.persistence.service;

import com.nishadh.mailfy.model.EmailAlert;
import com.nishadh.mailfy.model.User;
import com.nishadh.mailfy.service.EmailAlertService;
import com.nishadh.mailfy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * Created by nishadh on 12/2/15.
 */

@Singleton
public class Initializer {
    Logger LOG = LoggerFactory.getLogger(Initializer.class);
    @Inject
    UserService userService;

    @Inject
    EmailAlertService emailAlertService;

    @PostConstruct
    public void addDemoUser() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setEmail("online@nishadh.com.np");
                user.setPassword("password");
                user.setFirstname("Nishadh");
                user.setLastname("Shrestha");
                user.setCreatedAt(new Date());
                userService.create(user);

                EmailAlert emailAlert = new EmailAlert();
                emailAlert.setUser(user);
                emailAlert.setTitle("Alert 1");
                emailAlert.setMessage("Please do something here!");
                emailAlert.setCreatedAt(new Date());
                emailAlert.setTargetDate(new Date());
                emailAlertService.create(emailAlert);

                EmailAlert emailAlert2 = new EmailAlert();
                emailAlert2.setUser(user);
                emailAlert2.setTitle("Alert 2");
                emailAlert2.setMessage("Something else to do?");
                emailAlert2.setCreatedAt(new Date());
                emailAlert2.setTargetDate(new Date());
                emailAlertService.create(emailAlert2);
            }
        });
    }
}