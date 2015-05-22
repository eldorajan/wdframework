package wdframework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * HyperLink Element
 * @author erajan
 *
 */
public class HyperLink extends Element {

	public HyperLink(WebElement element) {
		super(element);
	}
	
	public HyperLink(WebDriver driver, By by) {
		super(driver, by);
	}

	

}
