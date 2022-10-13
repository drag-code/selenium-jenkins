package com.main.selenium.pom;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.main.selenium.common.Page;

public class EcommerceLandingPage extends Page{

	@FindBy(className = "products")
	private List<WebElement> products;
	@FindBy(className = "cart-item")
	private List<WebElement> productsInCart;
	@FindBy(css = "input[type = 'search']")
	private WebElement searchBox;
	@FindBy(className = "search-button")
	private WebElement searchButton;
	@FindBy(linkText = "Top Deals")
	private WebElement topDealsLink;
	@FindBy(linkText = "Flight Booking")
	private WebElement flightBookingLink;
	@FindBy(className = "cart-info")
	private WebElement cartInfoSection;
	@FindBy(className = "cart-icon")
	private WebElement cartButton;
	@FindBy(linkText = "Free Access to InterviewQues/ResumeAssistance/Material")
	private WebElement materialAccessLink;
	
	public EcommerceLandingPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void open(String URL) {
		driver.get(URL);
	}
	
	public void addProductToCart(String name) {
		Optional<WebElement> desiredProduct = products
				.stream()
				.filter(product -> product.findElement(By.className("product-name")).getText().contains(name))
				.findFirst();
		desiredProduct.ifPresent(product -> {
			product.findElement(By.tagName("button")).click();
			waitForTextToAppearInElement("✔ ADDED", product.findElement(By.tagName("button")));
		});
	}
	
	public void clickCart() {
		cartButton.click();
	}
	
	public int getCartItemsCount() {
		
		return 0;
	}
	
	public boolean productIsPresentInCart(String name) {
		return productsInCart
				.stream()
				.anyMatch(product -> product.findElement(By.className("product-name")).getText().contains(name));
	}
	
}
