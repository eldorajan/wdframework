package wdframework.action.onedrive;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import wdframework.driver.BrowserType;
import wdframework.logger.Logger;
import wdframework.onedrive.constants.OneDriveConstants;
import wdframework.pagefactory.AdvancedPageFactory;
import wdframework.pageobjects.OneDrivePage;
import wdframework.webelements.CheckBox;
import wdframework.webelements.Element;
import wdframework.webelements.HyperLink;

/**
 * One Drive Action Class
 * @author erajan
 *
 */
@SuppressWarnings("unused")
public class OneDriveAction{

	/**
	 * File type enum type
	 * @author erajan
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
			Thread.sleep(10000);

			Assert.assertTrue(odp.logo(driver).isElementVisible());
			Assert.assertTrue(driver.getTitle().contains("Files - OneDrive"));

		} catch (InterruptedException e) {
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
	public void createFolder(WebDriver driver, String itemType, String folderName) {
		try {
			OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

			selectQuickHeaders(driver, "Files");

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

			selectQuickHeaders(driver, "Files");

			int countBefore = getFolderCount(driver);			
			hoverOverFolderName(driver, folderName);
			selectInputBoxFolderName(driver, folderName);
			if(driver instanceof FirefoxDriver){
				clickCreateManageButton(driver, "Folder actions");
			}else{
				clickCreateManageButton(driver, "Manage");
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

			selectQuickHeaders(driver, "Files");

			int countBefore = getFileCount(driver,itemType);	
			String[] fileNamesBefore = getAllFileNames(driver,itemType);
			String winHandleBefore =  driver.getWindowHandle();
			
			clickCreateManageButton(driver, "Create");		
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

			selectQuickHeaders(driver, "Files");

			int countBefore = getFileCount(driver,itemType);	
			String[] fileNamesBefore = getAllFileNames(driver,itemType);
			String winHandleBefore =  driver.getWindowHandle();			
			hoverOverFileName(driver,itemType,fileName);
			selectInputBoxFileName(driver,fileName);
			clickCreateManageButton(driver, "Manage");
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
	 * Forced delete if file is opened for editing
	 * @param driver
	 * @throws InterruptedException
	 */
	public void clickErrorPanelDeleteButton(WebDriver driver)
			throws InterruptedException {
		OneDrivePage odp =  AdvancedPageFactory.getPageObject(driver,OneDrivePage.class);

		if(odp.errorpaneldeletebutton(driver).isElementVisible()){
			if(odp.errorpaneldeletebutton(driver).getText().equalsIgnoreCase("Delete")){
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
	private void clickCreateItemType(WebDriver driver, String itemType) throws InterruptedException {
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
	private void clickCreateManageButton(WebDriver driver, String createType) throws InterruptedException {
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
	 * get arrya difference as arrays
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
	private void revertToOldWindow(WebDriver driver,
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
	private void switchToNewWindowAndClose(WebDriver driver,
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
	private void switchToNewWindow(WebDriver driver,
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
}
