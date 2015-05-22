package wdframework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TextBox Element
 * @author erajan
 *
 */
public class TextBox extends Element {

	public TextBox(WebElement element) {
		super(element);
	}

	public TextBox(WebDriver driver, By by) {
		super(driver, by);
	}

	public void clear() {
		element.clear();
	}
	
	public void type(String text) {
		element.sendKeys(text);
	}
}