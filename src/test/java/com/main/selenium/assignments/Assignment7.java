package com.main.selenium.assignments;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Assignment7 {
	String URL = "https://rahulshettyacademy.com/AutomationPractice";
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
	public void assignment() {
		WebElement table = driver.findElement(By.xpath("//table[@name = 'courses']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", table);
		System.out.println("Number of rowws: " + table.findElements(By.tagName("tr")).size());
		System.out.println("Number of columns: " + table.findElements(By.tagName("th")).size());
		System.out.println("Second row: " + table.findElements(By.tagName("tr")).get(2).getText());
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
