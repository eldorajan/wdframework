package wdframework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import wdframework.logger.Logger;

/**
 * Retry analyzer
 * @author Eldo Rajan
 *
 */
public class RetryAnalyzer implements IRetryAnalyzer {

	private int count = 0;
	private int maxCount = 1;
	
	public RetryAnalyzer() {
		setCount(maxCount);
	}

	public boolean retry(ITestResult result) {
		
		if (!result.isSuccess()) {
			if (count < maxCount) {
				count++;
				Logger.info("Error seen in "+ result.getName() +" Retrying " + count + " times in progress");
				result.getTestContext().getFailedTests().removeResult(result.getMethod());
				/*ISuiteResult suiteResult = null;
				suiteResult.getTestContext().getFailedTests().removeResult(result.getMethod());*/
				return true;
			}

		}
		return false;
	}

	public void setCount(int count) {
		maxCount = count;
	}

	
	
}