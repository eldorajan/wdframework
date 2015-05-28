package wdframework.tests.dropbox;

import org.testng.annotations.Test;

import wdframework.common.CommonTest;
import wdframework.listeners.Priority;
import wdframework.constants.dropbox.DropBoxConstants;

/**
 * Dropbox Test Suite
 * @author Eldo Rajan
 *
 */
public class DropBoxTests extends CommonTest{
	
	/**
	 * Login Test
	 */
	@Priority(1)
	@Test(groups = { "sanity" })
	public void login() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
	}

	/**
	 * Create Folder Test
	 */
	@Priority(2)
	@Test(groups = { "sanity" })
	public void createFolder() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
		dropbox.createFolder(getWebDriver(), DropBoxConstants.NewFolder, DropBoxConstants.SampleFolderName);
	}
	
	/**
	 * Download Folder Test
	 */
	@Priority(3)
	@Test(groups = { "sanity" })
	public void downloadFolder() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
		dropbox.downloadFolder(getWebDriver(), DropBoxConstants.SampleFolderName);
	}

	/**
	 * Delete Folder Test
	 */
	@Priority(4)
	@Test(groups = { "sanity" })
	public void deleteFolder() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
		dropbox.deleteFolder(getWebDriver(), DropBoxConstants.SampleFolderName);
	}
	
	/**
	 * Upload File Test
	 */
	@Priority(4)
	@Test(groups = { "sanity" })
	public void uploadFile() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
		dropbox.uploadFile(getWebDriver(),DropBoxConstants.UploadFiles, DropBoxConstants.SampleFileName);		
	}
	
	/**
	 * Download File Test
	 */
	@Priority(5)
	@Test(groups = { "sanity" })
	public void downloadFile() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
		dropbox.downloadFile(getWebDriver(), DropBoxConstants.SampleFileName);			
	}
	
	/**
	 * Delete File Test
	 */
	@Priority(6)
	@Test(groups = { "sanity" })
	public void deleteFile() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
		dropbox.deleteFile(getWebDriver(), DropBoxConstants.SampleFileName);			
	}
	
	/**
	 * Logout Test
	 */
	@Priority(7)
	@Test(groups = { "sanity" })
	public void logout() {
		dropbox.login(getWebDriver(), DropBoxConstants.username, DropBoxConstants.password);
		dropbox.logout(getWebDriver());
	}
}
