package com.main.selenium.practices;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.compress.harmony.pack200.NewAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v106.emulation.Emulation;
import org.openqa.selenium.devtools.v106.fetch.Fetch;
import org.openqa.selenium.devtools.v106.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v106.network.Network;
import org.openqa.selenium.devtools.v106.network.model.ConnectionType;
import org.openqa.selenium.devtools.v106.network.model.ErrorReason;
import org.openqa.selenium.devtools.v106.network.model.ResourceType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class CDPTests {

	ChromeDriver driver;
	DevTools devTools;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@Test
	public void testDeviceMetrics() throws InterruptedException {
		devTools.send(Emulation.setDeviceMetricsOverride(400, 542, 50, true, Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty()));
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		Thread.sleep(3000);
	}

	@Test
	public void testCustomComand() throws InterruptedException {
		Map<String, Object> params = new HashMap<>();
		params.put("width", 400);
		params.put("height", 542);
		params.put("deviceScaleFactor", 50);
		params.put("mobile", true);
		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", params);
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		Thread.sleep(3000);
	}

	@Test
	public void testGeolocation() throws InterruptedException {
		devTools.send(Emulation.setGeolocationOverride(Optional.of(48), Optional.of(15), Optional.of(1)));
		driver.get("http://google.com");
		driver.findElement(By.name("q")).sendKeys(Keys.chord("netflix", Keys.ENTER));
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("a[href *= 'netflix.com']")).click();
		Thread.sleep(3000);
	}

	@Test
	public void testNetworkResponses() throws InterruptedException {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.responseReceived(), (response) -> {
			System.out.println(response.getResponse().getUrl() + " - " + response.getResponse().getStatus());
		});
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink $= 'library']"));
	}

	@Test
	public void testNetworkMock() throws InterruptedException {
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		devTools.addListener(Fetch.requestPaused(), request -> {
			String url = request.getRequest().getUrl();

			if (url.contains("shetty")) {
				url = url.replace("=shetty", "=BadGuy");
			}
			devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(url),
					Optional.of(request.getRequest().getMethod()), Optional.empty(), Optional.empty(),
					Optional.empty()));
		});
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink $= 'library']")).click();
		String message = driver.findElement(By.tagName("p")).getText();
		assertEquals(message, "Oops only 1 Book available");
	}

	@Test
	public void testNetworkFailed() throws InterruptedException {
		List<RequestPattern> patterns = Arrays
				.asList(new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty()));
		devTools.send(Fetch.enable(Optional.of(patterns), Optional.empty()));
		devTools.addListener(Fetch.requestPaused(), request -> {
			devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
		});
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink $= 'library']")).click();
		Thread.sleep(3000);
	}

	@Test
	public void testBlockRequest() throws InterruptedException {
		ImmutableList<String> urls = ImmutableList.of("*.png", "*.jpg");
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.setBlockedURLs(urls));
		long start = System.currentTimeMillis();
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		new Actions(driver).moveToElement(driver.findElement(By.id("traveller-home"))).perform();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	@Test
	public void testNetworkSpeedEmulation() throws InterruptedException {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.emulateNetworkConditions(false, 3000, 2000000, 100000, Optional.of(ConnectionType.WIFI)));
		long start = System.currentTimeMillis();
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	@Test
	public void testAuth() throws InterruptedException {
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("httpbin.org");
		((HasAuthentication) driver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));
		driver.get("https://httpbin.org/basic-auth/foo/bar");
		System.out.println(driver.findElement(By.tagName("pre")).getText());
	}

	@Test
	public void testJSErrors() throws InterruptedException {
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("a[routerlink $= 'products']")).click();
		driver.findElement(By.xpath("//button[text() = 'Enable/Disable Buying']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(By.cssSelector(".list-group-item a")).click();
		driver.findElement(By.xpath("(//div[@class = 'action']/child::button)[1]")).click();
		driver.findElement(By.xpath("(//div[@class = 'action']/child::button)[2]")).click();
		driver.findElement(By.cssSelector("a[routerlink $= 'cart']")).click();
		driver.findElement(By.id("exampleInputEmail1")).clear();
		driver.findElement(By.id("exampleInputEmail1")).sendKeys("2");
		driver.manage().logs().get(LogType.BROWSER).getAll().forEach(log -> {
			System.out.println(log.getMessage());
		});
		Thread.sleep(3000);
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
