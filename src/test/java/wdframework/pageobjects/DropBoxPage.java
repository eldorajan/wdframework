package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;

import wdframework.webelements.BasePage;
import wdframework.webelements.Button;
import wdframework.webelements.Element;
import wdframework.webelements.ElementList;
import wdframework.webelements.HyperLink;
import wdframework.webelements.TextBox;

/**
 * Dropbox page
 * @author Eldo Rajan
 *
 */
public class DropBoxPage extends BasePage{

	

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
	
	public Button usernamebuttonintopbar(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> usernamebuttonintopbardropdown(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public HyperLink closeoverlayicon(WebDriver driver){
		return new HyperLink(driver,getLocator());
	}

	public ElementList<Element> dropboxmenuactions(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public Element dropboxmenuactionslink(WebDriver driver, String actionName){
		return new Element(driver,getLocator().replace("ACTION_NAME", actionName));
	}
	
	public ElementList<Element> getdropboxfilefoldercount(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> getdropboxfilefoldertitle(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> dropboxfilefolderrightclickoptions(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public Button dropboxcreatefolderbutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public TextBox dropboxcreatefoldernametextbox(WebDriver driver){
		return new TextBox(driver,getLocator());
	}
	
	public ElementList<Element> dropboxdeletefolderbutton(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> dropboxchoosefilebutton(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
}
