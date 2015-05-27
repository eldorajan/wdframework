package wdframework.tests.onedrive;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import wd.common.CommonTest;
import wdframework.listeners.Priority;
import wdframework.constants.onedrive.OneDriveConstants;

/**
 * Onedrive Test Suite
 * @author Eldo Rajan
 *
 */
public class OneDriveTests extends CommonTest{
	String wordFileName="";String excelFileName="";String powerpointFileName="";
	String onenoteFileName="";String excelsurveyFileName="";String plaintextFileName="";
	
	/**
	 * Login Test
	 */
	@Priority(1)
	@Test(groups = { "sanity" })
	public void login() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
	}

	/**
	 * Create Folder Test
	 */
	@Priority(2)
	@Test(groups = { "sanity" })
	public void createFolder() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.createFolder(getWebDriver(), OneDriveConstants.Folder, "Sample");
	}

	/**
	 * Delete Folder Test
	 */
	@Priority(3)
	@Test(groups = { "sanity" })
	public void deleteFolder() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		if(getWebDriver() instanceof FirefoxDriver){
			onedrive.deleteFolder(getWebDriver(), OneDriveConstants.DeleteFolder, "Sample");
		}else{
			onedrive.deleteFolder(getWebDriver(), OneDriveConstants.Delete, "Sample");
		}		
	}

	/**
	 * Create Word File Test
	 */
	@Priority(4)
	@Test(groups = { "sanity" })
	public void createWordFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		wordFileName = onedrive.createFile(getWebDriver(), OneDriveConstants.Word, OneDriveConstants.WordFileType, "Sample");
	}

	/**
	 * Create Excel File Test
	 */
	@Priority(5)
	@Test(groups = { "sanity" })
	public void createExcelFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		excelFileName = onedrive.createFile(getWebDriver(), OneDriveConstants.Excel, OneDriveConstants.ExcelFileType, "Sample");
	}

	/**
	 * Create Powerpoint File Test
	 */
	@Priority(6)
	@Test(groups = { "sanity" })
	public void createPowerPointFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		powerpointFileName = onedrive.createFile(getWebDriver(), OneDriveConstants.PowerPoint, OneDriveConstants.PowerPointFileType, "Sample");
	}

	/**
	 * Create Onenote File Test
	 */
	@Priority(7)
	@Test(groups = { "sanity" })
	public void createOneNoteFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onenoteFileName = onedrive.createFile(getWebDriver(), OneDriveConstants.OneNote, OneDriveConstants.OneNoteFileType, "Sample");
	}

	/**
	 * Create Excel Survey File Test
	 */
	@Priority(8)
	@Test(groups = { "sanity" })
	public void createExcelSurveyFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		excelsurveyFileName = onedrive.createFile(getWebDriver(), OneDriveConstants.ExcelSurvey, OneDriveConstants.ExcelSurveyFileType, "Sample");
	}

	/**
	 * Create Plain Text File Test
	 */
	@Priority(9)
	@Test(groups = { "sanity" })
	public void createPlainTextFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		plaintextFileName = onedrive.createFile(getWebDriver(), OneDriveConstants.PlainText, OneDriveConstants.PlainTextFileType, "Sample");
	}

	/**
	 * Delete Word File Test
	 */
	@Priority(10)
	@Test(groups = { "sanity" })
	public void deleteWordFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.deleteFile(getWebDriver(), OneDriveConstants.Word, OneDriveConstants.WordFileType, wordFileName);		
	}
	
	/**
	 * Delete Excel File Test
	 */
	@Priority(11)
	@Test(groups = { "sanity" })
	public void deleteExcelFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.deleteFile(getWebDriver(), OneDriveConstants.Excel, OneDriveConstants.ExcelFileType, excelFileName);		
	}
	
	/**
	 * Delete Powerpoint File Test
	 */
	@Priority(12)
	@Test(groups = { "sanity" })
	public void deletePowerPointFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.deleteFile(getWebDriver(), OneDriveConstants.PowerPoint, OneDriveConstants.PowerPointFileType, powerpointFileName);		
	}
	
	/**
	 * Delete One Note File Test
	 */
	@Priority(13)
	@Test(groups = { "sanity" })
	public void deleteOneNoteFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.deleteFile(getWebDriver(), OneDriveConstants.OneNote, OneDriveConstants.OneNoteFileType, onenoteFileName);		
	}
	
	/**
	 * Delete Excel Survey File Test
	 */
	@Priority(14)
	@Test(groups = { "sanity" })
	public void deleteExcelSurveyFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.deleteFile(getWebDriver(), OneDriveConstants.ExcelSurvey, OneDriveConstants.ExcelSurveyFileType, excelsurveyFileName);		
	}
	
	/**
	 * Delete Plain Text File Test
	 */
	@Priority(15)
	@Test(groups = { "sanity" })
	public void deletePlainTextFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.deleteFile(getWebDriver(), OneDriveConstants.PlainText, OneDriveConstants.PlainTextFileType, plaintextFileName);		
	}
	
	/**
	 * Upload File Test
	 */
	@Priority(16)
	@Test(groups = { "sanity" })
	public void uploadFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.uploadFile(getWebDriver(), "Test.docx");		
	}
	
	/**
	 * Download File Test
	 */
	@Priority(17)
	@Test(groups = { "sanity" })
	public void downloadFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.downloadFile(getWebDriver(), "Test");			
	}
	
	/**
	 * Delete File Test
	 */
	@Priority(18)
	@Test(groups = { "sanity" })
	public void deleteFile() {
		onedrive.login(getWebDriver(), OneDriveConstants.username, OneDriveConstants.password);
		onedrive.deleteFile(getWebDriver(), OneDriveConstants.Word, OneDriveConstants.WordFileType, "Test");		
	}
}
