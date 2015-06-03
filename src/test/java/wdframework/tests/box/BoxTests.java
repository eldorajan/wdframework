package wdframework.tests.box;

import org.testng.annotations.Test;

import wdframework.common.CommonTest;
import wdframework.listeners.Priority;
import wdframework.constants.box.BoxConstants;

/**
 * Box Test Suite
 * @author Eldo Rajan
 *
 */
public class BoxTests extends CommonTest{
	
	/**
	 * Login Test
	 */
	@Priority(1)
	@Test(groups = { "sanity" })
	public void login() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
	}

	/**
	 * Create Folder Test
	 */
	@Priority(2)
	@Test(groups = { "sanity" })
	public void createFolder() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.createFolder(getWebDriver(), BoxConstants.SampleFolderName);
	}

	/**
	 * Download Folder Test
	 */
	@Priority(3)
	@Test(groups = { "sanity" })
	public void downloadFolder() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.downloadFolder(getWebDriver(), BoxConstants.SampleFolderName);
	}
	
	/**
	 * Delete Folder Test
	 */
	@Priority(4)
	@Test(groups = { "sanity" })
	public void deleteFolder() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.deleteFolder(getWebDriver(), BoxConstants.SampleFolderName);
	}
	
	/**
	 * Create File Test
	 */
	@Priority(5)
	@Test(groups = { "sanity" })
	public void uploadFile() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.uploadFile(getWebDriver(), BoxConstants.SampleFileName);
	}

	/**
	 * Download File Test
	 */
	@Priority(6)
	@Test(groups = { "sanity" })
	public void downloadFile() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.downloadFile(getWebDriver(), BoxConstants.SampleFileName);
	}
	
	/**
	 * Delete File Test
	 */
	@Priority(7)
	@Test(groups = { "sanity" })
	public void deleteFile() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.deleteFile(getWebDriver(), BoxConstants.SampleFileName);
	}
	
	/**
	 * Folder properties test
	 */
	@Priority(8)
	@Test(groups = { "sanity" })
	public void getFolderProperties() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.getFolderProperties(getWebDriver(), BoxConstants.SampleFolderName);
	}
	
	/**
	 * File properties test
	 */
	@Priority(9)
	@Test(groups = { "sanity" })
	public void getFileProperties() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.getFileProperties(getWebDriver(), BoxConstants.SampleFileName);
	}
	
	/**
	 * Logout Test
	 */
	@Priority(10)
	@Test(groups = { "sanity" })
	public void logout() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.logout(getWebDriver());
	}
	
}
