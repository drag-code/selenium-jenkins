package ecommerce_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.main.selenium.test_components.BaseTest;

public class Tests extends BaseTest{
	
	@Test
	public void productIsAddedToCartAndIsPresentWhenClickOnCartButton() {
		landingPage.addProductToCart("Brocolli");
		landingPage.clickCart();
		assertTrue(landingPage.productIsPresentInCart("Brocolli"));
		landingPage.waitFor(3000);
	}
}
