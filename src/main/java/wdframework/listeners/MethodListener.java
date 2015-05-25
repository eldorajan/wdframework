package wdframework.listeners;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import wdframework.logger.Logger;

/**
 * Prints helper messages for all methods and logs them in logger file
 * @author Eldo Rajan
 *
 */
public class MethodListener  implements ITestListener {

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext arg0) {
		//Logger.info("Started the test case:" + arg0.getCurrentXmlTest().getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub

			}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult arg0) {
		Logger.info("Started the test case:" + arg0.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		Logger.info("Completed the test case:" + arg0.getMethod().getMethodName());
	}
}
