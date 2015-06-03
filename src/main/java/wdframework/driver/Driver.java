package wdframework.driver;



import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import wdframework.logger.Logger;

import com.opera.core.systems.OperaDriver;

/**
 * Driver Class
 * @author Eldo Rajan
 *
 */
public class Driver {


	/**
	 * browser profile configuration 
	 * @param browser
	 * @param ieDriverBinary
	 * @param chromeDriverBinary
	 * @param phantomJSDriverBinary
	 * @return
	 */
	public WebDriver browserProfileConfiguration(BrowserType browser,
			String ieDriverBinary, String chromeDriverBinary, String phantomJSDriverBinary){
		Logger.info("Starting "+browser+" browser in local configuration");
		WebDriver driver = null;

		switch (browser) {
		case Firefox: {            
			driver = new FirefoxDriver(getDesiredCapabilities(browser,phantomJSDriverBinary));
			break;
		}
		case InternetExplorer: {
			System.setProperty("webdriver.ie.driver", ieDriverBinary);
			driver = new InternetExplorerDriver(getDesiredCapabilities(browser,phantomJSDriverBinary));            
			break;
		}
		case Chrome: {
			System.setProperty("webdriver.chrome.driver", chromeDriverBinary);
			driver = new ChromeDriver(getDesiredCapabilities(browser,phantomJSDriverBinary));
			break;
		}
		case Opera: {
			System.setProperty("os.name", "windows");
			driver = new OperaDriver(getDesiredCapabilities(browser,phantomJSDriverBinary));            
			break;
		}
		case Safari:
			driver = new SafariDriver(getDesiredCapabilities(browser,phantomJSDriverBinary));
			break;
		case PhantomJS:
			driver = new PhantomJSDriver(getDesiredCapabilities(browser,phantomJSDriverBinary));
			break;
		case Android:	
			try {
				driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), getDesiredCapabilities(browser,phantomJSDriverBinary));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		default:           
			try {
				Logger.error("Fail to start browser, please check browser parameter.");
				throw new Exception(
						"Fail to start browser, please check browser parameter.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Logger.info("Started "+browser+" browser in local configuration");
		return driver;
	}

	/**
	 * browser profile configuration for remote
	 * @param browser
	 * @param host
	 * @param phantomJSDriverBinary
	 * @return
	 */
	public RemoteWebDriver browserProfileConfigurationRemote(BrowserType browser, String host, String phantomJSDriverBinary){
		Logger.info("Starting "+browser+" browser in remote configuration");

		RemoteWebDriver driver = null;
		String huburl = "http://"+host+"/wd/hub";

		try {
			driver = new RemoteWebDriver(new URL(huburl), getDesiredCapabilities(browser, phantomJSDriverBinary));
		} catch (Exception e) {
			Logger.error("Fail to start browser in remote configuration");
			e.printStackTrace();
		}
		Logger.info("Started "+browser+" browser in remote configuration");
		return driver;
	}

	/**
	 * browser profile configuration for cloud
	 * @param browser
	 * @param host
	 * @param suiteName
	 * @return
	 */
	public RemoteWebDriver browserProfileConfigurationCloud(BrowserType browser, String host, String suiteName){
		Logger.info("Starting "+browser+" browser in cloud configuration");

		RemoteWebDriver driver = null;
		String huburl = "http://"+host+"@ondemand.saucelabs.com:80/wd/hub";

		try {
			driver = new RemoteWebDriver(new URL(huburl), getDesiredCapabilitiesCloud(browser,suiteName));
		} catch (Exception e) {
			Logger.error("Fail to start browser in cloud configuration");
			e.printStackTrace();
		}
		Logger.info("Started "+browser+" browser in cloud configuration");
		return driver;
	}

	/**
	 * get desired capabilities
	 * @param browserType
	 * @param phantomjsDriver
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public DesiredCapabilities getDesiredCapabilities(BrowserType browserType,String phantomjsDriver) {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		switch(browserType) {
		case InternetExplorer:
			desiredCapabilities = DesiredCapabilities.internetExplorer();
			desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
			desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
			desiredCapabilities.setCapability(CapabilityType.ENABLE_PERSISTENT_HOVERING, false);
			desiredCapabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, 1);
			desiredCapabilities.setCapability("javascriptEnabled",true);
			desiredCapabilities.setCapability("autoAcceptAlerts",true);
			desiredCapabilities.setCapability("unexpectedAlertBehaviour", UnexpectedAlertBehaviour.DISMISS);
			desiredCapabilities.setCapability("handlesAlerts", "dismissAlerts");
			desiredCapabilities.setCapability("requireWindowFocus", true);
			desiredCapabilities.setCapability("ie.ensureCleanSession", true);
			desiredCapabilities.setBrowserName("internet explorer");
			desiredCapabilities.setVersion("11");
			break;
		case Chrome:
			desiredCapabilities = DesiredCapabilities.chrome();	
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("allow-running-insecure-content");
			options.addArguments("ignore-certificate-errors");
			//desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			desiredCapabilities.setCapability("elementScrollBehavior", 1);
			break;
		case Opera:
			desiredCapabilities = DesiredCapabilities.opera();	
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
			desiredCapabilities.setCapability("opera.arguments", "-nowin -nomail -fullscreen");
		case Safari:
			desiredCapabilities = DesiredCapabilities.safari();	
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
			desiredCapabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, 1);
			desiredCapabilities.setCapability("javascriptEnabled",true);
			desiredCapabilities.setCapability("autoAcceptAlerts",true);
			desiredCapabilities.setCapability("unexpectedAlertBehaviour", UnexpectedAlertBehaviour.DISMISS);
			desiredCapabilities.setCapability("handlesAlerts", "dismissAlerts");
			desiredCapabilities.setCapability("safari.options.dataDir", System.getProperty("user.home")+File.separator+"Downloads");
			break;
		case Firefox:
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(false);
			profile.setEnableNativeEvents(true);

			profile.setPreference("browser.download.folderList", 1);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.manager.focusWhenStarting", false);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", true);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
			profile.setPreference("pdfjs.disabled", true);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,application/vnd.ms-powerpoint,application/octet-stream,application/msword,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.template,application/vnd.openxmlformats-officedocument.presentationml.template,application/vnd.openxmlformats-officedocument.presentationml.slideshow,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.presentationml.slide,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.wordprocessingml.template,application/vnd.ms-excel.addin.macroEnabled.12,application/vnd.ms-excel.sheet.binary.macroEnabled.12,application/vnd.ms-word.document.macroEnabled.12,application/vnd.ms-word.template.macroEnabled.12,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.template,application/vnd.ms-excel.sheet.macroEnabled.12,application/vnd.ms-excel.template.macroEnabled.12,application/vnd.openxmlformats-officedocument.presentationml.template,application/vnd.ms-powerpoint.addin.macroEnabled.12,application/vnd.ms-powerpoint.presentation.macroEnabled.12,application/vnd.ms-powerpoint.template.macroEnabled.12,application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
			profile.setPreference("security.mixed_content.block_active_content", false);
			profile.setPreference("security.mixed_content.block_display_content", true);
			profile.setPreference("capability.policy.default.Window.QueryInterface", "allAccess");  
			profile.setPreference("capability.policy.default.Window.frameElement.get","allAccess");

			desiredCapabilities = DesiredCapabilities.firefox();
			desiredCapabilities.setCapability("elementScrollBehavior", 1);
			desiredCapabilities.setCapability(FirefoxDriver.PROFILE, profile);
			break;
		case PhantomJS:
			String phantomJsPath = System.getProperty("user.dir") +File.separator+"src"+File.separator+"test"+File.separator+
			"resources"+File.separator+"lib"+File.separator+"phantomjs.exe";
			desiredCapabilities = DesiredCapabilities.phantomjs();
			ArrayList<String> cliArgsDesiredCapabilities = new ArrayList<String>();
			cliArgsDesiredCapabilities.add("--ssl-protocol=any");
			cliArgsDesiredCapabilities.add("--ignore-ssl-errors=true");
			cliArgsDesiredCapabilities.add("--web-security=false");
			desiredCapabilities.setCapability("takesScreenshot", true);
			desiredCapabilities.setCapability(
					PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsDesiredCapabilities);
			desiredCapabilities.setCapability(
					PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
					new String[] { "--logLevel=2" });
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,phantomJsPath);
			desiredCapabilities.setCapability("phantomjs.page.settings.loadImages", true);
			desiredCapabilities.setCapability("phantomjs.page.settings.localToRemoteUrlAccessEnabled", true);
			desiredCapabilities.setJavascriptEnabled(true);			
			break;
		case Android:
			File appDir = new File("src/test/resources/data");
			File app = new File(appDir, "appium.apk");
			desiredCapabilities = DesiredCapabilities.android();
			desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
			desiredCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);			
			break;
		default:
			desiredCapabilities = DesiredCapabilities.firefox();
			desiredCapabilities.setCapability("elementScrollBehavior", 1);
			break;
		}
		return desiredCapabilities;
	}

	/**
	 * get desired capabilities for cloud
	 * @param browserType
	 * @param suiteName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public DesiredCapabilities getDesiredCapabilitiesCloud(BrowserType browserType, String suiteName) {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		switch(browserType) {
		case InternetExplorer:
			desiredCapabilities = DesiredCapabilities.internetExplorer();
			desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
			desiredCapabilities.setCapability(CapabilityType.ENABLE_PERSISTENT_HOVERING, false);
			desiredCapabilities.setCapability("javascriptEnabled",true);
			desiredCapabilities.setCapability("autoAcceptAlerts",true);
			desiredCapabilities.setCapability("unexpectedAlertBehaviour", UnexpectedAlertBehaviour.DISMISS);
			desiredCapabilities.setCapability("handlesAlerts", "dismissAlerts");
			desiredCapabilities.setCapability("requireWindowFocus", true);
			desiredCapabilities.setCapability("ie.ensureCleanSession", true);
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, "Windows 8.1");
			desiredCapabilities.setCapability(CapabilityType.VERSION, "11.0");
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");			
			break;
		case Chrome:
			desiredCapabilities = DesiredCapabilities.chrome();	
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("allow-running-insecure-content");
			options.addArguments("ignore-certificate-errors");
			//desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, "Windows 8.1");
			desiredCapabilities.setCapability(CapabilityType.VERSION, "43.0");
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			break;
		case Safari:
			desiredCapabilities = DesiredCapabilities.safari();	
			desiredCapabilities.setCapability("javascriptEnabled",true);
			desiredCapabilities.setCapability("autoAcceptAlerts",true);
			desiredCapabilities.setCapability("unexpectedAlertBehaviour", UnexpectedAlertBehaviour.DISMISS);
			desiredCapabilities.setCapability("handlesAlerts", "dismissAlerts");
			desiredCapabilities.setCapability("safari.options.dataDir", System.getProperty("user.home")+File.separator+"Downloads");
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, "OS X 10.10");
			desiredCapabilities.setCapability(CapabilityType.VERSION, "8.0");
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
			break;
		case Firefox:
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(false);
			profile.setEnableNativeEvents(true);

			profile.setPreference("browser.download.folderList", 1);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.manager.focusWhenStarting", false);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", true);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
			profile.setPreference("pdfjs.disabled", true);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,application/vnd.ms-powerpoint,application/octet-stream,application/msword,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.template,application/vnd.openxmlformats-officedocument.presentationml.template,application/vnd.openxmlformats-officedocument.presentationml.slideshow,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.presentationml.slide,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.wordprocessingml.template,application/vnd.ms-excel.addin.macroEnabled.12,application/vnd.ms-excel.sheet.binary.macroEnabled.12,application/vnd.ms-word.document.macroEnabled.12,application/vnd.ms-word.template.macroEnabled.12,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.template,application/vnd.ms-excel.sheet.macroEnabled.12,application/vnd.ms-excel.template.macroEnabled.12,application/vnd.openxmlformats-officedocument.presentationml.template,application/vnd.ms-powerpoint.addin.macroEnabled.12,application/vnd.ms-powerpoint.presentation.macroEnabled.12,application/vnd.ms-powerpoint.template.macroEnabled.12,application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
			profile.setPreference("security.mixed_content.block_active_content", false);
			profile.setPreference("security.mixed_content.block_display_content", true);
			profile.setPreference("capability.policy.default.Window.QueryInterface", "allAccess");  
			profile.setPreference("capability.policy.default.Window.frameElement.get","allAccess");

			desiredCapabilities = DesiredCapabilities.firefox();
			desiredCapabilities.setCapability(FirefoxDriver.PROFILE, profile);
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, "Windows 8.1");
			desiredCapabilities.setCapability(CapabilityType.VERSION, "28.0");
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
			break;
		default:
			desiredCapabilities = DesiredCapabilities.chrome();	
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, "Windows 8.1");
			desiredCapabilities.setCapability(CapabilityType.VERSION, "43.0");
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			break;
		}
		desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		desiredCapabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, 1);
		desiredCapabilities.setCapability("screenResolution", "1024x768");
		desiredCapabilities.setCapability("name", suiteName);
		desiredCapabilities.setCapability("seleniumVersion","2.45.0");
		desiredCapabilities.setCapability("chromedriverVersion","2.15");
		desiredCapabilities.setCapability("iedriverVersion","2.45.0");
		return desiredCapabilities;
	}
}
