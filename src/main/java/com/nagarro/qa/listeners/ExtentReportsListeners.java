package com.nagarro.qa.listeners;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.nagarro.qa.base.BaseTest;

public class ExtentReportsListeners extends BaseTest implements ITestListener{
	
	public static final ThreadLocal<ExtentTest> threadtest = new ThreadLocal<>();
	
   private static ExtentTest extenttest;
	
	 
  
	@Override
	public synchronized void onTestStart(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " started..");
		int index = threadlocal.get().toString().lastIndexOf(":");
		String drivername = threadlocal.get().toString().substring(0, index);
		extenttest = extentreport.createTest(result.getMethod().getDescription()+" "+drivername);
		extenttest.assignCategory(result.getTestContext().getSuite().getName());
		threadtest.set(extenttest);		
		threadtest.get().getModel().setStartTime(getTime(result.getStartMillis()));
		
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		
		System.out.println(result.getMethod().getMethodName() + " Passed..");
		/*
		 * extentreport.createTest("Screenshot for Passed method "+result.getMethod().
		 * getMethodName())
		 * .pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).
		 * build()); threadtest.set(extenttest);
		 */
		threadtest.get().pass("Test Passed");
		threadtest.get().pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
		
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		
		System.out.println(result.getMethod().getMethodName() + " Failed!!");	
		/*
		 * extentreport.createTest("Screenshot for failed method "+result.getMethod().
		 * getMethodName())
		 * .fail(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).
		 * build()); threadtest.set(extenttest);
		 * 
		 */	
		threadtest.get().fail("Test Failed on "+threadlocal.get());
		threadtest.get().fail(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
		
	}
	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println(result.getMethod().getMethodName()+" is skipped...");
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public synchronized void onTestFailedWithTimeout(ITestResult result) {		
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public synchronized  void onStart(ITestContext context) {
		System.out.println("Test Suite Started.." +context.getName());
		
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println("Test Suite is finished...");
		extentreport.flush();
		
		
		
	}
	
	private Date getTime(long millis)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		return cal.getTime();
				
	}

}
