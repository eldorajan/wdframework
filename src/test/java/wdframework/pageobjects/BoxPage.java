package wdframework.pageobjects;

import org.openqa.selenium.WebDriver;

import wdframework.webelements.BasePage;
import wdframework.webelements.Button;
import wdframework.webelements.Element;
import wdframework.webelements.ElementList;
import wdframework.webelements.HyperLink;
import wdframework.webelements.TextArea;
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
	
	public Button usernamebuttonintopbar(WebDriver driver){
		return new Button(driver,getLocator());
	}
	
	public ElementList<Element> usernamebuttonintopbardropdown(WebDriver driver){
		return new ElementList<Element>(driver,getLocator());
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
		
	public ElementList<Element> boxfolderfileelementactionshare(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementactionunfavorite(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementactionfavorite(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementcreationinfolist(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementcreationinfo(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementcreatorinfo(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementfilecount(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementsharedmenulink(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public HyperLink boxfoldersharepopup(WebDriver driver) {
		return new HyperLink(driver,getLocator());
	}
	
	public HyperLink boxfoldersharepopupclose(WebDriver driver) {
		return new HyperLink(driver,getLocator());
	}
	
	public Button boxfoldersharepopupclosebutton(WebDriver driver) {
		return new Button(driver,getLocator());
	}
	
	public TextBox boxfoldersharepopupsharelink(WebDriver driver) {
		return new TextBox(driver,getLocator());
	}
	
	public ElementList<Element> boxfoldersharepopupbuttons(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public TextArea boxfolderemaillinkmessage(WebDriver driver) {
		return new TextArea(driver,getLocator());
	}
	
	public TextBox boxfolderemailtypeaddress(WebDriver driver) {
		return new TextBox(driver,getLocator());
	}
	
	public TextArea boxfolderembedcode(WebDriver driver) {
		return new TextArea(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementfilesize(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public ElementList<Element> boxfolderfileelementaddcomment(WebDriver driver) {
		return new ElementList<Element>(driver,getLocator());
	}
	
	public HyperLink boxpreview(WebDriver driver) {
		return new HyperLink(driver,getLocator());
	}
	
	public Button boxpreviewclose(WebDriver driver) {
		return new Button(driver,getLocator());
	}
		
}
