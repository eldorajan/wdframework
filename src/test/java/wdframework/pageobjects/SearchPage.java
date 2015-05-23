package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;

import wdframework.webelements.BasePage;
import wdframework.webelements.HyperLink;
import wdframework.webelements.TextBox;

/**
 * Sample page
 * @author erajan
 *
 */
public class SearchPage extends BasePage{

	

	public TextBox searchElement(WebDriver driver){
		return new TextBox(driver,getLocator());
	}
	
	public HyperLink searchLogo(WebDriver driver){
		return new HyperLink(driver,getLocator());
	}
}
