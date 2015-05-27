package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;

import wdframework.webelements.BasePage;
import wdframework.webelements.Button;
import wdframework.webelements.Element;
import wdframework.webelements.ElementList;
import wdframework.webelements.HyperLink;
import wdframework.webelements.TextBox;

/**
 * Box page
 * @author Eldo Rajan
 *
 */
public class BoxPage extends BasePage{

	

	public TextBox username(WebDriver driver){
		return new TextBox(driver,getLocator());
	}
	
	public TextBox password(WebDriver driver){
		return new TextBox(driver,getLocator());
	}
	
	public Button loginbutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public HyperLink logo(WebDriver driver){
		return new HyperLink(driver,getLocator());
	}
	
	public Button createnewbutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> createnewdropdownlist(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public TextBox createnewfolderinputbox(WebDriver driver){
		return new TextBox(driver,getLocator());
	}
	
	public Button createnewfolderbutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderelement(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementtitle(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementmoreactions(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementmoreactionsdropdownlist(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public Button boxfolderfiledownloadokayelement(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementmoreactionscheckbox(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public Button boxfolderfileelementactionbaroptions(WebDriver driver,String optionName){
		return new Button(driver,getLocator().replace("OPTIONNAME", optionName));
	}
	
	public Button boxfolderfiledeleteokayelement(WebDriver driver){
		return new Button(driver,getLocator());
	}

	public Button boxuploadbutton(WebDriver driver) {
		return new Button(driver,getLocator());
	}

	public ElementList<Element> boxuploaddropdownlist(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}

	public Button boxuploadokayelement(WebDriver driver) {
		return new Button(driver,getLocator());
	}
		
	
	
}
