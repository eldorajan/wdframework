package wdframework.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import wdframework.common.CommonTest;

/**
 * Android Test Suite
 * @author Eldo Rajan
 *
 */
public class AndroidTest extends CommonTest{

	@Test
	public void apiDemo(){
		WebElement el = getWebDriver().findElement(By.name("Animation"));
		Assert.assertEquals("Animation", el.getText());
		el = getWebDriver().findElement(By.className("android.widget.TextView"));
		Assert.assertEquals("API Demos", el.getText());
		el = getWebDriver().findElement(By.name("App"));
		el.click();
		List<WebElement> els = getWebDriver().findElements(By.className("android.widget.TextView"));
		Assert.assertEquals("Activity", els.get(2).getText());
	}
}
