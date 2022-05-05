package com.intuit.accountant.services.common.logging;


//TODO: Removed from code base until we pick up log4j2
//@Component
public class SFL4JLogHelper {
//	private static Map<String, Logger> loggers = new HashMap<>();
//	private Logger logger = SFL4JLogHelper.getLogger(SFL4JLogHelper.class);
//	
//	public static Logger getLogger(String loggerName) {
//		loggerName = loggerName.toLowerCase();
//		Logger logger = null;
//		if (!loggers.containsKey(loggerName)) {
//			logger = LoggerFactory.getLogger(loggerName);
//			loggers.put(loggerName, logger);
//		}
//		if (logger == null)
//			logger = loggers.get(loggerName);
//		return logger;
//	}
//	
//	public static void unloadLogger(String loggerName) {
//		Logger logger = getLogger(loggerName);
//		if (logger == null)
//			return;
//		loggers.remove(loggerName);
//		logger = null;
//	}
//
//	
//	public static <CLASS_TYPE> Logger  getLogger(Class<CLASS_TYPE> clazz) {
//		return getLogger(clazz.toString());			
//	}
//	
//	public static Logger debugLogger() {
//		Logger logger = SFL4JLogHelper.getLogger(DEBUG_LOGGER);
//		return logger;
//	}
//	
//	public SFL4JLogHelper() {
//		logger.info("Created LogHelper");
//		testLogger("Logging output test line");
//	}
//	
//	public static void testLogger(Logger loggerToTest, String message) {
//		loggerToTest.trace(message + " (TRACE)");
//		loggerToTest.debug(message + " (DEBUG)");
//		loggerToTest.info(message + " (INFO)");
//		loggerToTest.warn(message + " (WARN)");
//		loggerToTest.error(message + " (ERROR)");
//	}
//	
//	public void testLogger(String message) {
//		trace(message + " (TRACE)");
//		debug(message + " (DEBUG)");
//		info(message + " (INFO)");
//		warn(message + " (WARN)");
//		error(message + " (ERROR)");
//	}
//
//	private static final String TRACE_LOGGER = "trace-logger";
//	public static void trace(String message) {
//		Logger logger = SFL4JLogHelper.getLogger(TRACE_LOGGER);
//		logger.debug(message);
//	}
//	
//	private static final String DEBUG_LOGGER = "debug-logger";
//	public static void debug(String message) {
//		Logger logger = SFL4JLogHelper.getLogger(DEBUG_LOGGER);
//		logger.debug(message);
//	}
//
//	private static final String INFO_LOGGER = "info-logger";
//	public static void info(String message) {
//		Logger logger = SFL4JLogHelper.getLogger(INFO_LOGGER);
//		logger.info(message);
//	}
//	
//	private static final String WARN_LOGGER = "warn-logger";
//	public static void warn(String message) {
//		Logger logger = SFL4JLogHelper.getLogger(WARN_LOGGER);
//		logger.warn(message);
//	}
//	
//	private static final String ERROR_LOGGER = "error-logger";
//	public static void error(String message) {
//		Logger logger = SFL4JLogHelper.getLogger(ERROR_LOGGER);
//		logger.error(message);
//	}	
//	public static void error(String message, Exception ex) {
//		Logger logger = SFL4JLogHelper.getLogger(ERROR_LOGGER);		
//		logger.error(message, ex);
//	}
}
