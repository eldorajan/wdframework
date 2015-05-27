package wdframework.tests.box;

import org.testng.annotations.Test;

import wd.common.CommonTest;
import wdframework.listeners.Priority;
import wdframework.constants.box.BoxConstants;

/**
 * Box Test Suite
 * @author Eldo Rajan
 *
 */
public class BoxTests extends CommonTest{
	String wordFileName="";String excelFileName="";String powerpointFileName="";
	String onenoteFileName="";String excelsurveyFileName="";String plaintextFileName="";
	
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
		box.createFolder(getWebDriver(), "Sample");
	}

	/**
	 * Download Folder Test
	 */
	@Priority(3)
	@Test(groups = { "sanity" })
	public void downloadFolder() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.downloadFolder(getWebDriver(), "Sample");
	}
	
	/**
	 * Delete Folder Test
	 */
	@Priority(4)
	@Test(groups = { "sanity" })
	public void deleteFolder() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.deleteFolder(getWebDriver(), "Sample");
	}
	
	/**
	 * Create File Test
	 */
	@Priority(5)
	@Test(groups = { "sanity" })
	public void uploadFile() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.uploadFile(getWebDriver(), "Test.docx");
	}

	/**
	 * Download File Test
	 */
	@Priority(6)
	@Test(groups = { "sanity" })
	public void downloadFile() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.downloadFile(getWebDriver(), "Test.docx");
	}
	
	/**
	 * Delete File Test
	 */
	@Priority(7)
	@Test(groups = { "sanity" })
	public void deleteFile() {
		box.login(getWebDriver(), BoxConstants.username, BoxConstants.password);
		box.deleteFile(getWebDriver(), "Test.docx");
	}
	
}
