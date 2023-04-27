package com.selenium.qa.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.selenium.qa.utils.ExtentReporter;
import com.selenium.qa.utils.Utils;

public class MyListeners implements ITestListener {

	ExtentReports extentReport;
	ExtentTest extentTest;
	String testName;

	@Override
	public void onStart(ITestContext context) {
//		System.out.println("Test Execution started");
		extentReport = ExtentReporter.generateExtentReport();
	}

	@Override
	public void onTestStart(ITestResult result) {
		testName = result.getName();
		extentTest = extentReport.createTest(testName);
		extentTest.log(Status.INFO, testName + " test started");
//		System.out.println(testName + " test started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		testName = result.getName();
		extentTest.log(Status.PASS, testName + " is Passed");
//		System.out.println(testName + " is Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		testName = result.getName();

//		System.out.println("Screenshot taken");//To take the screenshot
		WebDriver driver = null; // ***Make sure to make WebDriver driver public in test classes

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());// To get the driver
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String ScreenshotDestinationPath = System.getProperty("user.dir") + "\\Screenshots" + testName + ".png";
		
		try {
			FileHandler.copy(srcScreenshot, new File(ScreenshotDestinationPath));
		} catch (IOException e) {
			e.printStackTrace();
		} */  //moved the screenshot code (captureScreenshot) to Utils files
		String ScreenshotDestinationPath = Utils.captureScreenshot(driver, testName);

		// To attach the screenshot to Extent report
		extentTest.addScreenCaptureFromPath(ScreenshotDestinationPath);

//		System.out.println(result.getThrowable());
		extentTest.log(Status.INFO, result.getThrowable());

//		System.out.println(testName + " is Failed");
		extentTest.log(Status.FAIL, testName + " is Failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		testName = result.getName();
//		System.out.println(result.getThrowable());
		extentTest.log(Status.INFO, result.getThrowable());
//		System.out.println(testName + " is Skipped");
		extentTest.log(Status.SKIP, testName + " is Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
//		System.out.println("Test Execution finished");
		extentReport.flush();
		
// To open the extent report automatically after running the tests
		String pathOfExtentReport = System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentReport.html";
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
