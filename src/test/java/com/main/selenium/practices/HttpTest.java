package com.main.selenium.practices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.checkerframework.common.reflection.qual.NewInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

public class HttpTest {
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
	public void testBrokenLink() {
		List<String> urls = driver
				.findElements(By.xpath("//div[@id = 'gf-BIG'] //a"))
				.stream()
				.map(element -> element.getAttribute("href"))
				.collect(Collectors.toList());
		HttpURLConnection connection;
		SoftAssert softAssert = new SoftAssert();
		for (String url : urls) {
			try {
				System.out.println("Sending request to " + url + "...");
				connection = (HttpURLConnection) new java.net.URL(url).openConnection();
				connection.setRequestMethod("HEAD");
				int responseCode = connection.getResponseCode();
				softAssert.assertTrue(!(responseCode >= 400), "The link " + url + " is broken");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		softAssert.assertAll();
	}

	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
