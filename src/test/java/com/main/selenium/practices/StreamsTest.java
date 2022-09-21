package com.main.selenium.practices;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class StreamsTest {
	String URL = "https://rahulshettyacademy.com/seleniumPractise/";
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
	public void testTableIsSorted() {
		driver.findElement(By.cssSelector("a[href *= 'offers']")).click();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		while (it.hasNext()) {
			String window = it.next();
			driver.switchTo().window(window);
			if (driver.getCurrentUrl().equals("https://rahulshettyacademy.com/seleniumPractise/#/offers")) {
				WebElement table = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/table"));
				table.findElement(By.xpath("//tr/th[1]")).click();
				List<String> productNames = driver.findElements(By.xpath("//tr/td[1]")).stream()
						.map(element -> element.getText()).collect(Collectors.toList());
				List<String> sortedProducts = productNames.stream().sorted().collect(Collectors.toList());
				assertEquals(productNames, sortedProducts);
			}
		}

	}

	@Test
	public void testGetThePriceOfProduct() {
		driver.findElement(By.cssSelector("a[href *= 'offers']")).click();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		while (it.hasNext()) {
			String window = it.next();
			driver.switchTo().window(window);
			if (driver.getCurrentUrl().equals("https://rahulshettyacademy.com/seleniumPractise/#/offers")) {
				break;
			}
		}
		WebElement table = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/table/tbody"));
		table.findElement(By.xpath("//tr/th[1]")).click();
		List<String> productPrices = table.findElements(By.xpath("//tr/td[1]")).stream()
				.filter(element -> element.getText().equals("Beans")).map(StreamsTest::getVeggiePrice)
				.collect(Collectors.toList());
		System.out.println(productPrices);

	}

	@Test
	public void testGetThePriceOfProductPaginated() {
		driver.findElement(By.cssSelector("a[href *= 'offers']")).click();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		while (it.hasNext()) {
			String window = it.next();
			driver.switchTo().window(window);
			if (driver.getCurrentUrl().equals("https://rahulshettyacademy.com/seleniumPractise/#/offers")) {
				break;
			}
		}
		WebElement table = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/table/tbody"));
		table.findElement(By.xpath("//tr/th[1]")).click();
		String desiredProduct = "Mango";
		List<String> productPrices = getProductPrices(table, desiredProduct);
		int currentPage = 1;
		while (productPrices.isEmpty()) {
			driver.findElement(By.cssSelector("a[aria-label = 'Next']")).click();
			productPrices = getProductPrices(table, desiredProduct);
			currentPage++;
		}
		System.out.println("Price was found in page " + currentPage);
		System.out.println(productPrices);
	}

	@Test
	public void testSearchFeature() {
		driver.findElement(By.cssSelector("a[href *= 'offers']")).click();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		while (it.hasNext()) {
			String window = it.next();
			driver.switchTo().window(window);
			if (driver.getCurrentUrl().equals("https://rahulshettyacademy.com/seleniumPractise/#/offers")) {
				break;
			}
		}
		String query = "ma";
		driver.findElement(By.id("search-field")).sendKeys(query);

		WebElement table = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/table/tbody"));
		List<String> rows = table.findElements(By.xpath("//tr/td[1]")).stream().map(element -> element.getText())
				.collect(Collectors.toList());
		List<String> names = rows.stream().filter(element -> element.toLowerCase().contains(query.toLowerCase()))
				.collect(Collectors.toList());
		assertEquals(rows, names, "Results mismatch");
		System.out.println(names);
	}

	public static List<String> getProductPrices(WebElement table, String productName) {
		return table.findElements(By.xpath("//tr/td[1]")).stream()
				.filter(element -> element.getText().equals(productName)).map(StreamsTest::getVeggiePrice)
				.collect(Collectors.toList());
	}

	public static String getVeggiePrice(WebElement veggie) {
		return veggie.findElement(By.xpath("following-sibling::td[1]")).getText();
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
