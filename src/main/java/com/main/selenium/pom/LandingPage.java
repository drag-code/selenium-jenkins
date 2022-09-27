package com.main.selenium.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.main.selenium.common.Page;

public class LandingPage extends Page{

	@FindBy(id = "userEmail")
	private WebElement emailInput;
	@FindBy(id = "userPassword")
	private WebElement passwordInput;
	@FindBy(id = "login")
	private WebElement loginButton;
	@FindBy(className = "forgot-password-link")
	private WebElement forgotPasswordLink;
	@FindBy(className = "text-reset")
	private WebElement registerLink;
	@FindBy(xpath = "/html/body/app-root/app-login/div[2]/section[1]/div[1] //h1")
	private WebElement successfullOrdersCard;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void open(String URL) {
		driver.get(URL);
	}
	
	public int getSuccessfullOrdersAmount() {
		executeScriptOn("arguments[0].scrollIntoView(true);", successfullOrdersCard);
		waitFor(3000);
		return Integer.parseInt(successfullOrdersCard.getText());
	}
}
