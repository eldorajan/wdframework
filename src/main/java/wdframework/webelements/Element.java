package wdframework.webelements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import wdframework.webelements.BasePage.LocatorType;

/**
 * Base Element
 * @author erajan
 *
 */
public class Element implements WebElement {

	protected WebElement element;
	
	public Element(WebElement element){
		this.element = element;
	}
	
	public Element(WebDriver driver,By by){
		this.element = driver.findElement(by);
	}
	
	public Element(WebDriver driver,String locator){
		this.element = driver.findElement(getBy(locator));
	}

	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement element) {
		this.element = element;
	}

	
	public void clear() {
		element.clear();
	}

	
	public void click() {
		element.click();
	}

	
	public WebElement findElement(By by) {
		return findElement(by);
	}

	
	public List<WebElement> findElements(By by) {
		return findElements(by);
	}

	
	public String getAttribute(String attribute) {
		return element.getAttribute(attribute);
	}

	
	public String getCssValue(String cssValue) {
		return element.getCssValue(cssValue);
	}

	
	public Point getLocation() {
		return element.getLocation();
	}

	
	public Dimension getSize() {
		return element.getSize();
	}

	
	public String getTagName() {
		return element.getTagName();
	}

	
	public String getText() {
		return element.getText();
	}

	
	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	
	public boolean isEnabled() {
		return element.isEnabled();
	}

	
	public boolean isSelected() {
		return element.isSelected();
	}

	
	public void type(String text) {
		element.sendKeys(text);
	}

	
	public void submit() {
		element.submit();
	}

	
	public void sendKeys(CharSequence... text) {
		element.sendKeys(text);
	}

	public boolean isElementPresent(By by) {
		boolean present = false;
		try{
			element.findElement(by);
			present = true;
		}catch(Exception e){
			present = false;
		} 
		return present; 

	}
	
	public boolean isElementVisible() {
		return element.isDisplayed();
	}
	
	public void selectDropDown(String valToSelect){
		Select select = new Select(element);  
		// Get a list of the options 
		List<WebElement> options = select.getOptions();  
		// For each option in the list, verify if it's the one you want and then click it 
		for (WebElement we : options) {     
			if (we.getText().equalsIgnoreCase(valToSelect)) {         
				System.out.println(String.format("Value is: %s", we.getText()));
				we.click();
				break;     
			} 
		} 

	}
	
	public void selectValueFromDropDown(String valToSelect){
		Select select = new Select(element);  
		// Get a list of the options 
		List<WebElement> options = select.getOptions();  
		// For each option in the list, verify if it's the one you want and then click it 
		for (WebElement we : options) {     
			if (we.getText().equalsIgnoreCase(valToSelect)) {         
				System.out.println(String.format("Value is: %s", we.getText()));
				select.selectByValue(valToSelect);
				break;     
			} 
		} 

	}

	public void selectValueVisible(String valToSelect){
		new Select(element).selectByVisibleText(valToSelect);  
	}



	public String[] getValueFromDropDown(){
		Select select = new Select(element);  
		// Get a list of the options 
		List<WebElement> options = select.getOptions();
		String[] value = new String[options.size()];
		for (int i=0;i<options.size();i++) {
			value[i] =options.get(i).getText();
		}
		return value;
	}

	public String selectedItem(){ 
		Select select = new Select(element); 
		WebElement element = select.getFirstSelectedOption();
		return element.getText();     
	}

	public void mouseOver(WebDriver driver){
		Actions action = new Actions(driver);		 
        action.moveToElement(element).build().perform();
	}
	
	public void mouseOverClick(WebDriver driver){
		Actions action = new Actions(driver);		 
        action.moveToElement(element).click().build().perform();
	}
	
	public By getBy(String locator) {
		LocatorType identifier = LocatorType.valueOf(locator.toUpperCase().substring(0, locator.indexOf("=")));
		locator = locator.substring(locator.indexOf("=")+1);

		By locatorIdentifiedBy=null;
		switch (identifier) {
		case XPATH:
			locatorIdentifiedBy = By.xpath(locator);
			break;
		case CSS:
			locatorIdentifiedBy = By.cssSelector(locator);
			break;
		case ID:
			locatorIdentifiedBy = By.id(locator);
			break;
		case NAME:
			locatorIdentifiedBy = By.name(locator);
			break;
		case CLASS:
			locatorIdentifiedBy = By.className(locator);
			break;
		case LINK:
			locatorIdentifiedBy = By.linkText(locator);
			break;
		case LINKP:
			locatorIdentifiedBy = By.partialLinkText(locator);
			break;
		default:
			locatorIdentifiedBy = By.cssSelector(locator);
			break;
		}
		return locatorIdentifiedBy;
	}
	
}
