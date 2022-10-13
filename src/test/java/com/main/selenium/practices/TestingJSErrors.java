package com.main.selenium.practices;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestingJSErrors implements ITestListener {
	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
	}
}
