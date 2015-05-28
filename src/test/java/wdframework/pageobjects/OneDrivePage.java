package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;

import wdframework.webelements.BasePage;
import wdframework.webelements.Button;
import wdframework.webelements.Element;
import wdframework.webelements.ElementList;
import wdframework.webelements.HyperLink;
import wdframework.webelements.TextBox;

/**
 * Onedrive page
 * @author Eldo Rajan
 *
 */
public class OneDrivePage extends BasePage{

	

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
	
	public HyperLink usernamesignoutbuttonintopbar(WebDriver driver){
		return new HyperLink(driver,getLocator());
	}
	
	public Button createbutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> createdropdown(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> uploaddropdown(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}

	public ElementList<Element> quickheader(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public TextBox folderinput(WebDriver driver){
		return new TextBox(driver,getLocator());
	}
	
	public Button foldercreatebutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> createdfolderelement(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createdfoldercount(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createdfoldertitle(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createdfolderselectbox(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createdfileelement(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createdfiletitle(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createdfileselectbox(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createmanagebutton(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> createdfiletypeelement(WebDriver driver,String fileType){
		return new ElementList<Element>(driver,getLocator().replace("FILETYPE", fileType));
	}
	
	public ElementList<Element> createdfiletypetitle(WebDriver driver,String fileType){
		return new ElementList<Element>(driver,getLocator().replace("FILETYPE", fileType));
	}
	
	public ElementList<Element> createdfiletypeselectbox(WebDriver driver,String fileType){
		return new ElementList<Element>(driver,getLocator().replace("FILETYPE", fileType));
	}
	
	public ElementList<Element> managelinks(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public Button createbuttonfile(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public Button errorpaneldeletebutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
}
