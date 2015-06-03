package wdframework.logger;


import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;


/**
 * Logger class
 * @author Eldo Rajan
 *
 */
public class Logger {

	protected static boolean isConfigured = false;
	protected static String loggerDirectory = System.getProperty("user.dir") +File.separator+"src"+File.separator+"main"+File.separator+
										"resources"+File.separator+"logger";
	protected static String loggerFile =File.separator+"log4j.properties";		
	
	/**
	 * 
	 * @return org.apache.log4j.Logger
	 */
	public synchronized static org.apache.log4j.Logger getLogger(String logFileName) {
		
		if (!isConfigured){
			PropertyConfigurator.configure(loggerDirectory + File.separator+logFileName);
			isConfigured = true;
		}
		return org.apache.log4j.Logger.getLogger(getClassName());
	}


	/**
	 * 
	 * @return org.apache.log4j.Logger
	 */
	public synchronized static org.apache.log4j.Logger getLogger() {
		if (!isConfigured){
			PropertyConfigurator.configure(loggerDirectory + org.apache.commons.io.FilenameUtils.separatorsToSystem(loggerFile));
			isConfigured = true;
		}
		return org.apache.log4j.Logger.getLogger(getClassName());
	}


	/**
	 * 
	 * @return the class name as a String object
	 */
	public synchronized static String getClassName() {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(Thread.currentThread().getStackTrace()[4].getClassName()).getName();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "";

	}


	
	/**
	 * 
	 * @param message
	 */
	public synchronized static void debug(Object message) {
		getLogger().debug(message);Reporter.log(message+"");
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public synchronized static void debug(Object message, Throwable t) {
		getLogger().debug(message, t);Reporter.log(message+"");
	}

	/**
	 * 
	 * @param message
	 */
	public synchronized static void error(Object message) {
		getLogger().error(message);Reporter.log(message+"");
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public synchronized static void error(Object message, Throwable t) {
		getLogger().error(message, t);Reporter.log(message+"");
	}

	/**
	 * 
	 * @param message
	 */
	public synchronized static void fatal(Object message) {
		getLogger().fatal(message);Reporter.log(message+"");
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public synchronized static void fatal(Object message, Throwable t) {
		getLogger().fatal(message, t);Reporter.log(message+"");
	}

	/**
	 * 
	 * @param message
	 */
	public synchronized static void info(Object message) {
		Reporter.log(message+"");getLogger().info(message);
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public synchronized static void info(Object message, Throwable t) {
		getLogger().info(message, t);Reporter.log(message+"");
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static boolean isDebugEnabled() {
		return getLogger().isDebugEnabled();
	}

	/**
	 * 
	 * @param level
	 * @return
	 */
	public synchronized static boolean isEnabledFor(Priority level) {
		return getLogger().isEnabledFor(level);
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static boolean isInfoEnabled() {
		return getLogger().isInfoEnabled();
	}


	/**
	 * 
	 * @param level
	 */
	public synchronized static void setLevel(Level level) {
		getLogger().setLevel(level);
	}

	/**
	 * 
	 * @param message
	 */
	public synchronized static void warn(Object message) {
		getLogger().warn(message);Reporter.log(message+"");
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public synchronized static void warn(Object message, Throwable t) {
		getLogger().warn(message, t);
	}
}

