package wdframework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Button Element
 * @author erajan
 *
 */
public class Button extends Element {

	public Button(WebElement element) {
		super(element);
	}
	
	public Button(WebDriver driver, By by) {
		super(driver, by);
	}

	

}
