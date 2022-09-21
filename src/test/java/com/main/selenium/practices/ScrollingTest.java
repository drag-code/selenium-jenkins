package com.main.selenium.practices;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ScrollingTest {
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
	public void testAmountCount() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//*[@class = 'tableFixHead'] //table[@id = 'product']")));
		List<WebElement> amountColumn = driver.findElements(By.xpath("//*[@class = 'tableFixHead'] //table[@id = 'product'] //td[4]"));
		Integer totalAmount = amountColumn
				.stream()
				.map(element -> Integer.parseInt(element.getText()))
				.reduce(0, Integer::sum);
		/*String totalAmountMessage = driver.findElement(By.className("totalAmount")).getText();
		assertTrue(totalAmountMessage.contains(totalAmount.toString()));*/
		String amountMessage = driver
				.findElement(By.className("totalAmount"))
				.getText();
		Integer expectedTotalAmount = Integer.valueOf(
				amountMessage
				.substring(amountMessage.indexOf(": "))
				.replace(": ", "")
			);
		assertEquals(totalAmount, expectedTotalAmount);
	}

	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
