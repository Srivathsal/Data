package com.intuit.accountant.services.common.logging;

import org.springframework.stereotype.Component;

@Component
public class LogHelper {
	/*
	private static Map<String, Logger> loggers = new HashMap<>();
	private Logger logger = LogHelper.getLogger(LogHelper.class);
	
	public static Logger getLogger(String loggerName) {
		loggerName = loggerName.toLowerCase();
		Logger logger = null;
		if (!loggers.containsKey(loggerName)) {
			logger = LogManager.getLogger(loggerName);
			loggers.put(loggerName, logger);
		}
		if (logger == null)
			logger = loggers.get(loggerName);
		return logger;
	}
	
	public static <CLASS_TYPE> Logger  getLogger(Class<CLASS_TYPE> clazz) {
		return getLogger(clazz.toString());			
	}
	
	public static Logger debugLogger() {
		Logger logger = LogHelper.getLogger(DEBUG_LOGGER);
		return logger;
	}
	
	public LogHelper() {
		logger.info("Created LogHelper");
		testLogger("Logging output test line");
	}
	
	public static void testLogger(Logger loggerToTest, String message) {
		loggerToTest.trace(message + " (TRACE)");
		loggerToTest.debug(message + " (DEBUG)");
		loggerToTest.info(message + " (INFO)");
		loggerToTest.warn(message + " (WARN)");
		loggerToTest.error(message + " (ERROR)");
	}
	
	public void testLogger(String message) {
		debug(message);
		info(message);
		warn(message);
		error(message);
	}

	private static final String TRACE_LOGGER = "trace-logger";
	public static void trace(String message) {
		Logger logger = LogHelper.getLogger(TRACE_LOGGER);
		logger.debug(message);
	}
	
	private static final String DEBUG_LOGGER = "debug-logger";
	public static void debug(String message) {
		Logger logger = LogHelper.getLogger(DEBUG_LOGGER);
		logger.debug(message);
	}

	private static final String INFO_LOGGER = "info-logger";
	public static void info(String message) {
		Logger logger = LogHelper.getLogger(INFO_LOGGER);
		logger.info(message);
	}
	
	private static final String WARN_LOGGER = "warn-logger";
	public static void warn(String message) {
		Logger logger = LogHelper.getLogger(WARN_LOGGER);
		logger.warn(message);
	}
	
	private static final String ERROR_LOGGER = "error-logger";
	public static void error(String message) {
		Logger logger = LogHelper.getLogger(ERROR_LOGGER);
		logger.error(message);
	}	
	public static void error(String message, Exception ex) {
		Logger logger = LogHelper.getLogger(ERROR_LOGGER);		
		logger.error(message, ex);
	}
	*/
}
