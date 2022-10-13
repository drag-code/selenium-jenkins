package com.main.selenium.practices;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUploadingTest {

	String URL = "http://admin:admin@the-internet.herokuapp.com/";
	String downloadsPath = System.getProperty("user.dir") + "\\files\\";
	String autoItPath = "C:\\Users\\esteb\\Documents\\autoit\\";
	String expectedFileName = "Bash-Beginners-Guide.docx";
	ChromeDriver driver;
 	
	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.default_directory", downloadsPath);
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}
	
	@Test
	public void testAuth() {
		driver.get(URL);
		driver.findElement(By.linkText("Basic Auth")).click();
	}
	
	@Test
	public void testFileUpload() {
		driver.get("https://www.ilovepdf.com/es");
		driver.findElement(By.cssSelector("a[title = 'PDF a Word']")).click();
		driver.findElement(By.id("pickfiles")).click();
		try {
			Thread.sleep(2000);
			Runtime.getRuntime().exec(autoItPath + "uploadFile.exe");
			Thread.sleep(3000);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFileDownload() {
		driver.get("https://www.ilovepdf.com/es");
		driver.findElement(By.cssSelector("a[title = 'PDF a Word']")).click();
		driver.findElement(By.id("pickfiles")).click();
		try {
			Thread.sleep(2000);
			Runtime.getRuntime().exec(autoItPath + "uploadFile.exe");
			driver.findElement(By.id("processTask")).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement processSection = driver.findElement(By.id("uploading"));
			wait.until(ExpectedConditions.invisibilityOf(processSection));
			Thread.sleep(10000);
			Runtime.getRuntime().exec(autoItPath + "downloadFile.exe");
			Thread.sleep(10000);
			File expectedFile = new File(downloadsPath + expectedFileName);
			assertTrue(expectedFile.exists());
//			FileUtils.delete(expectedFile);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
