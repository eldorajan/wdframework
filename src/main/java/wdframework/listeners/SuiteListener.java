package wdframework.listeners;

import java.io.File;

import org.testng.IExecutionListener;

import wdframework.logger.Logger;

/**
 * Helper messages for tests suite level
 * @author Eldo Rajan
 *
 */
public class SuiteListener implements IExecutionListener {
	private long startTime;
	public String reportLocation = System.getProperty("user.dir")+File.separator
			+"target" + File.separator + "reports" + File.separator + "reports.html";

	@Override
	public void onExecutionStart() {
		startTime = System.currentTimeMillis();
		Logger.info("Test Suite is going to start");
	}

	@Override
	public void onExecutionFinish() {
		Logger.info("Test Suite has completed, took around " + ((System.currentTimeMillis() - startTime)/1000) + "s");
	}
}