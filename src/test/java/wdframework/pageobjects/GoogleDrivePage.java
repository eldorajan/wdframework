package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;

import wdframework.webelements.BasePage;
import wdframework.webelements.Button;
import wdframework.webelements.Element;
import wdframework.webelements.ElementList;
import wdframework.webelements.HyperLink;
import wdframework.webelements.TextBox;

/**
 * Google Drive page
 * @author Eldo Rajan
 *
 */
public class GoogleDrivePage extends BasePage{

	

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
	
	public HyperLink usernamebuttonintopbar(WebDriver driver,String username){
		return new HyperLink(driver,getLocator().replace("ACCOUNT_NAME", username));
	}
	
	public HyperLink usernamebuttonintopbardropdown(WebDriver driver){
		return new HyperLink(driver,getLocator());
	}
	
	public ElementList<Element> mydrivebutton(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> mydrivebuttondropdown(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> mydrivefolderelement(WebDriver driver,String folderName){
		return new ElementList<Element>(driver,getLocator().replace("FOLDER_NAME", folderName));
	}
	
	public ElementList<Element> mydrivefolderelementtitle(WebDriver driver,String folderName){
		return new ElementList<Element>(driver,getLocator().replace("FOLDER_NAME", folderName));
	}
	
	public ElementList<Element> mydrivefileelement(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> mydrivefoldertextbox(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public Button mydrivefoldercreatebutton(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> mydrivefolderrightclickoptions(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> mydrivetypefileelement(WebDriver driver,String folderName){
		return new ElementList<Element>(driver,getLocator().replace("FILE_NAME", folderName));
	}
	
	public ElementList<Element> mydrivetypefileelementtitle(WebDriver driver,String folderName){
		return new ElementList<Element>(driver,getLocator().replace("FILE_NAME", folderName));
	}


	
}
