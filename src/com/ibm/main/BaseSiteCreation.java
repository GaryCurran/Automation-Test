package com.ibm.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BaseSiteCreation {

	private String username;
	private String password;
	private String driverPath;
	private String clientIdentifier;
	private String url;
	private String env;
	private Properties props;
	private String browser;
	static WebDriver driver;

	public void setupTest() throws IOException {

		props = new Properties();
		String fileName = "config.properties";
		InputStream is = NewClient.class.getResourceAsStream(fileName);

		if (is != null) {
			props.load(is);
			setupProperties();
			if (driver == null) {
				configureWebDriver();
			}
		} else {
			throw new FileNotFoundException("Property file " + fileName + " not found");
		}
	}

	public void setupProperties() {

		setUsername(props.getProperty("username"));
		setPassword(props.getProperty("password"));
		setDriverPath(props.getProperty("driverPath"));
		setclientIdentifier(props.getProperty("clientIdentifier"));
		setUrl(props.getProperty("url"));
		setEnv(props.getProperty("env"));
		setBrowser(props.getProperty("browser"));

	}

	public void configureWebDriver() {

		String browserType = props.getProperty("browser").toLowerCase().trim();

		if (browserType.contains("firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}

		if (browserType.contains("ie")) {
			System.setProperty("webdriver.ie.driver", getDriverPath() + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}

		if (browserType.contains("headless")) {
			driver = new HtmlUnitDriver(true);
			
		}

		if (browserType.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", getDriverPath() + "chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	public void setclientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDriverPath() {
		return driverPath;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public String getUrl() {
		return url;
	}

	public String getEnv() {
		return env;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		BaseSiteCreation.driver = driver;
	}

}
