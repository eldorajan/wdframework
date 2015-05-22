package wdframework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * RadioButton Element
 * @author erajan
 *
 */
public class RadioButton extends Element {

	public RadioButton(WebElement element) {
		super(element);
	}

	public RadioButton(WebDriver driver, By by) {
		super(driver, by);
	}
	
	public void select() {
		this.click();
	}
	
	public void unselect(){
		if(this.isSelected()){
			this.click();
		}
	}

	public boolean isSelected(){
		return this.isSelected();
	}
	
	
	
}