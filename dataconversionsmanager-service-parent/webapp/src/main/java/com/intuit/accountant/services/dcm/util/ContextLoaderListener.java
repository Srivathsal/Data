package com.intuit.accountant.services.dcm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            super.contextInitialized(event);
        } catch (Throwable t) {
            logger.error("Application Initialization failed", t);
            throw t;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            super.contextDestroyed(event);
        } catch (Throwable t) {
            logger.error("Application shutdown failed", t);
            throw t;
        }
    }
}
