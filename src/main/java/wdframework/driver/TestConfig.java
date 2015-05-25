package wdframework.driver;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class TestConfig {

	private static final String CONFIG_MODE = "mode";
	private static final String CONFIG_HUBURL = "huburl";
	private static final String CONFIG_BASEURL = "baseurl";
	private static final String CONFIG_BROWSER = "browser";
	private static final String CONFIG_CHROMEDRIVER = "chromedriver";
	private static final String CONFIG_IEDRIVER = "iedriver";
	private static final String CONFIG_PHANTOMJSDRIVER = "phantomjsdriver";
	private static final String CONFIG_PROPERTIES = "config/config.properties";

	private DriverType mode;
	private String huburl;
	private String baseUrl;		
	private BrowserType browser;
	private String chromedriver;
	private String iedriver;
	private String phantomjsdriver;
	private Properties properties = new Properties();

	public TestConfig() {
		properties = loadPropertiesFile();
		mode = DriverType.getDriverType(getPropertyOrThrowException(CONFIG_MODE));
		huburl = getPropertyOrThrowException(CONFIG_HUBURL);
		baseUrl = getPropertyOrThrowException(CONFIG_BASEURL);
		browser = BrowserType.getBrowserType(getPropertyOrThrowException(CONFIG_BROWSER));
		chromedriver = getPropertyOrThrowException(CONFIG_CHROMEDRIVER);
		iedriver = getPropertyOrThrowException(CONFIG_IEDRIVER);
		phantomjsdriver = getPropertyOrThrowException(CONFIG_PHANTOMJSDRIVER);
		
		try {
			URL resourceUrl = getClass().getResource(chromedriver);
			Path resourcePath = Paths.get(resourceUrl.toURI());
			chromedriver = resourcePath.toString();
			
			URL resourceUrl2 = getClass().getResource(iedriver);
			Path resourcePath2 = Paths.get(resourceUrl2.toURI());
			iedriver = resourcePath2.toString();
			
			URL resourceUrl3 = getClass().getResource(phantomjsdriver);
			Path resourcePath3 = Paths.get(resourceUrl3.toURI());
			phantomjsdriver = resourcePath3.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	private Properties loadPropertiesFile() {
		Properties result = new Properties();
		try {
			// get specified property file
			String filename = getPropertyOrNull(CONFIG_PROPERTIES);
			// it is not defined, use default
			if (filename == null) {
				filename = CONFIG_PROPERTIES;
			}
			// try to load from classpath
			InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
			// no file in classpath, look on disk
			if (stream == null) {
				stream = new FileInputStream(new File(filename));
			}
			
			result.load(stream);
		} catch (IOException e) {
			try {
				throw new Exception("Property file is not found");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;
	}

	public String getPropertyOrNull(String name) {
		return getProperty(name, false);
	}

	public String getPropertyOrThrowException(String name) {
		return getProperty(name, true);
	}

	private String getProperty(String name, boolean forceExceptionIfNotDefined) {
		String result;
		if ((result = System.getProperty(name, null)) != null && result.length() > 0) {
			return result;
		} else if ((result = getPropertyFromPropertiesFile(name)) != null && result.length() > 0) {
			return result;
		} else if (forceExceptionIfNotDefined) {
			try {
				throw new Exception("Unknown property: [" + name + "]");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private String getPropertyFromPropertiesFile(String name) {
		Object result = properties.get(name);
		if (result == null) {
			return null;
		} else {
			return result.toString();
		}
	}

	public DriverType getMode() {
		return mode;
	}
	
	public String getHubUrl() {
		return huburl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public BrowserType getBrowser() {
		return browser;
	}
	
	public String getChromeDriver() {
		return chromedriver;
	}

	public String getIEDriver() {
		return iedriver;
	}
	
	public String getPhantomJsDriver() {
		return phantomjsdriver;
	}
	
	public void setMode(DriverType mode) {
		this.mode=mode;
	}
	
	public void setHubUrl(String huburl) {
		this.huburl= huburl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl= baseUrl;
	}

	public void setBrowser(BrowserType browser) {
		this.browser= browser;
	}
	
	public void setChromeDriver(String chromedriver) {
		this.phantomjsdriver= chromedriver;
	}

	public void setIEDriver(String iedriver) {
		this.iedriver= iedriver;
	}
	
	public void setPhantomJsDriver(String phantomjsdriver) {
		this.phantomjsdriver= phantomjsdriver;
	}
}