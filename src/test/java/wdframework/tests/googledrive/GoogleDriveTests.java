package wdframework.tests.googledrive;

import org.testng.annotations.Test;

import wd.common.CommonTest;
import wdframework.listeners.Priority;
import wdframework.constants.googledrive.GoogleDriveConstants;

/**
 * Onedrive Test Suite
 * @author Eldo Rajan
 *
 */
public class GoogleDriveTests extends CommonTest{
	String wordFileName="";String excelFileName="";String powerpointFileName="";
	String onenoteFileName="";String excelsurveyFileName="";String plaintextFileName="";
	
	/**
	 * Login Test
	 */
	@Priority(1)
	@Test(groups = { "sanity" })
	public void login() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
	}

	/**
	 * Create Folder Test
	 */
	@Priority(2)
	@Test(groups = { "sanity" })
	public void createFolder() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.createFolder(getWebDriver(), GoogleDriveConstants.Folder, "Sample");
	}

	/**
	 * Delete Folder Test
	 */
	@Priority(3)
	@Test(groups = { "sanity" })
	public void deleteFolder() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.deleteFolder(getWebDriver(), GoogleDriveConstants.Folder, "Sample");
	}
	
	/**
	 * Upload file
	 */
	@Priority(4)
	@Test(groups = { "sanity" })
	public void uploadFile() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.uploadFile(getWebDriver(),GoogleDriveConstants.UploadFiles, "Test.docx");		
	}
	
	/**
	 * Download file
	 */
	@Priority(5)
	@Test(groups = { "sanity" })
	public void downloadFile() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.downloadFile(getWebDriver(), "Test.docx");			
	}
	
	/**
	 * Delete uploaded file
	 */
	@Priority(6)
	@Test(groups = { "sanity" })
	public void deleteUploadedWordFile() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.deleteFile(getWebDriver(), "Test.docx");			
	}
	
}
