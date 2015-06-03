package wdframework.action;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import wdframework.logger.Logger;
import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.SearchPage;

/**
 * Sample google search action class
 * @author Eldo Rajan
 *
 */
public class SearchAction extends Action{

	/**
	 * Sample Action method
	 * @param driver
	 * @param text
	 */
	public void searchInGoogle(WebDriver driver,String text){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			SearchPage sp =  AdvancedPageFactory.getPageObject(driver,SearchPage.class);
			
			sp.searchelement(driver).waitForLoading(driver);
			Assert.assertTrue(sp.searchelement(driver).isElementVisible());
			sp.searchelement(driver).type(text);
			sp.searchelement(driver).submit();
			
			sp.searchlogo(driver).waitForLoading(driver);
			
			sp.searchlogo(driver).waitForElementPresent(driver);
			sp.searchlogo(driver).waitForElementToBeVisible(driver);
			Logger.info("****************"+driver.getTitle()+"****************");
			Assert.assertTrue(sp.searchlogo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains(text));
			
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
			Assert.fail(e.toString());
		}
		
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	
	
}
