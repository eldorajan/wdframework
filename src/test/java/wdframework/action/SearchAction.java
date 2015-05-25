package wdframework.action;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.SearchPage;

/**
 * Sample google search action class
 * @author Eldo Rajan
 *
 */
public class SearchAction{

	/**
	 * Sample Action method
	 * @param driver
	 * @param text
	 */
	public void searchInGoogle(WebDriver driver,String text){
		
		try {
			SearchPage sp =  AdvancedPageFactory.getPageObject(driver,SearchPage.class);
			
			Assert.assertTrue(sp.searchElement(driver).isElementVisible());
			sp.searchElement(driver).type(text);
			sp.searchElement(driver).submit();
			
			sp.searchLogo(driver).waitForElementPresent(driver);
			sp.searchLogo(driver).waitForElementToBeVisible(driver); 
			
			Assert.assertTrue(sp.searchLogo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains(text));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
