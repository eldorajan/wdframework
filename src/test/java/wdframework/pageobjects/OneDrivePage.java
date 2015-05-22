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
 * @author erajan
 *
 */
public class OneDrivePage extends BasePage{

	

	public TextBox username(WebDriver driver){
		return new TextBox(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public TextBox password(WebDriver driver){
		return new TextBox(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public Button loginbutton(WebDriver driver){
		return new Button(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public HyperLink logo(WebDriver driver){
		return new HyperLink(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public Button createbutton(WebDriver driver){
		return new Button(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdropdown(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}

	public ElementList<Element> quickheader(WebDriver driver) {
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public TextBox folderinput(WebDriver driver){
		return new TextBox(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public Button foldercreatebutton(WebDriver driver){
		return new Button(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfolderelement(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfoldercount(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfoldertitle(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfolderselectbox(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfileelement(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfiletitle(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfileselectbox(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createmanagebutton(WebDriver driver){
		return new ElementList<Element>(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public ElementList<Element> createdfiletypeelement(WebDriver driver,String fileType){
		return new ElementList<Element>(driver,getBy(getLocator(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()).replace("FILETYPE", fileType)));
	}
	
	public ElementList<Element> createdfiletypetitle(WebDriver driver,String fileType){
		return new ElementList<Element>(driver,getBy(getLocator(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()).replace("FILETYPE", fileType)));
	}
	
	public ElementList<Element> createdfiletypeselectbox(WebDriver driver,String fileType){
		return new ElementList<Element>(driver,getBy(getLocator(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()).replace("FILETYPE", fileType)));
	}
	
	public Button createbuttonfile(WebDriver driver){
		return new Button(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
	
	public Button errorpaneldeletebutton(WebDriver driver){
		return new Button(driver,getBy(getClassName(),Thread.currentThread().getStackTrace()[1].getMethodName()));
	}
}
