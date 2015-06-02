package wdframework.common;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import wdframework.driver.BrowserType;
import wdframework.driver.Driver;
import wdframework.driver.DriverType;
import wdframework.driver.TestConfig;
import wdframework.logger.Logger;

/**
 * Common super class for driver running 
 * @author Eldo Rajan
 *
 */
public class DriverRunner extends Driver{


	DriverType mode=null;BrowserType browser=null;String baseUrl=null;
	// Separate driver instance for each thread
	protected ThreadLocal<WebDriver> localdriver = new ThreadLocal<WebDriver>();
	protected ThreadLocal<RemoteWebDriver> remotedriver = new ThreadLocal<RemoteWebDriver>();
	protected ThreadLocal<String> sessionId = new ThreadLocal<String>();
	TestConfig testconfig = null;   
			
	/**
	 * Before Method instantiations
	 * @param theTestContext
	 * @param theTestResult
	 */
	@BeforeMethod(alwaysRun = true)
	public void startBrowser(ITestContext theTestContext) {
		testconfig = new TestConfig();
		String suiteName = theTestContext.getSuite().getXmlSuite().getName();
		try {

			browser = BrowserType.getBrowserType(System.getProperty("browser"));
			mode = DriverType.getDriverType(System.getProperty("mode"));
			baseUrl = System.getProperty("baseurl");
			if(browser==null){
				browser = BrowserType.getBrowserType(theTestContext.getCurrentXmlTest().getParameter("browser"));            	
			}
			if(mode==null){
				mode = DriverType.getDriverType(theTestContext.getCurrentXmlTest().getParameter("mode"));
			}
			if(baseUrl==null){
				baseUrl = theTestContext.getCurrentXmlTest().getParameter("baseurl");	
			}

			if(browser!=null){
				testconfig.setBrowser(browser);
			}
			if(mode!=null){
				testconfig.setMode(mode);
			}
			if(baseUrl!=null){
				testconfig.setBaseUrl(baseUrl);
			}

			BrowserType browser = testconfig.getBrowser();
			String ieDriver = testconfig.getIEDriver();
			String chromeDriver = testconfig.getChromeDriver();
			mode = testconfig.getMode();
			String huburl = testconfig.getHubUrl();
			String baseUrl = testconfig.getBaseUrl();

			switch (mode) {
			case Local: {            
				localdriver.set(browserProfileConfiguration(browser, ieDriver, chromeDriver));
				break;
			}
			case Grid: {
				remotedriver.set(browserProfileConfigurationRemote(browser, huburl));
				break;
			}
			case Cloud: {
				remotedriver.set(browserProfileConfigurationCloud(browser, huburl, suiteName));
				sessionId.set(((RemoteWebDriver) getWebDriver()).getSessionId().toString());
				break;
			}

			default:           
				try {
					Logger.error(
							"Fail to intialize the driver, please check mode parameter.");
					throw new Exception(
							"Fail to intialize the driver, please check mode parameter.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			getWebDriver().get(baseUrl);
			getWebDriver().manage().window().maximize();
			getWebDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);            
		} catch (Exception e) {
			e.printStackTrace();
			/*throw new RuntimeException("Exception during browser startup: ",
                    e);*/
		}


	}

	/**
	 * After method disembodifying 
	 */
	@AfterMethod(alwaysRun = true)
	public void stopBrowser() {
		Logger.info("Closing the browser session after the method gets executed in progress");
		if (localdriver.get() != null) {
			getWebDriver().quit();
			localdriver.remove();
		}else if (remotedriver.get() != null) {
			getWebDriver().quit();
			remotedriver.remove();
		}else{
			getWebDriver().quit();
		}

		Logger.info("Closed the browser session after the method gets executed");
	}

	/**
	 * gets webdriver instance   
	 * @return
	 */
	public WebDriver getWebDriver(){
		WebDriver driver=null;
		switch (mode) {
		case Local: {            
			driver = localdriver.get();
			break;
		}
		case Grid: {
			driver = remotedriver.get();
			break;
		}
		case Cloud: {
			driver = remotedriver.get();
			break;
		}

		default:           
			try {
				Logger.error(
						"Fail to intialize the driver, please check mode parameter.");
				throw new Exception(
						"Fail to intialize the driver, please check mode parameter.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return driver;

	}
	
	
}
