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
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import wdframework.driver.BrowserType;
import wdframework.logger.Logger;
import wdframework.action.Action;
import wdframework.action.fileupload.FileUploadAction;
import wdframework.constants.CommonConstants;
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

			bp.logo(driver).waitForLoading(driver);

			bp.logo(driver).waitForElementPresent(driver);
			bp.logo(driver).waitForElementToBeVisible(driver);

			Assert.assertTrue(bp.logo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains(BoxConstants.LoggedInHeader));

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	/**
	 * logout of box
	 * @param driver
	 */
	public void logout(WebDriver driver){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			Assert.assertTrue(bp.usernamebuttonintopbar(driver).isElementVisible());
			clickUserNameButton(driver);
			clickButtonInUserNameButtonDropdown(driver,BoxConstants.Logout);
			
			bp.username(driver).waitForLoading(driver);
			bp.password(driver).waitForLoading(driver);
			bp.loginbutton(driver).waitForLoading(driver);
			Logger.info(driver.getTitle());
			Assert.assertTrue(bp.username(driver).isElementVisible());
			Assert.assertTrue(bp.password(driver).isElementVisible());
			Assert.assertTrue(bp.loginbutton(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains(BoxConstants.LoggedOutHeader));

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

			File dir = new File(CommonConstants.downloadDir);
			FileFilter fileFilter = new WildcardFileFilter(folderName+BoxConstants.SampleFolderExtension);
			int fileCountBefore = dir.listFiles(fileFilter).length;

			clickMoreActionsForFolderName(driver,folderName);
			selectOptionFromMoreActions(driver,BoxConstants.Download);
			clickOkayButton(driver);		

			dir = new File(CommonConstants.downloadDir);
			fileFilter = new WildcardFileFilter(folderName+BoxConstants.SampleFolderExtension);
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

			if(System.getProperty("os.name").contains("Mac")){
				if(driver instanceof FirefoxDriver){
					fu.applescriptFileUploadFirefox(fileName);
				}else if(driver instanceof SafariDriver){
					fu.applescriptFileUploadSafari(fileName);
				}
			}else{
				if(driver instanceof FirefoxDriver){
					fu.autoitFileUploadFirefox(fileName);
				}else{
					fu.autoitFileUploadChrome(fileName);
				}
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
		String[] files = fileName.split(".");
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			File dir = new File(CommonConstants.downloadDir);
			FileFilter fileFilter = new WildcardFileFilter(files[0]+"*"+files[1]);
			int fileCountBefore = dir.listFiles(fileFilter).length;

			clickMoreActionsForFolderName(driver,fileName);
			selectOptionFromMoreActions(driver,BoxConstants.Download);
			clickOkayButton(driver);			

			dir = new File(CommonConstants.downloadDir);
			fileFilter = new WildcardFileFilter(files[0]+"*"+files[1]);
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

	public void getFolderProperties(WebDriver driver, String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			clickShareForFolderName(driver, folderName);
			clickCloseXForSharePopup(driver);
			clickShareForFolderName(driver, folderName);
			clickCloseButtonForSharePopup(driver);

			clickFavoriteForFolderName(driver, folderName);
			clickUnFavoriteForFolderName(driver, folderName);

			clickShareForFolderName(driver, folderName);
			clickButtonsOnSharePopup(driver, BoxConstants.Share);
			clickButtonsOnSharePopup(driver, BoxConstants.Email);
			clickButtonsOnSharePopup(driver, BoxConstants.Embed);
			clickCloseButtonForSharePopup(driver);

			Logger.info(getFolderTitle(driver, folderName));
			Logger.info(getFolderIndex(driver, folderName));
			Logger.info(getFolderCreationInfo(driver, folderName));
			Logger.info(getFolderCreatorInfo(driver, folderName));
			Logger.info(getFileCountInFolder(driver, folderName));
			Logger.info(getFolderSharedOrNot(driver, folderName));
			
			clickSharedLinkButtonForFolderName(driver, folderName);
			clickCloseXForSharePopup(driver);



		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	public void getFileProperties(WebDriver driver, String fileName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);

			clickShareForFolderName(driver, fileName);
			clickCloseXForSharePopup(driver);
			clickShareForFolderName(driver, fileName);
			clickCloseButtonForSharePopup(driver);

			clickFavoriteForFolderName(driver, fileName);
			clickUnFavoriteForFolderName(driver, fileName);

			clickShareForFolderName(driver, fileName);
			clickButtonsOnSharePopup(driver, BoxConstants.Share);
			clickButtonsOnSharePopup(driver, BoxConstants.Email);
			clickButtonsOnSharePopup(driver, BoxConstants.Embed);
			clickCloseButtonForSharePopup(driver);

			Logger.info(getFolderTitle(driver, fileName));
			Logger.info(getFolderIndex(driver, fileName));
			Logger.info(getFolderCreationInfo(driver, fileName));
			Logger.info(getFolderCreatorInfo(driver, fileName));
			Logger.info(getFileSize(driver, fileName));
			Logger.info(getFolderSharedOrNot(driver, fileName));
			
			clickSharedLinkButtonForFolderName(driver, fileName);
			clickCloseXForSharePopup(driver);
			
			clickAddCommentButtonForFileName(driver, fileName);
			Logger.info(bp.boxpreview(driver).getText());
			clickCloseButtonForPreview(driver);

		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * click share for folder name
	 * @param driver
	 * @param folderName
	 */
	public void clickShareForFolderName(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementactionshare  = bp.boxfolderfileelementactionshare(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					boxfolderfileelementactionshare.get(i).click();
					bp.boxfoldersharepopup(driver).waitForLoading(driver);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Click buttons for share popup
	 * @param driver
	 * @param buttonName
	 */
	public void clickButtonsOnSharePopup(WebDriver driver,
			String buttonName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfoldersharepopupbuttons  = bp.boxfoldersharepopupbuttons(driver).getChildElements();
			for(int i=0;i<boxfoldersharepopupbuttons.size();i++){
				if(boxfoldersharepopupbuttons.get(i).getText().trim().contains(buttonName)){
					boxfoldersharepopupbuttons.get(i).click();
					if(buttonName.contains(BoxConstants.Share)){
						bp.boxfoldersharepopupsharelink(driver).waitForLoading(driver);
					}else if(buttonName.contains(BoxConstants.Email)){
						bp.boxfolderemaillinkmessage(driver).waitForLoading(driver);
					}else if(buttonName.contains(BoxConstants.Embed)){
						bp.boxfolderembedcode(driver).waitForLoading(driver);
					}
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click close x button for share popup
	 * @param driver
	 */
	public void clickCloseXForSharePopup(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.boxfoldersharepopupclose(driver).mouseOverClick(driver);
			bp.boxfoldersharepopup(driver).waitForElementToDisappear(driver);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * click close button for share popup
	 * @param driver
	 */
	public void clickCloseButtonForSharePopup(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.boxfoldersharepopupclosebutton(driver).mouseOverClick(driver);
			bp.boxfoldersharepopup(driver).waitForElementToDisappear(driver);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click favorite on folder name
	 * @param driver
	 * @param folderName
	 */
	public void clickFavoriteForFolderName(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementactionfavorite  = bp.boxfolderfileelementactionfavorite(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					boxfolderfileelementactionfavorite.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click unfavorite on folder name
	 * @param driver
	 * @param folderName
	 */
	public void clickUnFavoriteForFolderName(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementactionunfavorite  = bp.boxfolderfileelementactionunfavorite(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					boxfolderfileelementactionunfavorite.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}


	/**
	 * get folder title 
	 * @param driver
	 * @param folderName
	 */
	public String getFolderTitle(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		String title="";
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					title = boxfolderfileelementtitle.get(i).getText().trim();
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return title;
	}

	/**
	 * get folder index 
	 * @param driver
	 * @param folderName
	 */
	public int getFolderIndex(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		int index=0;
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					index = i;
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return index;
	}


	/**
	 * get folder creation info 
	 * @param driver
	 * @param folderName
	 */
	public String getFolderCreationInfo(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		String title="";
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementcreationinfo  = bp.boxfolderfileelementcreationinfo(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					title = boxfolderfileelementcreationinfo.get(i).getText().trim();
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return title;
	}

	/**
	 * get folder creator info 
	 * @param driver
	 * @param folderName
	 */
	public String getFolderCreatorInfo(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		String title="";
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementcreatorinfo  = bp.boxfolderfileelementcreatorinfo(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					title = boxfolderfileelementcreatorinfo.get(i).getText().trim();
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return title;
	}

	/**
	 * get file count in folders
	 * @param driver
	 * @param folderName
	 */
	public int getFileCountInFolder(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		int count=0;
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementfilecount  = bp.boxfolderfileelementfilecount(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					count = Integer.parseInt(boxfolderfileelementfilecount.get(i).getText().trim());
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return count;
	}
	
	/**
	 * get if folder is shared or not
	 * @param driver
	 * @param folderName
	 */
	public boolean getFolderSharedOrNot(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		boolean flag=false;
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					List<Element> boxfolderfileelementsharedmenulink  = bp.boxfolderfileelementsharedmenulink(driver).getChildElements();
					flag = boxfolderfileelementsharedmenulink.get(i).isElementPresent(driver);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return flag;
	}

	/**
	 * click shared link for folder name
	 * @param driver
	 * @param folderName
	 */
	public void clickSharedLinkButtonForFolderName(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementsharedmenulink  = bp.boxfolderfileelementsharedmenulink(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					boxfolderfileelementsharedmenulink.get(i).click();
					bp.boxfoldersharepopup(driver).waitForLoading(driver);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	/**
	 * click shared link for folder name
	 * @param driver
	 * @param folderName
	 */
	public void clickAddCommentButtonForFileName(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementaddcomment  = bp.boxfolderfileelementaddcomment(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					boxfolderfileelementaddcomment.get(i).mouseOverClick(driver);
					bp.boxpreview(driver).waitForLoading(driver);
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	
	/**
	 * get folder creation info 
	 * @param driver
	 * @param folderName
	 */
	public String getFileSize(WebDriver driver,
			String folderName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		String title="";
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> boxfolderfileelementtitle  = bp.boxfolderfileelementtitle(driver).getChildElements();
			List<Element> boxfolderfileelementfilesize  = bp.boxfolderfileelementfilesize(driver).getChildElements();
			for(int i=0;i<boxfolderfileelementtitle.size();i++){
				if(boxfolderfileelementtitle.get(i).getText().trim().contains(folderName)){
					title = boxfolderfileelementfilesize.get(i).getText().trim();
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		return title;
	}
	
	/**
	 * click close button for preview
	 * @param driver
	 */
	public void clickCloseButtonForPreview(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.boxpreviewclose(driver).mouseOverClick(driver);
			//bp.boxpreview(driver).waitForElementNotToBePresent(driver);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	/**
	 * click buttons in username button dropdown
	 * @param driver
	 * @param folderName
	 */
	public void clickButtonInUserNameButtonDropdown(WebDriver driver,
			String optionName) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			List<Element> usernamebuttonintopbardropdown  = bp.usernamebuttonintopbardropdown(driver).getChildElements();
			for(int i=0;i<usernamebuttonintopbardropdown.size();i++){
				if(usernamebuttonintopbardropdown.get(i).getText().trim().contains(optionName)){
					usernamebuttonintopbardropdown.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	
	/**
	 * click user name button
	 * @param driver
	 */
	public void clickUserNameButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			BoxPage bp =  AdvancedPageFactory.getPageObject(driver,BoxPage.class);
			bp.usernamebuttonintopbar(driver).mouseOverClick(driver);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	
	
}
