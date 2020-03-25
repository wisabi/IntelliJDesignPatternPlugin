package com.DesignPatternFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log{
    private static Logger logger = null;
    public static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger("DePaGenPlugin_Logger");
            logger.trace("Starting logger.");
        }
        logger.trace("Returning logger instance.");
        return logger;
    }
}