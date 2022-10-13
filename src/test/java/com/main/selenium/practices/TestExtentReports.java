package com.main.selenium.practices;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.main.selenium.test_components.BaseTest2;

public class TestExtentReports extends BaseTest2 {

	@Test()
	public void testSuccessfullOrders() {
		int successfullOrdersAmount = landingPage.getSuccessfullOrdersAmount();
		assertEquals(successfullOrdersAmount, 3546540);
	}

	@Test()
	public void testFailedOrders() {
		int successfullOrdersAmount = landingPage.getSuccessfullOrdersAmount();
		assertEquals(successfullOrdersAmount, 3546, "Values are not equal");
	}
}
