package wdframework.action;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.webdriven.Windows;


/**
 * Action Super Class
 * @author Eldo Rajan
 *
 */
public class Action{
	
	public int DEFAULT_IMPLICIT_WAIT_TIMEOUT = 5;
	public int DEFAULT_EXPLICIT_WAIT_TIME_OUT = 15;
	public int DEFAULT_PAGE_LOAD_TIMEOUT = 90;

	
	/**
	 * Accept alerts
	 * @param driver
	 */
	public void acceptAlert(WebDriver driver)  {
		Alert alert = driver.switchTo().alert();
		alert.accept();
		driver.switchTo().defaultContent();
	}  

	/**
	 * Dismisses alerts
	 * @param driver
	 */
	public void dismissAlert(WebDriver driver)  {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		driver.switchTo().defaultContent();
	} 

	/**
	 * Get alert text
	 * @param driver
	 * @param text
	 * @return
	 */
	public String getAlertText(WebDriver driver,String text) {
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		return alertText;
	}

	/**
	 * get attributes
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public String getAttribute(WebElement element, String attributeName) {
		String attributeValue = element.getAttribute(attributeName);
		return attributeValue;
	}

	/**
	 * is element present
	 * @param element
	 * @return
	 */
	public boolean isElementPresent(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * is element not present
	 * @param element
	 * @return
	 */
	public boolean isNotElementPresent(WebElement element) {
		return !isElementPresent(element);
	}

	/**
	 * is element enabled
	 * @param element
	 * @return
	 */
	public boolean isElementEnabled(WebElement element) {
		return element.isEnabled();
	}

	/**
	 * is element not enabled
	 * @param element
	 * @return
	 */
	public boolean isElementNotEnabled(WebElement element) {
		return !element.isEnabled();
	}

	/**
	 * is element displayed
	 * @param element
	 * @return
	 */
	public boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed();
	}

	/**
	 * is element selected
	 * @param element
	 * @return
	 */
	public boolean isElementSelected(WebElement element) {
		return element.isSelected();
	}

	/**
	 * is element not selected
	 * @param element
	 * @return
	 */
	public boolean isElementNotSelected(WebElement element) {
		return !element.isSelected();
	}

	/**
	 * select frame
	 * @param driver
	 * @param by
	 */
	public void selectFrame(WebDriver driver,By by) {
		driver.switchTo().frame(driver.findElement(by));
	}

	/**
	 * select window
	 * @param driver
	 * @param windowName
	 */
	public  void selectWindow(WebDriver driver,String windowName){
		if (windowName == null) {
			Windows windows = new Windows(driver);
			try {
				windows.selectBlankWindow(driver);
			} catch (SeleniumException e) {
				driver.switchTo().defaultContent();
			}

		} else {
			Windows windows = new Windows(driver);
			windows.selectWindow(driver, "name=" + windowName);
		}
	}

	/**
	 * wait for element to be checked
	 * @param driver
	 * @param element
	 */
	public void waitForElementChecked(WebDriver driver,WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeSelected(element));
	}

	/**
	 * wait for element to be editable
	 * @param driver
	 * @param element
	 */
	public void waitForElementEditable(WebDriver driver,WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * wait for element to be present
	 * @param driver
	 * @param by
	 */
	public void waitForElementPresent(WebDriver driver,By by) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * wait for element to be present with timeout
	 * @param driver
	 * @param by
	 * @param timeout
	 */
	public void waitForElementPresent(WebDriver driver,By by, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * wait for element to be visible
	 * @param driver
	 * @param by
	 */
	public void waitForElementToBeVisible(WebDriver driver,By by) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	/**
	 * wait for element to be present
	 * @param driver
	 * @param element
	 */
	public void waitForElementToBeVisible(WebDriver driver,WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * wait for element to be disappear
	 * @param driver
	 * @param by
	 */
	public void waitForElementToDisappear(WebDriver driver,By by) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**
	 * wait for text to be present
	 * @param driver
	 * @param element
	 * @param text
	 */
	public void waitForTextPresent(WebDriver driver,WebElement element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
}
