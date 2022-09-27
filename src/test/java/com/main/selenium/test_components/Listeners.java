package com.main.selenium.test_components;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.main.selenium.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentReports extentReports = ExtentReporterNG.getReporter();
	ExtentTest test;
	ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		test = extentReports.createTest(result.getMethod().getMethodName());
		threadLocal.set(test); // set unique thread id for each test
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		threadLocal.get().log(Status.PASS, "PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		threadLocal.get().fail(result.getThrowable());
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			System.out.println(e.getMessage());
		}
		threadLocal.get().addScreenCaptureFromPath(getScreenShot(result.getMethod().getMethodName(), driver),
				result.getMethod().getMethodName());
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();
	}
}
