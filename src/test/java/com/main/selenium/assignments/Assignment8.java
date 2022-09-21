package com.main.selenium.assignments;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Assignment8 {
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
	public void assignment() throws InterruptedException {
		WebElement countryBox = driver.findElement(By.id("autocomplete"));
		String countryClue = "ind";
		countryBox.sendKeys(countryClue);
		String actualCountry = "India";
		List<WebElement> countries = driver.findElements(By.className("ui-menu-item"));
		for (WebElement countryOption : countries) {
			if (countryOption.getText().equalsIgnoreCase(actualCountry)) {
				countryOption.click();
				break;
			}
		}
		String countryBoxContent = countryBox.getAttribute("value");
		assertEquals(countryBoxContent, actualCountry);
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
