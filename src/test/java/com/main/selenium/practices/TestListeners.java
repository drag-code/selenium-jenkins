package com.main.selenium.practices;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestListeners {

	String URL = "https://google.com";
	String screnshotsPath = "C:\\Users\\cacha\\Pictures\\Selenium";
	WebDriver driver;

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get(URL);
	}

	@Test(groups = { "Smoke" })
	public void test1() {
		System.out.println("WebLoginHome");
	}

	@Test(groups = { "Smoke" })
	public void failureTest() {
		driver.findElement(By.id("badId"));
	}

	@Test(groups = { "Smoke" })
	public void test2() {
		System.out.println("Login");
	}

	@Test(groups = { "Smoke" })
	public void test3() {
		System.out.println("good");
	}

	@AfterMethod
	public void takeScreenShotTo(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Test case " + result.getName() + " has failed");
			System.out.println("Taking screenshot...");
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(src, new File(screnshotsPath + "\\error.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@AfterClass
	public void teardown() {
		driver.quit();
	}
}
