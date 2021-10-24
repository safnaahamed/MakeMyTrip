package com.utils;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class CreateExtentReports {
	ExtentSparkReporter sparkReporter;
	ExtentReports extentreport;
	ExtentTest extentTest;
	
	public void extentSparkReporter() {
		this.sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"//reports//extentReport.html");
		  sparkReporter.config().setEncoding("utf-8");
		  sparkReporter.config().setDocumentTitle("MakeMyTrip Execution Report");
		  sparkReporter.config().setReportName("Automation Execution Report");
		  sparkReporter.config().setTheme(Theme.STANDARD);
		
	}
	public void extentReports() {
		this.extentreport=new ExtentReports();
		  extentreport.attachReporter(sparkReporter);
		  extentreport.setSystemInfo("Author","Safna");
		  extentreport.setSystemInfo("OS", "Windows 11");
		  extentreport.setSystemInfo("Browser", "Chrome");
		  extentreport.setSystemInfo("Testing Website", "MakeMyTrip");
	  }
		
	
	public void extentTest() {
		 this.extentTest=extentreport.createTest("TC_01_FlightBookingValidation");
	}
	
}
