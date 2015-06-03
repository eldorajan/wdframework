package wdframework.action.onedrive;

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
import wdframework.action.fileupload.FileUploadAction;
import wdframework.constants.CommonConstants;
import wdframework.constants.onedrive.OneDriveConstants;
import wdframework.pagefactory.AdvancedPageFactory;
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
public class OneDriveAction{

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
	 * login into onedrive
	 * @param driver
	 * @param username
	 * @param password
	 */
	public void login(WebDriver driver,String username,String password){

		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			Assert.assertTrue(odp.username(driver).isElementVisible());
			Assert.assertTrue(odp.password(driver).isElementVisible());
			Assert.assertTrue(odp.loginbutton(driver).isElementVisible());

			odp.username(driver).clear();odp.username(driver).type(username);
			odp.password(driver).clear();odp.password(driver).type(password);
			odp.loginbutton(driver).click();
			
			odp.logo(driver).waitForElementPresent(driver);
			odp.logo(driver).waitForElementToBeVisible(driver);

			Assert.assertTrue(odp.logo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains(OneDriveConstants.LoggedInHeader));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	
	/**
	 * logout of onedrive
	 * @param driver
	 */
	public void logout(WebDriver driver,String username){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			Assert.assertTrue(odp.usernamebuttonintopbar(driver).isElementVisible());
			clickUserNameButton(driver);
			clickSignOutButtonInUserNameButtonDropdown(driver);
			
			odp.username(driver).waitForLoading(driver);
			odp.password(driver).waitForLoading(driver);
			odp.loginbutton(driver).waitForLoading(driver);
			Logger.info(driver.getTitle());
			Assert.assertTrue(driver.getTitle().contains(OneDriveConstants.LoggedOutHeader));

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
	public void createFolder(WebDriver driver, String itemType, String folderName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			selectQuickHeaders(driver, OneDriveConstants.Files);

			int countBefore = getFolderCount(driver);			
			odp.createbutton(driver).click();
			Thread.sleep(5000);

			clickCreateItemType(driver, itemType);
			odp.folderinput(driver).clear();odp.folderinput(driver).type(folderName);
			odp.foldercreatebutton(driver).click();
			Thread.sleep(10000);

			int countAfter = getFolderCount(driver);				
			Assert.assertTrue(countAfter==countBefore+1,"Folder creation failed as new folder is not created");
			String[] folderNames = getAllFolderNames(driver);	
			Assert.assertTrue(Arrays.asList(folderNames).contains(folderName),"Folder creation failed for folder name:"+folderName);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * delete folder
	 * @param driver
	 * @param manageType
	 * @param folderName
	 */
	public void deleteFolder(WebDriver driver, String manageType, String folderName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			selectQuickHeaders(driver, OneDriveConstants.Files);

			int countBefore = getFolderCount(driver);			
			hoverOverFolderName(driver, folderName);
			selectInputBoxFolderName(driver, folderName);
			if(driver instanceof FirefoxDriver){
				clickCreateManageButton(driver, OneDriveConstants.FolderActions);
			}else{
				clickCreateManageButton(driver, OneDriveConstants.Manage);
			}			
			clickCreateItemType(driver, manageType);

			int countAfter = getFolderCount(driver);				
			Assert.assertTrue(countAfter==countBefore-1,"Folder deletion failed as new folder is not deleted");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * create file types
	 * @param driver
	 * @param fileType
	 * @param itemType
	 * @param fileName
	 * @return
	 */
	public String createFile(WebDriver driver, String fileType, String itemType, String fileName) {
		String newFileName = "";
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			selectQuickHeaders(driver, OneDriveConstants.Files);

			int countBefore = getFileCount(driver,itemType);	
			String[] fileNamesBefore = getAllFileNames(driver,itemType);
			String winHandleBefore =  driver.getWindowHandle();
			
			clickCreateManageButton(driver, OneDriveConstants.Create);		
			clickCreateItemType(driver, fileType);
			
			FileType ft = getFileType(fileType);
			switch (ft) {
    		case Word: {            
    			revertToOldWindow(driver, winHandleBefore);
            	break;
    		}
    		case Excel: {
    			revertToOldWindow(driver, winHandleBefore);
            	break;
    		}
    		case PowerPoint: {            
    			revertToOldWindow(driver, winHandleBefore);
            	break;
    		}    		
    		case OneNote: {            
    			switchToNewWindow(driver, winHandleBefore);
    			odp.createbuttonfile(driver).click();Thread.sleep(10000);
    			driver.close();
    			
    			driver.switchTo().window(winHandleBefore);Thread.sleep(5000);
            	break;
    		}
    		case ExcelSurvey: {            
    			revertToOldWindow(driver, winHandleBefore);
            	break;
    		}
    		case PlainText: {            
    			revertToOldWindow(driver, winHandleBefore);
            	break;
    		}
    		default:           
    			try {
    				Logger.error(
    						"File type:"+fileType+" is not found");
    				throw new Exception(
    						"File type:"+fileType+" is not found");
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
           
			driver.navigate().refresh();
			Thread.sleep(10000);			

			int countAfter = getFileCount(driver,itemType);				
			Assert.assertTrue(countAfter==countBefore+1,"File creation failed as new file is not created");
			String[] fileNamesAfter = getAllFileNames(driver,itemType);	
			newFileName = arrayDifference(fileNamesAfter, fileNamesBefore);			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newFileName;
	}

	

	/**
	 * delete file
	 * @param driver
	 * @param fileType
	 * @param itemType
	 * @param fileName
	 */
	public void deleteFile(WebDriver driver, String fileType, String itemType, String fileName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			selectQuickHeaders(driver, OneDriveConstants.Files);

			int countBefore = getFileCount(driver,itemType);	
			String[] fileNamesBefore = getAllFileNames(driver,itemType);
			String winHandleBefore =  driver.getWindowHandle();			
			hoverOverFileName(driver,itemType,fileName);
			selectInputBoxFileName(driver,fileName);
			clickCreateManageButton(driver, OneDriveConstants.Manage);
			clickCreateItemType(driver, OneDriveConstants.Delete);
			clickErrorPanelDeleteButton(driver);

			int countAfter = getFileCount(driver,itemType);				
			Assert.assertTrue(countAfter==countBefore-1,"File deletion failed as "+fileName+" is not deleted");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	/**
	 * Upload file
	 * @param driver
	 * @param fileName
	 */
	public void uploadFile(WebDriver driver, String fileName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
			FileUploadAction fu = new FileUploadAction();
			selectQuickHeaders(driver, OneDriveConstants.Files);

			int countBefore = getFileCount(driver);	
			String[] fileNamesBefore = getAllFileNames(driver);
			
			
			if(driver instanceof FirefoxDriver){
				clickCreateManageButton(driver, OneDriveConstants.Upload);
				clickUploadItemType(driver, OneDriveConstants.Upload);
				fu.autoitFileUploadFirefox(fileName);
			}else{
				clickCreateManageButton(driver, OneDriveConstants.Upload);
				clickUploadItemType(driver, OneDriveConstants.Files);
				fu.autoitFileUploadChrome(fileName);
			}
			driver.navigate().refresh();
			Thread.sleep(10000);			

			int countAfter = getFileCount(driver);				
			Assert.assertTrue(countAfter==countBefore+1,"File upload failed as file "+fileName+" is not uploaded");
			String[] fileNamesAfter = getAllFileNames(driver);	
			String newFileName = arrayDifference(fileNamesAfter, fileNamesBefore);	
			
			Assert.assertTrue(fileName.contains(newFileName),"File upload failed as file "+fileName+" is not uploaded");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	/**
	 * Upload file
	 * @param driver
	 * @param fileName
	 */
	public void downloadFile(WebDriver driver, String fileName) {
		String[] files = fileName.split(".");
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			selectQuickHeaders(driver, OneDriveConstants.Files);

			File dir = new File(CommonConstants.downloadDir);
			FileFilter fileFilter = new WildcardFileFilter(files[0]+"*"+files[1]);
			int fileCountBefore = dir.listFiles(fileFilter).length;
			
			hoverOverFileName(driver,fileName);
			selectInputBoxFileName(driver,fileName);
			clickManageLinks(driver, OneDriveConstants.Download);
			
			dir = new File(CommonConstants.downloadDir);
			fileFilter = new WildcardFileFilter(files[0]+"*"+files[1]);
			int fileCountAfter = dir.listFiles(fileFilter).length;			
			Assert.assertTrue(fileCountAfter==fileCountBefore+1,"File downloaded failed as file "+fileName+" is not downloaded");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Forced delete if file is opened for editing
	 * @param driver
	 * @throws InterruptedException
	 */
	public void clickErrorPanelDeleteButton(WebDriver driver)
			throws InterruptedException {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

		if(odp.errorpaneldeletebutton(driver).isElementVisible()){
			if(odp.errorpaneldeletebutton(driver).getText().equalsIgnoreCase(OneDriveConstants.Delete)){
				odp.errorpaneldeletebutton(driver).click();
				Thread.sleep(10000);
			}				
		}
	}

	/**
	 * Click on create/manage/upload link
	 * @param driver
	 * @param itemType
	 * @throws InterruptedException
	 */
	public void clickCreateItemType(WebDriver driver, String itemType) throws InterruptedException {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

		List<Element> createDropdown  = odp.createdropdown(driver).getChildElements();		
		for(int i=0;i<createDropdown.size();i++){
			if(createDropdown.get(i).getText().trim().contains(itemType)){
				createDropdown.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	
	/**
	 * Click on upload link
	 * @param driver
	 * @param itemType
	 * @throws InterruptedException
	 */
	public void clickUploadItemType(WebDriver driver, String itemType) throws InterruptedException {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

		List<Element> uploaddropdown  = odp.uploaddropdown(driver).getChildElements();		
		for(int i=0;i<uploaddropdown.size();i++){
			if(uploaddropdown.get(i).getText().trim().contains(itemType)){
				uploaddropdown.get(i).mouseOver(driver);
				uploaddropdown.get(i).mouseOverClick(driver);
				Thread.sleep(10000);
				break;
			}
		}
	}

	
	/**
	 * Select quick headers in the side bar
	 * @param driver
	 * @param headerName
	 */
	public void selectQuickHeaders(WebDriver driver, String headerName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);


			List<Element> quickheader  = odp.quickheader(driver).getChildElements();		
			for(int i=0;i<quickheader.size();i++){
				if(quickheader.get(i).getText().trim().contains(headerName)){
					quickheader.get(i).click();
					Thread.sleep(5000);
					break;
				}
			}


		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * get folder count
	 * @param driver
	 * @return
	 */
	public int getFolderCount(WebDriver driver) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfolderelement  = odp.createdfolderelement(driver).getChildElements();
		return createdfolderelement.size();	
	}

	/**
	 * get file count
	 * @param driver
	 * @return
	 */
	public int getFileCount(WebDriver driver) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfileelement  = odp.createdfileelement(driver).getChildElements();
		return createdfileelement.size();	
	}
	
	/**
	 * get file count according to type
	 * @param driver
	 * @param fileType
	 * @return
	 */
	public int getFileCount(WebDriver driver,String fileType) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfileelement  = odp.createdfiletypeelement(driver,fileType).getChildElements();
		return createdfileelement.size();	
	}

	/**
	 * get folder name per index
	 * @param driver
	 * @param index
	 * @return
	 */
	public String getFolderName(WebDriver driver, int index) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfoldertitle  = odp.createdfoldertitle(driver).getChildElements();
		return createdfoldertitle.get(index-1).getText().trim();	
	}
	/**
	 * get file name per index
	 * @param driver
	 * @param index
	 * @return
	 */
	public String getFileName(WebDriver driver, int index) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfiletitle  = odp.createdfiletitle(driver).getChildElements();
		return createdfiletitle.get(index-1).getText().trim();	
	}

	/**
	 * get all folder names
	 * @param driver
	 * @return
	 */
	public String[] getAllFolderNames(WebDriver driver) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfoldertitle  = odp.createdfoldertitle(driver).getChildElements();
		String[] list = new String[createdfoldertitle.size()];
		for(int i=0;i<createdfoldertitle.size();i++){
			list[i] = createdfoldertitle.get(i).getText().trim();
		}

		return list;	
	}

	/**
	 * get all file names
	 * @param driver
	 * @return
	 */
	public String[] getAllFileNames(WebDriver driver) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfiletitle  = odp.createdfiletitle(driver).getChildElements();
		String[] list = new String[createdfiletitle.size()];
		for(int i=0;i<createdfiletitle.size();i++){
			list[i] = createdfiletitle.get(i).getText().trim();
		}

		return list;	
	}
	
	/**
	 * get all file names for a file type
	 * @param driver
	 * @param fileType
	 * @return
	 */
	public String[] getAllFileNames(WebDriver driver,String fileType) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfiletitle  = odp.createdfiletypetitle(driver,fileType).getChildElements();
		String[] list = new String[createdfiletitle.size()];
		for(int i=0;i<createdfiletitle.size();i++){
			list[i] = createdfiletitle.get(i).getText().trim();
		}

		return list;	
	}

	/**
	 * Click on create/manage button
	 * @param driver
	 * @param createType
	 * @throws InterruptedException
	 */
	public void clickCreateManageButton(WebDriver driver, String createType) throws InterruptedException {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

		List<Element> createmanagebutton  = odp.createmanagebutton(driver).getChildElements();		
		for(int i=0;i<createmanagebutton.size();i++){
			if(createmanagebutton.get(i).getText().trim().contains(createType)){
				createmanagebutton.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	/**
	 * hover over folder name
	 * @param driver
	 * @param folderName
	 */
	public void hoverOverFolderName(WebDriver driver,String folderName) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfoldertitle  = odp.createdfoldertitle(driver).getChildElements();
		for(int i=0;i<createdfoldertitle.size();i++){
			if(createdfoldertitle.get(i).getText().trim().contains(folderName)){
				createdfoldertitle.get(i).mouseOver(driver);
				break;
			}
		}	
	}

	/**
	 * select checkbox for folder name
	 * @param driver
	 * @param folderName
	 */
	public void selectInputBoxFolderName(WebDriver driver,String folderName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
			List<Element> createdfoldertitle  = odp.createdfoldertitle(driver).getChildElements();
			List<Element> createdfolderselectbox  = odp.createdfolderselectbox(driver).getChildElements();
			for(int i=0;i<createdfolderselectbox.size();i++){
				if(createdfoldertitle.get(i).getText().trim().contains(folderName)){
					createdfolderselectbox.get(i).mouseOverClick(driver);
					Thread.sleep(5000);
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	/**
	 * hover over file name
	 * @param driver
	 * @param fileName
	 */
	public void hoverOverFileName(WebDriver driver,String fileName) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfiletitle  = odp.createdfiletitle(driver).getChildElements();
		for(int i=0;i<createdfiletitle.size();i++){
			if(createdfiletitle.get(i).getText().trim().contains(fileName)){
				createdfiletitle.get(i).mouseOver(driver);
				break;
			}
		}	
	}

	/**
	 * select check box for file name
	 * @param driver
	 * @param fileName
	 */
	public void selectInputBoxFileName(WebDriver driver,String fileName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
			List<Element> createdfiletitle  = odp.createdfiletitle(driver).getChildElements();
			List<Element> createdfileselectbox  = odp.createdfileselectbox(driver).getChildElements();
			for(int i=0;i<createdfileselectbox.size();i++){
				if(createdfiletitle.get(i).getText().trim().contains(fileName)){
					createdfileselectbox.get(i).mouseOverClick(driver);
					Thread.sleep(5000);
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * hover over file name with file type 
	 * @param driver
	 * @param fileType
	 * @param fileName
	 */
	public void hoverOverFileName(WebDriver driver,String fileType,String fileName) {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
		List<Element> createdfiletypetitle  = odp.createdfiletypetitle(driver,fileType).getChildElements();
		for(int i=0;i<createdfiletypetitle.size();i++){
			if(createdfiletypetitle.get(i).getText().trim().contains(fileName)){
				createdfiletypetitle.get(i).mouseOver(driver);
				break;
			}
		}	
	}
	
	/**
	 * select checkbox for filename with specific file type
	 * @param driver
	 * @param fileType
	 * @param fileName
	 */
	public void selectInputBoxFileName(WebDriver driver,String fileType,String fileName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
			List<Element> createdfiletypetitle  = odp.createdfiletypetitle(driver,fileType).getChildElements();
			List<Element> createdfiletypeselectbox  = odp.createdfiletypeselectbox(driver,fileType).getChildElements();
			for(int i=0;i<createdfiletypeselectbox.size();i++){
				if(createdfiletypetitle.get(i).getText().trim().contains(fileName)){
					createdfiletypeselectbox.get(i).mouseOverClick(driver);
					Thread.sleep(5000);
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Click on create/manage button
	 * @param driver
	 * @param createType
	 * @throws InterruptedException
	 */
	public void clickManageLinks(WebDriver driver, String createType) throws InterruptedException {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

		List<Element> managelinks  = odp.managelinks(driver).getChildElements();		
		for(int i=0;i<managelinks.size();i++){
			if(managelinks.get(i).getText().trim().contains(createType)){
				managelinks.get(i).click();
				Thread.sleep(10000);
				break;
			}
		}
	}

	/**
	 * get array difference as arrays
	 * @param first
	 * @param second
	 * @return
	 */
	public  String[] arraysDifference(String[] first, String[] second) {
		Set<String> union = new HashSet<String>(Arrays.asList(first));
		union.addAll(Arrays.asList(second));
		Set<String> intersection = new HashSet<String>(Arrays.asList(first));
		intersection.retainAll(Arrays.asList(second));
		union.removeAll(intersection);

		return union.toArray(new String[union.size()]);
	}

	/**
	 * get arrya difference as String
	 * @param first
	 * @param second
	 * @return
	 */
	public  String arrayDifference(String[] first, String[] second) {
		Set<String> union = new HashSet<String>(Arrays.asList(first));
		union.addAll(Arrays.asList(second));
		Set<String> intersection = new HashSet<String>(Arrays.asList(first));
		intersection.retainAll(Arrays.asList(second));
		union.removeAll(intersection);

		return Arrays.toString(union.toArray(new String[union.size()])).replace("[", "").replace("]", "");
	}
	
	/**
	 * revert to first window
	 * @param driver
	 * @param winHandleBefore
	 * @throws InterruptedException
	 */
	public void revertToOldWindow(WebDriver driver,
			String winHandleBefore) throws InterruptedException {
		switchToNewWindowAndClose(driver, winHandleBefore);
		
		driver.switchTo().window(winHandleBefore);Thread.sleep(5000);
	}
	
	/**
	 * switch to new window and close
	 * @param driver
	 * @param winHandleBefore
	 * @throws InterruptedException
	 */
	public void switchToNewWindowAndClose(WebDriver driver,
			String winHandleBefore) throws InterruptedException {
		Set<String> winHandles =  driver.getWindowHandles();
		for(String w:winHandles){
			if(w.equalsIgnoreCase(winHandleBefore)){
				Logger.info("Window already selected");
			}else{
				driver.switchTo().window(w);break;
			}
		}
		driver.close();
		
	}
	
	/**
	 * switch to new window
	 * @param driver
	 * @param winHandleBefore
	 * @throws InterruptedException
	 */
	public void switchToNewWindow(WebDriver driver,
			String winHandleBefore) throws InterruptedException {
		Set<String> winHandles =  driver.getWindowHandles();
		for(String w:winHandles){
			if(w.equalsIgnoreCase(winHandleBefore)){
				Logger.info("Window already selected");
			}else{
				driver.switchTo().window(w);break;
			}
		}
		
	}
	
	/**
	 * click username button 
	 * @param driver
	 */
	public void clickUserNameButton(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
			odp.usernamebuttonintopbar(driver).mouseOverClick(driver);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * click signout button 
	 * @param driver
	 */
	public void clickSignOutButtonInUserNameButtonDropdown(WebDriver driver) {
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);
			odp.usernamesignoutbuttonintopbar(driver).mouseOverClick(driver);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

}
