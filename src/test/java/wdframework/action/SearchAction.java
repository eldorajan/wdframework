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
			
			Assert.assertTrue(sp.searchelement(driver).isElementVisible());
			sp.searchelement(driver).type(text);
			sp.searchelement(driver).submit();
			
			Thread.sleep(5000);
			
			sp.searchlogo(driver).waitForElementPresent(driver);
			sp.searchlogo(driver).waitForElementToBeVisible(driver);
			Assert.assertTrue(sp.searchlogo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains(text));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
