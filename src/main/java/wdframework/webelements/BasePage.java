package wdframework.webelements;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;

/**
 * Base html page abstraction. Used by PageObject and WebPageSection
 */
public abstract class BasePage {


	

	private String LOCATOR_CONFIG_PROPERTIES = "locators/FILENAME.properties";
	Properties properties = new Properties();
	

	public enum LocatorType {
		CSS, XPATH, ID, NAME, CLASS, DOM, LINK, LINKP
	}
	
	
	
	public By getBy(String page,String elementName) {
		Properties properties = new Properties();
		properties = loadPropertiesFile(page);		
		String locator = properties.getProperty(elementName.toLowerCase());
		LocatorType identifier = LocatorType.valueOf(locator.toUpperCase().substring(0, locator.indexOf("=")));
		locator = locator.substring(locator.indexOf("=")+1);

		By locatorIdentifiedBy=null;
		switch (identifier) {
		case XPATH:
			locatorIdentifiedBy = By.xpath(locator);
			break;
		case CSS:
			locatorIdentifiedBy = By.cssSelector(locator);
			break;
		case ID:
			locatorIdentifiedBy = By.id(locator);
			break;
		case NAME:
			locatorIdentifiedBy = By.name(locator);
			break;
		case CLASS:
			locatorIdentifiedBy = By.className(locator);
			break;
		case LINK:
			locatorIdentifiedBy = By.linkText(locator);
			break;
		case LINKP:
			locatorIdentifiedBy = By.partialLinkText(locator);
			break;
		default:
			locatorIdentifiedBy = By.cssSelector(locator);
			break;
		}
		return locatorIdentifiedBy;
	}
	
	public By getBy(String locator) {
		LocatorType identifier = LocatorType.valueOf(locator.toUpperCase().substring(0, locator.indexOf("=")));
		locator = locator.substring(locator.indexOf("=")+1);

		By locatorIdentifiedBy=null;
		switch (identifier) {
		case XPATH:
			locatorIdentifiedBy = By.xpath(locator);
			break;
		case CSS:
			locatorIdentifiedBy = By.cssSelector(locator);
			break;
		case ID:
			locatorIdentifiedBy = By.id(locator);
			break;
		case NAME:
			locatorIdentifiedBy = By.name(locator);
			break;
		case CLASS:
			locatorIdentifiedBy = By.className(locator);
			break;
		case LINK:
			locatorIdentifiedBy = By.linkText(locator);
			break;
		case LINKP:
			locatorIdentifiedBy = By.partialLinkText(locator);
			break;
		default:
			locatorIdentifiedBy = By.cssSelector(locator);
			break;
		}
		return locatorIdentifiedBy;
	}
	
	public String getLocator(String page,String elementName) {
		Properties properties = new Properties();
		properties = loadPropertiesFile(page);		
		String locator = properties.getProperty(elementName.toLowerCase());
		
		return locator;
	}
	
	private Properties loadPropertiesFile(String page) {
		Properties result = new Properties();
		try {
			String fileLocation = LOCATOR_CONFIG_PROPERTIES.replace("FILENAME", page.toLowerCase());
			String filename = getPropertyOrNull(fileLocation);
			// it is not defined, use default
			if (filename == null) {
				filename = fileLocation;
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

	public String getClassName() {
		return getClass().getName().replace("wdframework.pageobjects.", "");
	}

	
}
