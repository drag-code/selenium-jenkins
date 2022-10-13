package com.main.selenium.common;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
	WebDriverWait wait;
	Actions actions;
	JavascriptExecutor jsExecutor;
	protected WebDriver driver;

	public Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		this.actions = new Actions(driver);
		this.jsExecutor = (JavascriptExecutor) driver;
	}

	protected void waitForElementToAppear(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected void waitForElementToAppear(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	protected void waitForTextToAppearInElement(String text, WebElement element) {
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	protected void waitForElementToDissapear(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	protected void waitForElementToDissapear(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	protected void waitForElementsToAppear(List<WebElement> elements) {
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	public void waitFor(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	protected Actions hoverOn(WebElement element) {
		return actions.moveToElement(element);
	}

	protected void executeScriptOn(String script, WebElement element) {
		jsExecutor.executeScript(script, element);
	}

	protected void perform() {
		actions.perform();
	}

	protected void setWaitDuration(int seconds) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
	}
}
