package com.makemytrip.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.makemytrip.pageobject.PaymentPage;
import com.makemytrip.pageobject.PromoDealValidationPassengerPage;
import com.makemytrip.pageobject.SearchFlightPage;
import com.makemytrip.pageobject.SelectFlightPage;
import com.utils.CreateExtentReports;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class TC_01_FlightBookingValidation  {
	WebDriver driver;
	FluentWait<WebDriver> Wait;
	WebDriverWait dwait;
	ExtentSparkReporter sparkReporter;
	ExtentReports extentreport;
	ExtentTest extentTest;
	CreateExtentReports oreports;
	
	@Parameters({ "source", "destination", "firstName1", "lastname1", "firstName2", "phone", "email", "cardNo",
			"cardName", "cardMonth", "cardYear", "cvv", "country", "state", "billingAddress", "city", "pincode" })
	@Test

	public void flightBooking(String source, String destination, String firstName1, String lastname1, String firstName2,
			String phone, String email, String cardNo, String cardName, String cardMonth, String cardYear, String cvv,
			String country, String state, String billingAddress, String city, String pincode) {
		
				
		driver.get("https://www.makemytrip.com/");
		SearchFlightPage osearch = new SearchFlightPage(driver);
		osearch.clickOnPopUp();
		osearch.enterFromLocation(source);		
		osearch.enterDestination(destination);
		osearch.enterStartDate();
		// osearch.enterReturnDate();
		osearch.enterTravelAndClass();
		osearch.selectFareType();
		osearch.clickOnSearchBtn();
		SelectFlightPage opage = new SelectFlightPage(driver);
		// opage.popUp();
		// opage.verifyTheElements(source, destination);
		opage.verifyFlights();
		opage.clickOnFlight();
		PromoDealValidationPassengerPage opromo = new PromoDealValidationPassengerPage(driver);
		opromo.applyDeals(firstName1, lastname1, firstName2, phone, email, cardNo, cardName, cardMonth, cardYear, cvv,
				country, state, billingAddress, city, pincode);

	}

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver_win32\\chromedriver.exe");
		this.driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterTest
	public void afterTest() {
		
		driver.quit();
	}

}
