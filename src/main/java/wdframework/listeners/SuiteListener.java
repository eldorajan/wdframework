package wdframework.listeners;

import org.testng.IExecutionListener;

import wdframework.logger.Logger;

/**
 * Helper messages for tests suite level
 * @author erajan
 *
 */
public class SuiteListener implements IExecutionListener {
	private long startTime;

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