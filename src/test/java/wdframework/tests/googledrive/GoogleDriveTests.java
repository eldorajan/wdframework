package wdframework.tests.googledrive;

import org.testng.annotations.Test;

import wdframework.common.CommonTest;
import wdframework.listeners.Priority;
import wdframework.constants.dropbox.DropBoxConstants;
import wdframework.constants.googledrive.GoogleDriveConstants;

/**
 * Onedrive Test Suite
 * @author Eldo Rajan
 *
 */
@SuppressWarnings("unused")
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
		googledrive.createFolder(getWebDriver(), GoogleDriveConstants.Folder, GoogleDriveConstants.SampleFolderName);
	}

	/**
	 * Delete Folder Test
	 */
	@Priority(3)
	@Test(groups = { "sanity" })
	public void deleteFolder() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.deleteFolder(getWebDriver(), GoogleDriveConstants.SampleFolderName);
	}
	
	/**
	 * Upload File Test
	 */
	@Priority(4)
	@Test(groups = { "sanity" })
	public void uploadFile() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.uploadFile(getWebDriver(),GoogleDriveConstants.UploadFiles, GoogleDriveConstants.SampleFileName);		
	}
	
	/**
	 * Download File Test
	 */
	@Priority(5)
	@Test(groups = { "sanity" })
	public void downloadFile() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.downloadFile(getWebDriver(), GoogleDriveConstants.SampleFileName);			
	}
	
	/**
	 * Delete File Test
	 */
	@Priority(6)
	@Test(groups = { "sanity" })
	public void deleteFile() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.deleteFile(getWebDriver(), GoogleDriveConstants.SampleFileName);			
	}
	
	
	/**
	 * Logout Test
	 */
	@Priority(7)
	@Test(groups = { "sanity" })
	public void logout() {
		googledrive.login(getWebDriver(), GoogleDriveConstants.username, GoogleDriveConstants.password);
		googledrive.logout(getWebDriver(), GoogleDriveConstants.username);
	}
}
