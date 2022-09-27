package com.main.selenium.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	public static ExtentReports getReporter() {
		ExtentSparkReporter reporterConfig = new ExtentSparkReporter(
				System.getProperty("user.dir") + "\\reports\\index.html");
		reporterConfig.config().setReportName("Test Extent Report");
		reporterConfig.config().setDocumentTitle("Test Results");

		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(reporterConfig);
		extentReports.setSystemInfo("Tester", "Esteban Alexis Arce Gómez");
		extentReports.setSystemInfo("Browser", "Chrome");
		return extentReports;
	}
}
