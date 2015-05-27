package wdframework.listeners;

import java.io.File;



import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


/**
 * Screenshot listener
 * @author Eldo Rajan
 *
 */
public class ScreenShotListener extends TestListenerAdapter implements ISuiteListener {


	File scrFile = null;
	WebDriver augmentedDriver = null;
	static File screenShotFolder = null;
	

	public void onStart(ISuite iSuite){
		
		try{	
			/*String host = iSuite.getParameter("mode");
			if(host.equals("local")){
				screenShotFolder = new File((System.getProperty("user.dir")+File.separator+"target" + File.separator + "logs"));
				if(!screenShotFolder.exists()){
					screenShotFolder.mkdir();
				}else{
					deleteDirectory(screenShotFolder);
					screenShotFolder.mkdir();
				}
			}*/
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void onTestSuccess(ITestResult iTestResult){
		
	}

	public void onTestFailure(ITestResult iTestResult) {
		
	}

	public void onTestSkipped(ITestResult iTestResult) {
		
	}


	@Override
	public void onFinish(ITestContext iTestContext){
		
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub

	}

	/**
	 * delete directory
	 * @param path
	 */
	public void deleteDirectory(File path) {
		if (path == null)
			return;
		if (path.exists()){
			for(File f : path.listFiles()){
				if(f.isDirectory()){
					deleteDirectory(f);
					f.delete();
				}else{
					f.delete();
				}
			}
			path.delete();
		}
	}


	
}


