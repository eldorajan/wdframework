package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;
import wdframework.webelements.BasePage;
import wdframework.webelements.Element;
import wdframework.webelements.TextBox;

/**
 * Sample page
 * @author erajan
 *
 */
public class SearchPage extends BasePage{

	

	public TextBox searchElement(WebDriver driver){
		return new TextBox(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public Element searchLogo(WebDriver driver){
		return new Element(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
}
