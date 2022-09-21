package com.main.selenium.practices;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestParams {

	
	@Parameters({"URL"})
	@Test(groups = {"Smoke"})
	public void WebLoginHome(String url) {
		System.out.println("WebLoginHome");
		System.out.println(url);
	}

	@Test(groups = {"Smoke"})
	public void WebLoginCar() {
		System.out.println("WebLogincar");
	}
	
	@Test(groups = {"Smoke"})
	public void Login() {
		System.out.println("Login");
	}
	
	@Test(groups = {"Smoke"})
	public void Good() {
		System.out.println("good");
	}
}
