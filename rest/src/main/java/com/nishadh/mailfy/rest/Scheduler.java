package com.nishadh.mailfy.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by nishadh on 12/1/15.
 */
public class Scheduler implements Runnable {

    private static final transient Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void init() {
        LOGGER.info("EMAIL SENDER: Timer initialized");
        scheduler.scheduleWithFixedDelay(this, 0, 1, TimeUnit.HOURS);
    }

    public void destroy() {
        LOGGER.info("EMAIL SENDER: task is being destroyed");
        scheduler.shutdown();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        LOGGER.info("EMAIL SENDER: Running per 10 seconds!");
        /*
        * TODO:
        * Check and send emails to users
        * */
    }
}