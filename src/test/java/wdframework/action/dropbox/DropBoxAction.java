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
import wdframework.constants.dropbox.DropBoxConstants;
import wdframework.constants.onedrive.OneDriveConstants;
import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.DropBoxPage;
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
public class DropBoxAction{

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
			if (fileType.equalsIgnoreCase("Folder")) {
				fType=FileType.Folder;
			} else if (fileType.equalsIgnoreCase("document")) {
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

			/*if(dbp.closeoverlayicon(driver).isElementPresent(driver)){
				if(dbp.closeoverlayicon(driver).isElementVisible()){
					dbp.closeoverlayicon(driver).click();
					Thread.sleep(2000);
				}
			}*/



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public int getFolderFileCount(WebDriver driver,String itemType) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> getdropboxfilefoldercount  = dbp.getdropboxfilefoldercount(driver).getChildElements();
		int count=0;		
		for(int i=0;i<getdropboxfilefoldercount.size();i++){
			if(getdropboxfilefoldercount.get(i).getText().trim().equalsIgnoreCase(itemType)){
				count++;
			}
		}
		return count;	
	}

	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public String[] getFolderFileNames(WebDriver driver) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> getdropboxfilefoldertitle  = dbp.getdropboxfilefoldertitle(driver).getChildElements();
		int count=0; String[] folderFileNames = new String[getdropboxfilefoldertitle.size()];
		for(int i=0;i<getdropboxfilefoldertitle.size();i++){
			folderFileNames[i] = getdropboxfilefoldertitle.get(i).getText().trim();
		}
		return folderFileNames;	
	}

	/**
	 * create folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void createFolder(WebDriver driver, String optionType, String folderName) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void downloadFolder(WebDriver driver, String folderName) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void deleteFolder(WebDriver driver, String folderName) {
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver,"folder");

			rightClickOnFolderName(driver,folderName);
			clickFolderRightClickOptions(driver,"Delete");
			clickDeleteButton(driver);
			int countAfter = getFolderFileCount(driver,"folder");	

			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void uploadFile(WebDriver driver, String optionType, String fileName) {
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver,"document");

			clickDropboxMenuActionsLink(driver, DropBoxConstants.UploadFiles);
			clickChooseFilesButton(driver);	
			if(driver instanceof FirefoxDriver){
				autoitFileUploadFirefox(fileName);
			}else{
				autoitFileUploadChrome(fileName);
			}

			int countAfter = getFolderFileCount(driver,"document");	

			Assert.assertTrue(countAfter==countBefore+1,"File creation failed as new file is not created");
			String[] folderNames = getFolderFileNames(driver);	
			Assert.assertTrue(Arrays.asList(folderNames).contains(fileName),"File creation failed for file name:"+fileName);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	
	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void downloadFile(WebDriver driver, String fileName) {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	/**
	 * delete folder
	 * @param driver
	 * @param itemType
	 * @param folderName
	 */
	public void deleteFile(WebDriver driver, String fileName) {
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver,"document");

			rightClickOnFolderName(driver,fileName);
			clickFolderRightClickOptions(driver,"Delete");
			clickDeleteButton(driver);
			int countAfter = getFolderFileCount(driver,"document");	

			Assert.assertTrue(countAfter==countBefore-1,"File deletion failed as new file is not deleted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void clickChooseFilesButton(WebDriver driver) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> dropboxchoosefilebutton  = dbp.dropboxchoosefilebutton(driver).getChildElements();
		for(int i=0;i<dropboxchoosefilebutton.size();i++){
			dropboxchoosefilebutton.get(i).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}


	}
	

	public void rightClickOnFolderName(WebDriver driver,String folderName) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> getdropboxfilefoldertitle  = dbp.getdropboxfilefoldertitle(driver).getChildElements();
		for(int i=0;i<getdropboxfilefoldertitle.size();i++){
			if(getdropboxfilefoldertitle.get(i).getText().trim().equalsIgnoreCase(folderName)){
				getdropboxfilefoldertitle.get(i).mouseOverRightClick(driver);
				break;
			}

		}


	}

	public void clickDeleteButton(WebDriver driver) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> dropboxdeletefolderbutton  = dbp.dropboxdeletefolderbutton(driver).getChildElements();
		for(int i=0;i<dropboxdeletefolderbutton.size();i++){
			if(dropboxdeletefolderbutton.get(i).getText().trim().equalsIgnoreCase("Delete")){
				dropboxdeletefolderbutton.get(i).click();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

		}


	}

	private void clickFolderRightClickOptions(WebDriver driver, String optionType) throws InterruptedException {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

		List<Element> dropboxfilefolderrightclickoptions  = dbp.dropboxfilefolderrightclickoptions(driver).getChildElements();		
		for(int i=0;i<dropboxfilefolderrightclickoptions.size();i++){
			if(dropboxfilefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
				dropboxfilefolderrightclickoptions.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	private void clickCreateDropBoxFolderButton(WebDriver driver) {
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			dbp.dropboxcreatefolderbutton(driver).click();
			Thread.sleep(10000);	

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void typeDropBoxFolderTextbox(WebDriver driver, String folderName) {

		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			dbp.dropboxcreatefoldernametextbox(driver).clear();
			dbp.dropboxcreatefoldernametextbox(driver).type(folderName);
			Thread.sleep(2000);	

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clickDropboxMenuActionsLink(WebDriver driver, String actionName) {
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			dbp.dropboxmenuactionslink(driver, actionName).click();
			Thread.sleep(10000);	

		} catch (InterruptedException e) {
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
	
	
	/*

	public void uploadFile(WebDriver driver, String optionType, String fileName) {
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

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
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

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
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

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


	 *//**
	 * autoit file upload chrome
	 * @param fileName
	 *//*
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

	  *//**
	  * autoit file upload firefox
	  * @param fileName
	  *//*
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
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		try {
			List<Element> mydrivefoldertextbox  = dbp.mydrivefoldertextbox(driver).getChildElements();		
			if(mydrivefoldertextbox.size()==3){
				mydrivefoldertextbox.get(mydrivefoldertextbox.size()-1).clear();
				mydrivefoldertextbox.get(mydrivefoldertextbox.size()-1).type(folderName);
				dbp.mydrivefoldercreatebutton(driver).click();
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	   *//**
	   * Click on my drive button
	   * @param driver
	   * @param itemType
	   * @throws InterruptedException
	   *//*
	private void clickMyDriveButton(WebDriver driver, String itemType) throws InterruptedException {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

		List<Element> mydrivebutton  = dbp.mydrivebutton(driver).getChildElements();		
		for(int i=0;i<mydrivebutton.size();i++){
			if(mydrivebutton.get(i).getText().trim().contains(itemType)){
				mydrivebutton.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	    *//**
	    * Click on create/upload link
	    * @param driver
	    * @param itemType
	    * @throws InterruptedException
	    *//*
	private void clickCreateOptionType(WebDriver driver, String optionType) throws InterruptedException {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

		List<Element> mydrivebuttondropdown  = dbp.mydrivebuttondropdown(driver).getChildElements();		
		for(int i=0;i<mydrivebuttondropdown.size();i++){
			if(mydrivebuttondropdown.get(i).getText().trim().contains(optionType)){
				mydrivebuttondropdown.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}



	     *//**
	     * get folder count
	     * @param driver
	     * @return
	     *//*
	public int getFileCount(WebDriver driver) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> mydrivefileelement  = dbp.mydrivefileelement(driver).getChildElements();
		return mydrivefileelement.size();	
	}



	      *//**
	      * get all folder names
	      * @param driver
	      * @return
	      *//*
	public void rightClickOnFolderName(WebDriver driver,String folderName) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> mydrivefolderelement  = dbp.mydrivefolderelement(driver,folderName).getChildElements();
		String[] list = new String[mydrivefolderelement.size()];
		for(int i=0;i<mydrivefolderelement.size();i++){
			mydrivefolderelement.get(i).mouseOverRightClick(driver);
			break;
		}


	}

	private void clickFolderRightClickOptions(WebDriver driver, String optionType) throws InterruptedException {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

		List<Element> mydrivefolderrightclickoptions  = dbp.mydrivefolderrightclickoptions(driver).getChildElements();		
		for(int i=0;i<mydrivefolderrightclickoptions.size();i++){
			if(mydrivefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
				mydrivefolderrightclickoptions.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	       *//**
	       * get all folder names
	       * @param driver
	       * @return
	       *//*
	public void rightClickOnFileName(WebDriver driver,String fileName) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> mydrivetypefileelement  = dbp.mydrivetypefileelement(driver,fileName).getChildElements();
		String[] list = new String[mydrivetypefileelement.size()];
		for(int i=0;i<mydrivetypefileelement.size();i++){
			mydrivetypefileelement.get(i).mouseOverRightClick(driver);
			break;
		}


	}

	private void clickFileRightClickOptions(WebDriver driver, String optionType) throws InterruptedException {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

		List<Element> mydrivefolderrightclickoptions  = dbp.mydrivefolderrightclickoptions(driver).getChildElements();		
		for(int i=0;i<mydrivefolderrightclickoptions.size();i++){
			if(mydrivefolderrightclickoptions.get(i).getText().trim().contains(optionType)){
				mydrivefolderrightclickoptions.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	        *//**
	        * get folder count
	        * @param driver
	        * @return
	        *//*
	public int getFileCount(WebDriver driver,String fileName) {
		DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);
		List<Element> mydrivetypefileelement  = dbp.mydrivetypefileelement(driver, fileName).getChildElements();
		return mydrivetypefileelement.size();	
	}


	         */
}
