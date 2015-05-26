package wdframework.webelements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import wdframework.logger.Logger;
import wdframework.webelements.BasePage.LocatorType;

/**
 * Base Element
 * @author Eldo Rajan
 *
 */
public class Element implements WebElement {
	protected int DEFAULT_IMPLICIT_WAIT_TIMEOUT = 5;
	protected int DEFAULT_EXPLICIT_WAIT_TIME_OUT = 15;
	protected int DEFAULT_PAGE_LOAD_TIMEOUT = 90;
	
	protected WebElement element;
	protected WebDriver driver;
	protected String locator;
	
	public Element(WebElement element){
		this.element = element;
	}
	
	public Element(WebDriver driver,By by){
		this.driver = driver;
		try{
			this.element = driver.findElement(by);
		}catch(Exception e){} 
	}
	
	public Element(WebDriver driver,String locator){
		this.locator = locator;
		this.driver = driver;
		try{
			this.element = driver.findElement(getBy(locator));
		}catch(Exception e){} 
		
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
		try{
			return findElement(by);
		}catch(Exception e){
			return null;
		} 
		
		
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
		try{
			return element.isDisplayed();
		}catch(Exception e){
			return false;
		} 		
	}

	
	public boolean isEnabled() {
		try{
			return element.isEnabled();
		}catch(Exception e){
			return false;
		} 
	}

	
	public boolean isSelected() {
		try{
			return element.isSelected();
		}catch(Exception e){
			return false;
		} 
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

	public boolean isElementPresent(WebDriver driver) {
		try{
			return driver.findElements(getBy(locator)).size()>0;
		}catch(Exception e){
			return false;
		} 
	}
	
	public boolean isElementVisible() {
		try{
			return element.isDisplayed();
		}catch(Exception e){
			return false;
		} 
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
		Actions action2 = action.moveToElement(element);
		action2.click().build().perform();
	}
	
	public void mouseOverRightClick(WebDriver driver){
		Actions action = new Actions(driver);		 
		Actions action2 = action.moveToElement(element);
		action2.contextClick().build().perform();
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
	
	public void check() {
		element.click();
	}
	
	public void uncheck(){
		if(element.isSelected()){
			element.click();
		}
	}

	public boolean isChecked(){
		try{
			return element.isSelected();
		}catch(Exception e){
			return false;
		} 
	}
	
	public void select() {
		element.click();
	}
	
	public void unselect(){
		if(element.isSelected()){
			element.click();
		}
	}

	public  void selectValue(String sItem) {
		selectValueFromDropDown(sItem);	
	}
	
	public String[] getItemList(){
		return getValueFromDropDown();
	}
	
	public void selectItem(String sItem) {
		selectDropDown(sItem);	
	}

	/**
	 * wait for element to be checked
	 * @param driver
	 * @param element
	 */
	public void waitForElementChecked(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeSelected(element));
	}

	/**
	 * wait for element to be editable
	 * @param driver
	 * @param element
	 */
	public void waitForElementEditable(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * wait for element to be present
	 * @param driver
	 * @param by
	 */
	public void waitForElementPresent(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(getBy(locator)));
	}

	/**
	 * wait for element to be present with timeout
	 * @param driver
	 * @param by
	 * @param timeout
	 */
	public void waitForElementPresent(WebDriver driver,int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(getBy(locator)));
	}

	
	/**
	 * wait for element to be present
	 * @param driver
	 * @param element
	 */
	public void waitForElementToBeVisible(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * wait for element to be disappear
	 * @param driver
	 * @param by
	 */
	public void waitForElementToDisappear(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(getBy(locator)));
	}

	/**
	 * wait for text to be present
	 * @param driver
	 * @param element
	 * @param text
	 */
	public void waitForTextPresent(WebDriver driver,String text) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
	
}
