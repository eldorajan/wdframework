package wdframework.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import wdframework.webelements.BasePage;

/**
 * Page factory for webelements
 * @author erajan
 *
 */
public class AdvancedPageFactory {
	
	/**
	 * 
	 * @param driver
	 * @param klass
	 * @return
	 */
	public static <T extends BasePage> T getPageObject(WebDriver driver,Class<T> klass) {
		return PageFactory.initElements(driver, klass);
	}
}

