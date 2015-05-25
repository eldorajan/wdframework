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
import wdframework.constants.onedrive.OneDriveConstants;
import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.GoogleDrivePage;
import wdframework.pageobjects.OneDrivePage;
import wdframework.webelements.CheckBox;
import wdframework.webelements.Element;
import wdframework.webelements.HyperLink;

/**
 * One Drive Action Class
 * @author Eldo Rajan
 *
 */
@SuppressWarnings("unused")
public class GoogleDriveAction{

	/**
	 * File type enum type
	 * @author Eldo Rajan
	 *
	 */
	public enum FileType {

		Word("Word document"),
		Excel("Excel workbook"),
		PowerPoint("PowerPoint presentation"),
		OneNote("OneNote notebook"),
		ExcelSurvey("Excel survey"),
		PlainText("Plain text document");

		private String fileType;

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
			if (fileType.equalsIgnoreCase("Word document")) {
				fType=FileType.Word;
			} else if (fileType.equalsIgnoreCase("Excel workbook")) {
				fType=FileType.Excel;
			} else if (fileType.equalsIgnoreCase("PowerPoint presentation")) {
				fType=FileType.PowerPoint;
			} else if (fileType.equalsIgnoreCase("OneNote notebook")) {
				fType=FileType.OneNote;
			} else if (fileType.equalsIgnoreCase("Excel survey")) {
				fType=FileType.ExcelSurvey;
			} else if (fileType.equalsIgnoreCase("Plain text document")) {
				fType=FileType.PlainText;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * create folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void createFolder(WebDriver driver, String optionType, String folderName) {
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

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void deleteFolder(WebDriver driver, String itemType, String folderName) {
		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			int countBefore = getFolderCount(driver,folderName);
			rightClickOnFolderName(driver,folderName);
			clickFolderRightClickOptions(driver,"Remove");

			int countAfter = getFolderCount(driver,folderName);	

			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void uploadFile(WebDriver driver, String optionType, String fileName) {
		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			int countBefore = getFileCount(driver);

			clickMyDriveButton(driver, "My Drive");
			clickCreateOptionType(driver, optionType);
			if(driver instanceof FirefoxDriver){
				autoitFileUploadFirefox(fileName);
			}else{
				autoitFileUploadChrome(fileName);
			}
			int countAfter = getFileCount(driver);;	

			Assert.assertTrue(countAfter==countBefore+1,"File upload failed as new folder is not uploaded");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void downloadFile(WebDriver driver, String fileName) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void deleteFile(WebDriver driver, String fileName) {
		try {
			GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

			int countBefore = getFileCount(driver,fileName);
			
			rightClickOnFileName(driver,fileName);
			clickFileRightClickOptions(driver,"Remove");
			
			int countAfter = getFileCount(driver,fileName);	

			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	/**
	 * autoit file upload chrome
	 * @param fileName
	 */
	public void autoitFileUploadChrome(String fileName){

		File fileLocation   =  new File(System.getProperty("user.dir")+File.separator+"src/test/resources/data");
		String absolutePath =   fileLocation.getAbsolutePath() + "\\" + fileName;
		File exeLocation 	= new File("src/test/resources/utils/fileuploadchrome.exe");
		String exeAbsolutePath  =  exeLocation.getAbsolutePath().replace("\\", "\\\\");                    
		try {
			new ProcessBuilder(exeAbsolutePath,
					absolutePath, "Open").start();
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	/**
	 * autoit file upload firefox
	 * @param fileName
	 */
	public void autoitFileUploadFirefox(String fileName){

		File fileLocation   =  new File(System.getProperty("user.dir")+File.separator+"src/test/resources/data");
		String absolutePath =   fileLocation.getAbsolutePath() + "\\" + fileName;
		File exeLocation 	= new File("src/test/resources/utils/fileuploadfirefox.exe");
		String exeAbsolutePath  =  exeLocation.getAbsolutePath().replace("\\", "\\\\");                    
		try {
			new ProcessBuilder(exeAbsolutePath,
					absolutePath, "Open").start();
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	private void typeMyDriveFolderTextbox(WebDriver driver, String folderName) {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		try {
			List<Element> mydrivefoldertextbox  = gdp.mydrivefoldertextbox(driver).getChildElements();		
			if(mydrivefoldertextbox.size()==3){
				mydrivefoldertextbox.get(mydrivefoldertextbox.size()-1).clear();
				mydrivefoldertextbox.get(mydrivefoldertextbox.size()-1).type(folderName);
				gdp.mydrivefoldercreatebutton(driver).click();
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Click on my drive button
	 * @param driver
	 * @param itemType
	 * @throws InterruptedException
	 */
	private void clickMyDriveButton(WebDriver driver, String itemType) throws InterruptedException {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

		List<Element> mydrivebutton  = gdp.mydrivebutton(driver).getChildElements();		
		for(int i=0;i<mydrivebutton.size();i++){
			if(mydrivebutton.get(i).getText().trim().contains(itemType)){
				mydrivebutton.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	/**
	 * Click on create/upload link
	 * @param driver
	 * @param itemType
	 * @throws InterruptedException
	 */
	private void clickCreateOptionType(WebDriver driver, String optionType) throws InterruptedException {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

		List<Element> mydrivebuttondropdown  = gdp.mydrivebuttondropdown(driver).getChildElements();		
		for(int i=0;i<mydrivebuttondropdown.size();i++){
			if(mydrivebuttondropdown.get(i).getText().trim().contains(optionType)){
				mydrivebuttondropdown.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public int getFolderCount(WebDriver driver,String folderName) {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefolderelement  = gdp.mydrivefolderelement(driver,folderName).getChildElements();
		return mydrivefolderelement.size();	
	}

	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public int getFileCount(WebDriver driver) {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefileelement  = gdp.mydrivefileelement(driver).getChildElements();
		return mydrivefileelement.size();	
	}

	/**
	 * get all folder names
	 * @param driver
	 * @return
	 */
	public String[] getAllFolderNames(WebDriver driver,String folderName) {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefolderelementtitle  = gdp.mydrivefolderelementtitle(driver,folderName).getChildElements();
		String[] list = new String[mydrivefolderelementtitle.size()];
		for(int i=0;i<mydrivefolderelementtitle.size();i++){
			list[i] = mydrivefolderelementtitle.get(i).getText().trim();
		}

		return list;	
	}

	/**
	 * get all folder names
	 * @param driver
	 * @return
	 */
	public void rightClickOnFolderName(WebDriver driver,String folderName) {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivefolderelement  = gdp.mydrivefolderelement(driver,folderName).getChildElements();
		String[] list = new String[mydrivefolderelement.size()];
		for(int i=0;i<mydrivefolderelement.size();i++){
			mydrivefolderelement.get(i).mouseOverRightClick(driver);
			break;
		}


	}

	private void clickFolderRightClickOptions(WebDriver driver, String optionType) throws InterruptedException {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

		List<Element> mydrivefolderrightclickoptions  = gdp.mydrivefolderrightclickoptions(driver).getChildElements();		
		for(int i=0;i<mydrivefolderrightclickoptions.size();i++){
			if(mydrivefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
				mydrivefolderrightclickoptions.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}
	
	/**
	 * get all folder names
	 * @param driver
	 * @return
	 */
	public void rightClickOnFileName(WebDriver driver,String fileName) {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivetypefileelement  = gdp.mydrivetypefileelement(driver,fileName).getChildElements();
		String[] list = new String[mydrivetypefileelement.size()];
		for(int i=0;i<mydrivetypefileelement.size();i++){
			mydrivetypefileelement.get(i).mouseOverRightClick(driver);
			break;
		}


	}

	private void clickFileRightClickOptions(WebDriver driver, String optionType) throws InterruptedException {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);

		List<Element> mydrivefolderrightclickoptions  = gdp.mydrivefolderrightclickoptions(driver).getChildElements();		
		for(int i=0;i<mydrivefolderrightclickoptions.size();i++){
			if(mydrivefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
				mydrivefolderrightclickoptions.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public int getFileCount(WebDriver driver,String fileName) {
		GoogleDrivePage gdp =  AdvancedPageFactory.getPageObject(driver,GoogleDrivePage.class);
		List<Element> mydrivetypefileelement  = gdp.mydrivetypefileelement(driver, fileName).getChildElements();
		return mydrivetypefileelement.size();	
	}
	


}
