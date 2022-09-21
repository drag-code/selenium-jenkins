package com.main.selenium.selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NewTest {
	String urlString = "https://www.google.com";
	WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(urlString);
	}
	
	@Test
	public void f() {
		System.out.println("F");
	}
	
	public void f1() {
		System.out.println("F1");
	}
	
	public void f2() {
		System.out.println("F2");
	}
	
	public void g() {
		System.out.println("G");
	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
	}
}
