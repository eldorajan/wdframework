package wdframework.webelements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Element List
 * @author erajan
 *
 * @param <T>
 */
public class ElementList <T extends Element>{

	protected List<WebElement> elementList;
	
	public ElementList(List<WebElement> elementList){
		this.elementList = elementList;
	}
	
	public ElementList(WebDriver driver,By by){
		this.elementList = driver.findElements(by);
	}

	@SuppressWarnings("unchecked")
	public List<T> getChildElements() {
		List<T> ElementList = new ArrayList<T>();
		
		for (WebElement anElement : elementList){
			try {
				ElementList.add((T) new Element(anElement));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return ElementList;
	}

	public List<WebElement> getElementList() {
		return elementList;
	}

	public void setElementList(List<WebElement> elementList) {
		this.elementList = elementList;
	}

	
}
