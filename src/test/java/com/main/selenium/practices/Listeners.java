package com.main.selenium.practices;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {
	String screnshotsPath = "C:\\Users\\cacha\\Pictures\\Selenium";
	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("SUCCESS");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("FAILURE");
	}
}
