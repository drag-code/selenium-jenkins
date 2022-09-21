package com.main.selenium.practices;

import org.testng.annotations.Test;

public class TestGroups {

	
	@Test(groups = {"Smoke"})
	public void WebLoginHome() {
		System.out.println("WebLoginHome");
	}

	@Test(groups = {"Smoke"})
	public void WebLoginCar() {
		System.out.println("WebLogincar");
	}
	
	@Test(groups = {"Smoke"})
	public void Login() {
		System.out.println("Login");
	}
	
	@Test(groups = {"Smoke"}, dependsOnMethods = {"Login"})
	public void Good() {
		System.out.println("good");
	}
}
