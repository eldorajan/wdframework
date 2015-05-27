package wdframework.action.box;

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
import wdframework.constants.box.BoxConstants;
import wdframework.constants.dropbox.DropBoxConstants;
import wdframework.constants.onedrive.OneDriveConstants;
import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.BoxPage;
import wdframework.pageobjects.DropBoxPage;
import wdframework.pageobjects.GoogleDrivePage;
import wdframework.pageobjects.OneDrivePage;
import wdframework.webelements.CheckBox;
import wdframework.webelements.Element;
import wdframework.webelements.HyperLink;

/**
 * Box Action Class
 * @author Eldo Rajan
 *
 */
@SuppressWarnings("unused")
public class BoxAction extends Action{

	/**
	 * File type enum type
	 * @author Eldo Rajan
	 *
	 */
	public enum FileType {

		BoxNote("Box Note"),
		Bookmark("Bookmark"),
		GoogleDoc("Google Doc"),
		GoogleSpreadsheet("Google Spreadsheet"),
		WordDocument("Word Document"),
		PowerpointDocument("Powerpoint Document"),
		ExcelSpreadsheet("Excel Spreadsheet");

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
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		FileType fType=null;
		try{
			if (fileType.equalsIgnoreCase("Box Note")||
					fileType.equalsIgnoreCase("New Box Note")) {
				fType=FileType.BoxNote;
			} else if (fileType.equalsIgnoreCase("Bookmark")||
					fileType.equalsIgnoreCase("New Bookmark")) {
				fType=FileType.Bookmark;
			} else if (fileType.equalsIgnoreCase("Google Doc")||
					fileType.equalsIgnoreCase("New Google Doc")) {
				fType=FileType.GoogleDoc;
			}  else if (fileType.equalsIgnoreCase("Google Spreadsheet")||
					fileType.equalsIgnoreCase("New Google Spreadsheet")) {
				fType=FileType.GoogleSpreadsheet;
			}  else if (fileType.equalsIgnoreCase("Word Document")||
					fileType.equalsIgnoreCase("New Word Document")) {
				fType=FileType.WordDocument;
			}  else if (fileType.equalsIgnoreCase("Powerpoint Document")||
					fileType.equalsIgnoreCase("New Powerpoint Document")) {
				fType=FileType.PowerpointDocument;
			} else if (fileType.equalsIgnoreCase("Excel Spreadsheet")||
					fileType.equalsIgnoreCase("New Excel Spreadsheet")) {
				fType=FileType.ExcelSpreadsheet;
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return fType;
	}

	/**
	 * login into box
	 * @param driver
	 * @param username
	 * @param password
	 */
	public void login(WebDriver driver,String username,String password){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			Assert.assertTrue(bp.username(driver).isElementVisible());
			Assert.assertTrue(bp.password(driver).isElementVisible());
			Assert.assertTrue(bp.loginbutton(driver).isElementVisible());

			bp.username(driver).clear();bp.username(driver).type(username);
			bp.password(driver).clear();bp.password(driver).type(password);
			bp.loginbutton(driver).click();

			Thread.sleep(10000);

			bp.logo(driver).waitForElementPresent(driver);
			bp.logo(driver).waitForElementToBeVisible(driver);

			Assert.assertTrue(bp.logo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains("All files and folders - Box"));

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * create folder
	 * @param driver
	 * @param folderName
	 */
	public void createFolder(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			int countBefore = getFolderFileCount(driver);

			clickNewButton(driver);
			selectOptionFromNewButton(driver,BoxConstants.Folder);
			typeNewFolderName(driver,folderName);		
			clickCreateNewFolderOkayButton(driver);

			int countAfter = getFolderFileCount(driver);	

			Assert.assertTrue(countAfter==countBefore+1,"Folder creation failed as new folder is not created");
			String[] folderNames = getAllFolderFileNames(driver);	
			Assert.assertTrue(Arrays.asList(folderNames).contains(folderName),"Folder creation failed for folder name:"+folderName);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * download folder
	 * @param driver
	 * @param folderName
	 */
	public void downloadFolder(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			File dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			FileFilter fileFilter = new WildcardFileFilter(folderName+"*.zip");
			int fileCountBefore = dir.listFiles(fileFilter).length;

			clickMoreActionsForFolderName(driver,folderName);
			selectOptionFromMoreActions(driver,BoxConstants.Download);
			clickOkayButton(driver);		

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
	 * @param folderName
	 */
	public void deleteFolder(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver);

			clickMoreActionsCheckboxForFolderName(driver,folderName);
			clickButtonFromActionBar(driver,BoxConstants.MODelete);
			clickDeleteButton(driver);

			int countAfter = getFolderFileCount(driver);

			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Upload file 
	 * @param driver
	 * @param fileName
	 */
	public void uploadFile(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		FileUploadAction fu = new FileUploadAction();

		try {
			DropBoxPage dbp =  AdvancedPageFactory.getPageObject(driver,DropBoxPage.class);

			int countBefore = getFolderFileCount(driver);

			clickUploadButton(driver);
			selectOptionFromUploadButton(driver,BoxConstants.UploadFiles);

			if(driver instanceof FirefoxDriver){
				fu.autoitFileUploadFirefox(fileName);
			}else{
				fu.autoitFileUploadChrome(fileName);
			}
			clickUploadNewFileOkayButton(driver);
			int countAfter = getFolderFileCount(driver);	

			Assert.assertTrue(countAfter==countBefore+1,"File creation failed as new file is not created");
			String[] folderNames = getAllFolderFileNames(driver);	
			Assert.assertTrue(Arrays.asList(folderNames).contains(fileName),"File creation failed for file name:"+fileName);

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Download file
	 * @param driver
	 * @param fileName
	 */
	public void downloadFile(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			File dir = new File(System.getProperty("user.home")+File.separator+"Downloads");
			FileFilter fileFilter = new WildcardFileFilter("Test*.docx");
			int fileCountBefore = dir.listFiles(fileFilter).length;

			clickMoreActionsForFolderName(driver,fileName);
			selectOptionFromMoreActions(driver,BoxConstants.Download);
			clickOkayButton(driver);			

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
	 * Delete file
	 * @param driver
	 * @param fileName
	 */
	public void deleteFile(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			int countBefore = getFolderFileCount(driver);

			clickMoreActionsCheckboxForFolderName(driver,fileName);
			clickButtonFromActionBar(driver,BoxConstants.MODelete);
			clickDeleteButton(driver);

			int countAfter = getFolderFileCount(driver);

			Assert.assertTrue(countAfter==countBefore-1,"File deletion failed as new file is not deleted");
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * get folder/file count
	 * @param driver
	 * @return
	 */
	public int getFolderFileCount(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
		List<Element> boxfolderelement  = bp.boxfolderelement(driver).getChildElements();
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return boxfolderelement.size();	
	}

	/**
	 * get all folder names
	 * @param driver
	 * @return
	 */
	public String[] getAllFolderFileNames(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
		List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
		String[] list = new String[boxfolderfileelementtitle.size()];
		for(int i=0;i<boxfolderfileelementtitle.size();i++){
			list[i] = boxfolderfileelementtitle.get(i).getText().trim();
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return list;	
	}


	/**
	 * click okay button
	 * @param driver
	 */
	public void clickOkayButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.boxfolderfiledownloadokayelement(driver).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * select option from more actions
	 * @param driver
	 * @param optionName
	 */
	public void selectOptionFromMoreActions(WebDriver driver, String optionName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementmoreactionsdropdownlist  = bp.boxfolderfileelementmoreactionsdropdownlist(driver).getChildElements();		
			for(int i=0;i<boxfolderfileelementmoreactionsdropdownlist.size();i++){
				if(boxfolderfileelementmoreactionsdropdownlist.get(i).getText().trim().contains(optionName)){
					boxfolderfileelementmoreactionsdropdownlist.get(i).click();
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
	 * select more actions for folder name
	 * @param driver
	 * @param folderName
	 */
	public void clickMoreActionsForFolderName(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementmoreactions  = bp.boxfolderfileelementmoreactions(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					boxfolderfileelementmoreactions.get(i).click();
					Thread.sleep(5000);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click more actions checkbox for folder name
	 * @param driver
	 * @param folderName
	 */
	public void clickMoreActionsCheckboxForFolderName(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementmoreactionscheckbox  = bp.boxfolderfileelementmoreactionscheckbox(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					boxfolderfileelementmoreactionscheckbox.get(i).mouseOver(driver);
					boxfolderfileelementmoreactionscheckbox.get(i).mouseOverClick(driver);
					Thread.sleep(5000);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click button from action bar
	 * @param driver
	 * @param optionName
	 */
	public void clickButtonFromActionBar(WebDriver driver, String optionName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.boxfolderfileelementactionbaroptions(driver,optionName).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click delete button
	 * @param driver
	 */
	public void clickDeleteButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.boxfolderfiledeleteokayelement(driver).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * type new folder name
	 * @param driver
	 * @param folderName
	 */
	public void typeNewFolderName(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.createnewfolderinputbox(driver).clear();
			bp.createnewfolderinputbox(driver).type(folderName);
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * select option from new button
	 * @param driver
	 * @param optionType
	 * @throws InterruptedException
	 */
	public void selectOptionFromNewButton(WebDriver driver, String optionType) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
		try {
			List<Element> createnewdropdownlist  = bp.createnewdropdownlist(driver).getChildElements();		
			for(int i=0;i<createnewdropdownlist.size();i++){
				if(createnewdropdownlist.get(i).getText().trim().contains(optionType)){
					createnewdropdownlist.get(i).click();
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
	 * click new button
	 * @param driver
	 */
	public void clickNewButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.createnewbutton(driver).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click create new folder okay button
	 * @param driver
	 */
	public void clickCreateNewFolderOkayButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.createnewfolderbutton(driver).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click upload button
	 * @param driver
	 */
	public void clickUploadButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.boxuploadbutton(driver).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * select option from upload button
	 * @param driver
	 * @param optionType
	 */
	public void selectOptionFromUploadButton(WebDriver driver, String optionType) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		
		BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
		try {
			List<Element> boxuploaddropdownlist  = bp.boxuploaddropdownlist(driver).getChildElements();		
			for(int i=0;i<boxuploaddropdownlist.size();i++){
				if(boxuploaddropdownlist.get(i).getText().trim().contains(optionType)){
					boxuploaddropdownlist.get(i).mouseOverClick(driver);
					//boxuploaddropdownlist.get(i).click();
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
	 * click upload new file okay button
	 * @param driver
	 */
	public void clickUploadNewFileOkayButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			bp.boxuploadokayelement(driver).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}





}
