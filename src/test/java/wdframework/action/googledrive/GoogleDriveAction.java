package wdframework.action.googledrive;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import wdframework.driver.BrowserType;
import wdframework.logger.Logger;
import wdframework.action.Action;
import wdframework.action.fileupload.FileUploadAction;
import wdframework.constants.onedrive.OneDriveConstants;
import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.GoogleDrivePage;
import wdframework.pageobjects.OneDrivePage;
import wdframework.webelements.CheckBox;
import wdframework.webelements.Element;
import wdframework.webelements.HyperLink;

/**
 * Google Drive Action Class
 * @author Eldo Rajan
 *
 */
@SuppressWarnings("unused")
public class GoogleDriveAction extends Action{

	/**
	 * File type enum type
	 * @author Eldo Rajan
	 *
	 */
	public enum FileType {

		Docs("Google Docs"),
		Sheets("Google Sheets"),
		Slides("Google Slides"),
		Forms("Google Forms"),
		Drawings("Google Drawings"),
		Maps("Google My Maps");

		public String fileType;

		FileType(String fileType) {
			this.fileType = fileType;
		}

		public String displayName() { return fileType; }

		// Optionally and/or additionally, toString.
		@Override public String toString() { return fileType; }
	}

	/**
	 * get file type
	 * @param fileType
	 * @return
	 */
	public static FileType getFileType(String fileType) {
		FileType fType=null;
		try{
			if (fileType.equalsIgnoreCase("Google Docs")) {
				fType=FileType.Docs;
			} else if (fileType.equalsIgnoreCase("Google Sheets")) {
				fType=FileType.Sheets;
			} else if (fileType.equalsIgnoreCase("Google Slides")) {
				fType=FileType.Slides;
			} else if (fileType.equalsIgnoreCase("Google Forms")) {
				fType=FileType.Forms;
			} else if (fileType.equalsIgnoreCase("Google Drawings")) {
				fType=FileType.Drawings;
			} else if (fileType.equalsIgnoreCase("Google My Maps")) {
				fType=FileType.Maps;
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return fType;
	}

	/**
	 * login into googledrive
	 * @param driver
	 * @param username
	 * @param password
	 */
	public void login(WebDriver driver,String username,String password){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			Assert.assertTrue(gdp.username(driver).isElementVisible());
			Assert.assertTrue(gdp.password(driver).isElementVisible());
			Assert.assertTrue(gdp.loginbutton(driver).isElementVisible());

			gdp.username(driver).clear();gdp.username(driver).type(username);
			gdp.password(driver).clear();gdp.password(driver).type(password);
			gdp.loginbutton(driver).click();

			Thread.sleep(10000);

			gdp.logo(driver).waitForElementPresent(driver);
			gdp.logo(driver).waitForElementToBeVisible(driver);

			Assert.assertTrue(gdp.logo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains("My Drive - Google Drive"));

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * create folder
	 * @param driver
	 * @param optionType
	 * @param folderName
	 */
	public void createFolder(WebDriver driver, String optionType, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			int countBefore = getFolderCount(driver,folderName);

			clickMyDriveButton(driver, "My Drive");
			clickCreateOptionType(driver, optionType);
			typeMyDriveFolderTextbox(driver,folderName);			

			int countAfter = getFolderCount(driver,folderName);	

			Assert.assertTrue(countAfter==countBefore+1,"Folder creation failed as new folder is not created");
			String[] folderNames = getAllFolderNames(driver,folderName);	
			Assert.assertTrue(Arrays.asList(folderNames).contains(folderName),"Folder creation failed for folder name:"+folderName);

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void deleteFolder(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			int countBefore = getFolderCount(driver,folderName);
			rightClickOnFolderName(driver,folderName);
			clickFolderRightClickOptions(driver,"Remove");

			int countAfter = getFolderCount(driver,folderName);	

			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * upload file
	 * @param driver
	 * @param optionType
	 * @param fileName
	 */
	public void uploadFile(WebDriver driver, String optionType, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		FileUploadAction fu = new FileUploadAction();

		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			int countBefore = getFileCount(driver);

			clickMyDriveButton(driver, "My Drive");
			clickCreateOptionType(driver, optionType);
			if(driver instanceof FirefoxDriver){
				fu.autoitFileUploadFirefox(fileName);
			}else{
				fu.autoitFileUploadChrome(fileName);
			}
			int countAfter = getFileCount(driver);

			Assert.assertTrue(countAfter==countBefore+1,"File upload failed as new folder is not uploaded");
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * download file
	 * @param driver
	 * @param fileName
	 */
	public void downloadFile(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			File dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			FileFilter fileFilter = new WildcardFileFilter("Test*.docx");
			int fileCountBefore = dir.listFiles(fileFilter).length;

			rightClickOnFileName(driver,fileName);
			clickFileRightClickOptions(driver,"Download");

			dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			fileFilter = new WildcardFileFilter("Test*.docx");
			int fileCountAfter = dir.listFiles(fileFilter).length;			
			Assert.assertTrue(fileCountAfter==fileCountBefore+1,"File downloaded failed as file "+fileName+" is not downloaded");

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * delete file
	 * @param driver
	 * @param fileName
	 */
	public void deleteFile(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			int countBefore = getFileCount(driver,fileName);

			rightClickOnFileName(driver,fileName);
			clickFileRightClickOptions(driver,"Remove");

			int countAfter = getFileCount(driver,fileName);	

			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * type my drive folder text box
	 * @param driver
	 * @param folderName
	 */
	public void typeMyDriveFolderTextbox(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		try {
			List<Element> mydrivefoldertextbox  = gdp.mydrivefoldertextbox(driver).getChildElements();		
			if(mydrivefoldertextbox.size()==3){
				mydrivefoldertextbox.get(mydrivefoldertextbox.size()-1).clear();
				mydrivefoldertextbox.get(mydrivefoldertextbox.size()-1).type(folderName);
				gdp.mydrivefoldercreatebutton(driver).click();
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * Click on my drive button
	 * @param driver
	 * @param itemType
	 * @throws InterruptedException
	 */
	public void clickMyDriveButton(WebDriver driver, String itemType){

		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try{
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			List<Element> mydrivebutton  = gdp.mydrivebutton(driver).getChildElements();		
			for(int i=0;i<mydrivebutton.size();i++){
				if(mydrivebutton.get(i).getText().trim().contains(itemType)){
					mydrivebutton.get(i).click();
					Thread.sleep(10000);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * click create option type
	 * @param driver
	 * @param optionType
	 */
	public void clickCreateOptionType(WebDriver driver, String optionType){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try{
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			List<Element> mydrivebuttondropdown  = gdp.mydrivebuttondropdown(driver).getChildElements();		
			for(int i=0;i<mydrivebuttondropdown.size();i++){
				if(mydrivebuttondropdown.get(i).getText().trim().contains(optionType)){
					mydrivebuttondropdown.get(i).click();
					Thread.sleep(10000);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * get folder count
	 * @param driver
	 * @param folderName
	 * @return
	 */
	public int getFolderCount(WebDriver driver,String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefolderelement  = gdp.mydrivefolderelement(driver,folderName).getChildElements();

		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return mydrivefolderelement.size();	
	}

	/**
	 * get file count
	 * @param driver
	 * @return
	 */
	public int getFileCount(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefileelement  = gdp.mydrivefileelement(driver).getChildElements();

		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return mydrivefileelement.size();	
	}

	/**
	 * get all folder names
	 * @param driver
	 * @param folderName
	 * @return
	 */
	public String[] getAllFolderNames(WebDriver driver,String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefolderelementtitle  = gdp.mydrivefolderelementtitle(driver,folderName).getChildElements();
		String[] list = new String[mydrivefolderelementtitle.size()];
		for(int i=0;i<mydrivefolderelementtitle.size();i++){
			list[i] = mydrivefolderelementtitle.get(i).getText().trim();
		}

		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return list;	
	}

	/**
	 * right click on folder name
	 * @param driver
	 * @param folderName
	 */
	public void rightClickOnFolderName(WebDriver driver,String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefolderelement  = gdp.mydrivefolderelement(driver,folderName).getChildElements();
		String[] list = new String[mydrivefolderelement.size()];
		for(int i=0;i<mydrivefolderelement.size();i++){
			mydrivefolderelement.get(i).mouseOverRightClick(driver);
			break;
		}

		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click folder right click options
	 * @param driver
	 * @param optionType
	 */
	public void clickFolderRightClickOptions(WebDriver driver, String optionType){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try{
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			List<Element> mydrivefolderrightclickoptions  = gdp.mydrivefolderrightclickoptions(driver).getChildElements();		
			for(int i=0;i<mydrivefolderrightclickoptions.size();i++){
				if(mydrivefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
					mydrivefolderrightclickoptions.get(i).click();
					Thread.sleep(10000);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * right click on file name
	 * @param driver
	 * @param fileName
	 */
	public void rightClickOnFileName(WebDriver driver,String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivetypefileelement  = gdp.mydrivetypefileelement(driver,fileName).getChildElements();
		String[] list = new String[mydrivetypefileelement.size()];
		for(int i=0;i<mydrivetypefileelement.size();i++){
			mydrivetypefileelement.get(i).mouseOverRightClick(driver);
			break;
		}

		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click file right click options
	 * @param driver
	 * @param optionType
	 */
	public void clickFileRightClickOptions(WebDriver driver, String optionType){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try{
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			List<Element> mydrivefolderrightclickoptions  = gdp.mydrivefolderrightclickoptions(driver).getChildElements();		
			for(int i=0;i<mydrivefolderrightclickoptions.size();i++){
				if(mydrivefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
					mydrivefolderrightclickoptions.get(i).click();
					Thread.sleep(10000);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * get file name
	 * @param driver
	 * @param fileName
	 * @return
	 */
	public int getFileCount(WebDriver driver,String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivetypefileelement  = gdp.mydrivetypefileelement(driver, fileName).getChildElements();
		
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return mydrivetypefileelement.size();	
	}



}
