package wdframework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * SelectList Element
 * @author erajan
 *
 */
public class SelectList extends Element{

	public SelectList(WebElement element) {
		super(element);
	}

	public SelectList(WebDriver driver, By by) {
		super(driver, by);
	}
	
	public SelectList(WebDriver driver, String locator) {
		super(driver, locator);
	}

	public void selectItem(String sItem) {
		selectDropDown(sItem);	
	}
	
	public  void selectValue(String sItem) {
		selectValueFromDropDown(sItem);	
	}
	
	public  void selectValueVisible(String sItem) {
		selectValueVisible(sItem);	
	}
	
	public String selectedItem(){
		return selectedItem();
	}
	
	public String[] getItemList(){
		return getValueFromDropDown();
	}
	
	
}
