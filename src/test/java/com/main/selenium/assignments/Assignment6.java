package com.main.selenium.assignments;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Assignment6 {
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
		WebElement label = driver.findElement(By.xpath("//label[@for='bmw']"));
		label.findElement(By.id("checkBoxOption1")).click();
		String textString = label.getText();
		System.out.println(textString);
		Select optionsDD = new Select(driver.findElement(By.id("dropdown-class-example")));
		optionsDD.selectByVisibleText(textString);
		driver.findElement(By.id("name")).sendKeys(textString);
		driver.findElement(By.id("alertbtn")).click();
		String alertMessage = driver.switchTo().alert().getText();
		System.out.println(alertMessage);
		assertTrue(alertMessage.contains(textString));
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
