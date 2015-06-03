package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;

import wdframework.webelements.BasePage;
import wdframework.webelements.HyperLink;
import wdframework.webelements.TextBox;

/**
 * Search page
 * @author Eldo Rajan
 *
 */
public class SearchPage extends BasePage{

	

	public TextBox searchelement(WebDriver driver){
		return new TextBox(driver,getLocator());
	}
	
	public HyperLink searchlogo(WebDriver driver){
		return new HyperLink(driver,getLocator());
	}
}
