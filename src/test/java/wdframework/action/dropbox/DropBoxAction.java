package wdframework.action.dropbox;

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
import wdframework.constants.dropbox.DropBoxConstants;
import wdframework.constants.onedrive.OneDriveConstants;
import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.DropBoxPage;
import wdframework.pageobjects.OneDrivePage;
import wdframework.webelements.CheckBox;
import wdframework.webelements.Element;
import wdframework.webelements.HyperLink;

/**
 * Dropbox Action Class
 * @author Eldo Rajan
 *
 */
@SuppressWarnings("unused")
public class DropBoxAction extends Action{

	/**
	 * File type enum type
	 * @author Eldo Rajan
	 *
	 */
	public enum FileType {

		Folder("folder"),
		Document("document");

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
			if (fileType.equalsIgnoreCase("Folder")||fileType.equalsIgnoreCase("folder")) {
				fType=FileType.Folder;
			} else if (fileType.equalsIgnoreCase("Document")||fileType.equalsIgnoreCase("document")) {
				fType=FileType.Document;
			} 
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return fType;
	}

	/**
	 * login into dropbox
	 * @param driver
	 * @param username
	 * @param password
	 */
	public void login(WebDriver driver,String username,String password){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			Assert.assertTrue(dbp.username(driver).isElementVisible());
			Assert.assertTrue(dbp.password(driver).isElementVisible());
			Assert.assertTrue(dbp.loginbutton(driver).isElementVisible());

			dbp.username(driver).clear();dbp.username(driver).type(username);
			dbp.password(driver).clear();dbp.password(driver).type(password);
			dbp.loginbutton(driver).click();

			Thread.sleep(10000);

			dbp.logo(driver).waitForElementPresent(driver);
			dbp.logo(driver).waitForElementToBeVisible(driver);

			Assert.assertTrue(dbp.logo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains("Home - Dropbox"));

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * create folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void createFolder(WebDriver driver, String optionType, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver,"folder");

			clickDropboxMenuActionsLink(driver, DropBoxConstants.NewFolder);
			typeDropBoxFolderTextbox(driver,folderName);	
			clickCreateDropBoxFolderButton(driver);

			int countAfter = getFolderFileCount(driver,"folder");	

			Assert.assertTrue(countAfter==countBefore+1,"Folder creation failed as new folder is not created");
			String[] folderNames = getFolderFileNames(driver);	
			Assert.assertTrue(Arrays.asList(folderNames).contains(folderName),"Folder creation failed for folder name:"+folderName);

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void downloadFolder(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			File dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			FileFilter fileFilter = new WildcardFileFilter(folderName+"*.zip");
			int fileCountBefore = dir.listFiles(fileFilter).length;

			rightClickOnFolderName(driver,folderName);
			clickFolderRightClickOptions(driver,"Download");
			clickDeleteButton(driver);		

			dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			fileFilter = new WildcardFileFilter(folderName+"*.zip");
			int fileCountAfter = dir.listFiles(fileFilter).length;			
			Assert.assertTrue(fileCountAfter==fileCountBefore+1,"Folder downloaded failed as folder "+folderName+" is not downloaded");
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void deleteFolder(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver,"folder");

			rightClickOnFolderName(driver,folderName);
			clickFolderRightClickOptions(driver,"Delete");
			clickDeleteButton(driver);
			int countAfter = getFolderFileCount(driver,"folder");	

			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	public void uploadFile(WebDriver driver, String optionType, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		FileUploadAction fu = new FileUploadAction();
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver,"document");

			clickDropboxMenuActionsLink(driver, DropBoxConstants.UploadFiles);
			clickChooseFilesButton(driver);	
			if(driver instanceof FirefoxDriver){
				fu.autoitFileUploadFirefox(fileName);
			}else{
				fu.autoitFileUploadChrome(fileName);
			}

			int countAfter = getFolderFileCount(driver,"document");	

			Assert.assertTrue(countAfter==countBefore+1,"File creation failed as new file is not created");
			String[] folderNames = getFolderFileNames(driver);	
			Assert.assertTrue(Arrays.asList(folderNames).contains(fileName),"File creation failed for file name:"+fileName);

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void downloadFile(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			File dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			FileFilter fileFilter = new WildcardFileFilter("Test*.docx");
			int fileCountBefore = dir.listFiles(fileFilter).length;

			rightClickOnFolderName(driver,fileName);
			clickFolderRightClickOptions(driver,"Download");
			clickDeleteButton(driver);		

			dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			fileFilter = new WildcardFileFilter("Test*.docx");
			int fileCountAfter = dir.listFiles(fileFilter).length;	
			Assert.assertTrue(fileCountAfter==fileCountBefore+1,"File downloaded failed as file "+fileName+" is not downloaded");
		}catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void deleteFile(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver,"document");

			rightClickOnFolderName(driver,fileName);
			clickFolderRightClickOptions(driver,"Delete");
			clickDeleteButton(driver);
			int countAfter = getFolderFileCount(driver,"document");	

			Assert.assertTrue(countAfter==countBefore-1,"File deletion failed as new file is not deleted");
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public int getFolderFileCount(WebDriver driver,String itemType) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> getdropboxfilefoldercount  = dbp.getdropboxfilefoldercount(driver).getChildElements();
		int count=0;		
		for(int i=0;i<getdropboxfilefoldercount.size();i++){
			if(getdropboxfilefoldercount.get(i).getText().trim().equalsIgnoreCase(itemType)){
				count++;
			}
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return count;	
	}

	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public String[] getFolderFileNames(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> getdropboxfilefoldertitle  = dbp.getdropboxfilefoldertitle(driver).getChildElements();
		int count=0; String[] folderFileNames = new String[getdropboxfilefoldertitle.size()];
		for(int i=0;i<getdropboxfilefoldertitle.size();i++){
			folderFileNames[i] = getdropboxfilefoldertitle.get(i).getText().trim();
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return folderFileNames;	
	}



	public void clickChooseFilesButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try{
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
			List<Element> dropboxchoosefilebutton  = dbp.dropboxchoosefilebutton(driver).getChildElements();
			for(int i=0;i<dropboxchoosefilebutton.size();i++){
				dropboxchoosefilebutton.get(i).click();
				Thread.sleep(5000);
				break;
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	public void rightClickOnFolderName(WebDriver driver,String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> getdropboxfilefoldertitle  = dbp.getdropboxfilefoldertitle(driver).getChildElements();
		for(int i=0;i<getdropboxfilefoldertitle.size();i++){
			if(getdropboxfilefoldertitle.get(i).getText().trim().equalsIgnoreCase(folderName)){
				getdropboxfilefoldertitle.get(i).mouseOverRightClick(driver);
				break;
			}
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	public void clickDeleteButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
			List<Element> dropboxdeletefolderbutton  = dbp.dropboxdeletefolderbutton(driver).getChildElements();
			for(int i=0;i<dropboxdeletefolderbutton.size();i++){
				if(dropboxdeletefolderbutton.get(i).getText().trim().equalsIgnoreCase("Delete")){
					dropboxdeletefolderbutton.get(i).click();
					Thread.sleep(10000);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private void clickFolderRightClickOptions(WebDriver driver, String optionType) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try{
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			List<Element> dropboxfilefolderrightclickoptions  = dbp.dropboxfilefolderrightclickoptions(driver).getChildElements();		
			for(int i=0;i<dropboxfilefolderrightclickoptions.size();i++){
				if(dropboxfilefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
					dropboxfilefolderrightclickoptions.get(i).click();
					Thread.sleep(10000);
					break;
				}
			}
		}catch(Exception e){
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private void clickCreateDropBoxFolderButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			dbp.dropboxcreatefolderbutton(driver).click();
			Thread.sleep(10000);	

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private void typeDropBoxFolderTextbox(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			dbp.dropboxcreatefoldernametextbox(driver).clear();
			dbp.dropboxcreatefoldernametextbox(driver).type(folderName);
			Thread.sleep(2000);	

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private void clickDropboxMenuActionsLink(WebDriver driver, String actionName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			dbp.dropboxmenuactionslink(driver, actionName).click();
			Thread.sleep(10000);	

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}





}
