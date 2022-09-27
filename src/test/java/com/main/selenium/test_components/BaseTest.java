package com.main.selenium.test_components;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.main.selenium.pom.LandingPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {

	public WebDriver driver;
	protected LandingPage landingPage;
	String URL = "https://rahulshettyacademy.com/client";

	private void initializeDriver() {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\main\\selenium\\resources\\global.properties"));
		} catch (IOException e) {
			System.out.println("File doesn't exists");
		}
		
		String browser = System.getProperty("browser") != null ?
				System.getProperty("browser") :
					properties.getProperty("browser");

		switch (browser.toLowerCase()) {
		case "chrome": {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		}
		case "firefox": {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}
		case "edge": {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		}
		default: {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}

	@BeforeMethod
	protected void launchApp() {
		initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.open(URL);
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	public String getScreenShot(String testCase, WebDriver driver2) {
		File src = ((TakesScreenshot) driver2).getScreenshotAs(OutputType.FILE);
		String destinyUrl = System.getProperty("user.dir") + "\\reports\\" + testCase + ".png";
		File destiny = new File(destinyUrl);
		try {
			FileUtils.copyFile(src, destiny);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return destinyUrl;
	}
}
