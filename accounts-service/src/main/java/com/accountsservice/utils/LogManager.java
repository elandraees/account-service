package com.accountsservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogManager {

    // Create log files in directory (windows/linux)
    // Store which file to log messages too
    // Set log properties in application logger
    // Create process that 'archives' current log file and creates a new one

    private final static Logger LOGGER = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public static void addLogMessage(String message, Class<?> clazz) {

    }

    public static void addError(String error, Class<?> clazz) {

        LOGGER.error(clazz.getName() + ": " + error);
    }

}
