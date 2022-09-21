package com.main.selenium.practices;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.openqa.selenium.support.locators.RelativeLocator.*;

public class TestNewFeatures {
	String URL = "https://rahulshettyacademy.com/angularpractice/";
	String screnshotsPath = "C:\\Users\\cacha\\Pictures\\Selenium";
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get(URL);
	}

	@Test
	public void testAboveLocator() {
		WebElement nameBox = driver.findElement(By.name("name"));
		String labelText = driver.findElement(with(By.tagName("label")).above(nameBox)).getText();
		System.out.println(labelText);
	}
	
	@Test
	public void testMultipleWindows() throws InterruptedException {
		String shopUrl = driver.findElement(By.cssSelector("a[href $= 'shop']")).getAttribute("href");
		driver.switchTo().newWindow(WindowType.WINDOW);
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String parentWindow = it.next();
		String childWindow = it.next();
		driver.switchTo().window(childWindow);
		driver.get(shopUrl);
		String firstProductName = driver
				.findElements(By.tagName("app-card"))
				.get(0)
				.findElement(By.cssSelector(".card-title a"))
				.getText();
		driver.switchTo().window(parentWindow);
		driver.findElement(By.name("name")).sendKeys(firstProductName);
		Thread.sleep(3000);
	}

	@Test
	public void testPartialScreenshot() throws IOException {
		String shopUrl = driver.findElement(By.cssSelector("a[href $= 'shop']")).getAttribute("href");
		driver.switchTo().newWindow(WindowType.WINDOW);
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String parentWindow = it.next();
		String childWindow = it.next();
		driver.switchTo().window(childWindow);
		driver.get(shopUrl);
		String firstProductName = driver
				.findElements(By.tagName("app-card"))
				.get(0)
				.findElement(By.cssSelector(".card-title a"))
				.getText();
		driver.switchTo().window(parentWindow);
		WebElement nameField = driver.findElement(By.name("name"));
		nameField.sendKeys(firstProductName);
		File src = nameField.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(screnshotsPath + "\\test.png"));
	}
	
	@Test
	public void testHeightAndWidth() {
		WebElement nameField = driver.findElement(By.name("name"));
		System.out.println("Name field is: " + nameField.getRect().getHeight() + "px tall and " + nameField.getRect().getWidth() + "px wide");
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
