package com.main.selenium.practices;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.main.selenium.test_components.BaseTest;

import static org.testng.Assert.*;

public class LinksCountTest {
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
	public void testLinksCount() {
		int linksCount = driver.findElements(By.tagName("a")).size();
		System.out.println(linksCount + " links found");
		assertEquals(linksCount, 27);
	}

	@Test
	public void testLinksCountInFooter() {
		int linksCount = driver.findElements(By.xpath("//*[@id = 'gf-BIG'] //a")).size();
		System.out.println(linksCount);
		assertEquals(linksCount, 20);
	}

	@Test
	public void testLinksCountInFooterFirstColumn() {
		int linksCount = driver.findElements(By.xpath("//*[@id=\"gf-BIG\"]/table/tbody/tr/td[1] //a")).size();
		System.out.println(linksCount);
		assertEquals(linksCount, 5);
	}

	@Test
	public void testLinksAreWorkingInFooterFirstColumn() {
		List<WebElement> links = driver.findElements(By.xpath("//*[@id=\"gf-BIG\"]/table/tbody/tr/td[1] //a"));
		int linksCount = links.size();
		Actions actions = new Actions(driver);

		for (int i = 1; i < linksCount; i++) {
			actions.moveToElement(links.get(i)).keyDown(Keys.LEFT_CONTROL).click().perform();
			// ALTERNATIVE WAY
			// String openLinkInAnotherTabCommand = Keys.chord(Keys.CONTROL, Keys.ENTER);
			// links.get(i).sendKeys(openLinkInAnotherTabCommand)
		}

		Set<String> tabs = driver.getWindowHandles();
		String currentTab = driver.getWindowHandle();
		Iterator<String> tabsIterator = tabs.iterator();

		while (tabsIterator.hasNext()) {
			String nextTab = tabsIterator.next();
			if (!nextTab.equals(currentTab)) {
				driver.switchTo().window(nextTab);
				String title = driver.getTitle();
				System.out.println(title);
				assertNotNull(title);
			}
		}
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
