package com.selenium.qa.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListeners implements ITestListener {

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test Execution started");
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getName();
		System.out.println(testName + " test started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getName();
		System.out.println(testName + " is Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getName();
		System.out.println(testName + " is Failed");
		System.out.println(result.getThrowable());
		System.out.println("Screenshot taken");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getName();
		System.out.println(testName + " is Skipped");
		System.out.println(result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test Execution finished");
	}

}
