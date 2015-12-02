package com.nishadh.mailfy.rest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nishadh on 12/1/15.
 */
public class Activator implements BundleActivator {
    private static final transient Logger LOGGER = LoggerFactory.getLogger(Activator.class);
    private Scheduler scheduler;

    public void start(BundleContext bundleContext) throws Exception {
        LOGGER.info("Started Rest!");
        LOGGER.debug("EMAIL: init discovery task");
        scheduler = new Scheduler();
        scheduler.init();
    }

    public void stop(BundleContext bundleContext) throws Exception {
        LOGGER.info("Stop Rest!");
    }
}
