package com.makemytrip.pageobject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.utils.CreateExtentReports;



public class SearchFlightPage {
	WebDriver driver;
	FluentWait<WebDriver> Wait;
	WebDriverWait dwait;
	DateFormat df;
	Actions oactions;
	ExtentSparkReporter sparkReporter;
	ExtentReports extentreport;
	ExtentTest extentTest;
	
	By PopUpMenu = By.xpath("//p[contains(text(),'Login/Signup for Best Prices')]");
	By LanguagePopUp = By.xpath("//span[@class='langCardClose']");
	By FlightBtn = By.xpath("//li[@class='menu_Flights']/a");
	By PopUp = By.xpath("//li[@data-cy='account']"); // ul//li[contains(@class,'userLoggedOut')]
	By Source = By.id("fromCity"); // ul[@role='listbox']//div[contains(text(),'COK')]
	By Destination = By.xpath("//label[@for='toCity']");
	By Departure = By.xpath("//span[text()='DEPARTURE']");
	By calendar = By.xpath("//div[@class='DayPicker-wrapper']");
	By Return = By.xpath("//span[text()='RETURN']");
	By Passenger = By.xpath("//span[contains(text(),'Travellers & CLASS')]");
	By TravelClass = By.xpath("//li[contains(text(),'Business')]");
	By AdultCount = By.xpath("//ul[contains(@class,'guestCounter')]/li[contains(@data-cy,'2')]");
	By ApplyBtn = By.xpath("//button[contains(text(),'APPLY')]");
	By SearchBtn = By.xpath("//a[text()='Search']/parent::p");

	public SearchFlightPage(WebDriver driver) {
		this.driver = driver;
		this.Wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(35))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(ElementClickInterceptedException.class)
				.ignoring(ElementNotInteractableException.class).ignoring(NoSuchElementException.class);
		this.dwait = new WebDriverWait(driver, 35);
		this.oactions = new Actions(driver);
		
		
	}

	public void clickOnPopUp() {
		try {
			dwait.until(ExpectedConditions.visibilityOfElementLocated(PopUpMenu));
			dwait.until(ExpectedConditions.elementToBeClickable(PopUp)).click();

			Wait.until(ExpectedConditions.elementToBeClickable(LanguagePopUp)).click();
			
			Reporter.log("<br>Pass::Successfully clicked on the Create or Login button");
//			extentTest.pass("Pass::Clicked on the PopUp Successfully");			
		} catch (Exception e) {

			Reporter.log("<br>Fail::Failed to click on the Create or Login button");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to click on the PopUp");
		}
	}

	public void enterFromLocation(String source) {
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(Source)).click();
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='From']")))
					.sendKeys(source);
			Thread.sleep(2000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//ul[@role='listbox']/li/div//p[contains(text(),'" + source + "')]"))).click();
			Reporter.log("<br>Pass::Successfully entered the location:" + Source);
//			extentTest.pass("Pass::entered the Search Location Successfully"+source);	
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to enter the location:" + Source);
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to enter the correct Source Location");
		}
	}

	public void enterDestination(String destination) {
		try {

			WebElement ToInput = dwait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='To']")));
			oactions.sendKeys(ToInput, destination).build().perform();
			
			WebElement DestSuggest = Wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + destination + "']"))); // ul[@role='listbox']/li/div//div[contains(text(),'BLR')]
			Thread.sleep(2000);
			Wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//ul[@role='listbox']/li/div/div[contains(text(),'"+destination+"')]")))
					.click();

			Reporter.log("<br>Pass::Successfully entered the destination:" + destination);
//			extentTest.pass("Pass::entered the Search Location Successfully"+destination);	
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to enter the destination:" + destination);
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to enter the correct Destination Location"+destination);
		}
	}

	public void enterStartDate() {
		try {
			df = new SimpleDateFormat("dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 5);
			String date = df.format(cal.getTime());

			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'" + date + "')]")))
					.click();
			Reporter.log("<br>Pass::Successfully entered the date:" + date);
//			extentTest.pass("Pass::Successfully selected the fifth date from the current date"+date);
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to click on the date");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to enter the date");
		}
	}

	public void enterReturnDate() {
		try {
			dwait.until(ExpectedConditions.visibilityOfElementLocated(Return)).click();

			df = new SimpleDateFormat("dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,7);
			String date = df.format(cal.getTime());
			dwait.until(ExpectedConditions.visibilityOfElementLocated(calendar)).click();
			driver.findElement(By.xpath("//p[contains(text(),'" + date + "')]")).click();
			Reporter.log("<br>Pass::Successfully entered the date:" + date);
//			extentTest.pass("Pass::Successfully selected the seventh date from the current date"+date);
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to click on the date:");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to enter the date");
		}
	}

	public void enterTravelAndClass() {
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(Passenger)).click();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(TravelClass)).click();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(AdultCount)).click();
			driver.findElement(ApplyBtn).click();
			Reporter.log("<br>Pass::Successfully clicked on the class:" + TravelClass);
			Reporter.log("<br>Pass::Successfully selected the Adult number:" + AdultCount);
//			extentTest.pass("Pass::Successfully entered the Passenger details"+ TravelClass + ":" + AdultCount);
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to click on the expected:" + TravelClass + ":" + AdultCount);
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to enter the passenger details");
		}
	}

	public void selectFareType() {
		try {
			for (int i = 1; i <=4; i++) {
				ArrayList<WebElement> FareType = new ArrayList<>(
						Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='specialFareNew']//li"))));
				oactions.clickAndHold(FareType.get(i)).build().perform();
				WebElement ToolTip = Wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("(//div[contains(@class,'specialFareTooltip')]//p[2])[" + i + "]")));
				String ToolTipText = ToolTip.getText();
				System.out.println("ToolTipText=" + ToolTipText);
			}
			driver.findElement(By.xpath("//ul[@class='specialFareNew']//li")).click();
			Reporter.log("<br>Test passed::Successfully clicked and printed the Tooltip texts");
//			extentTest.pass("Pass::Was able to print out the tooltiptexts for all the fare types");
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to print the ToolTipTexts");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to print the tooltiptexts");
		}

	}

	public void clickOnSearchBtn() {
		try {
			Wait.until(ExpectedConditions.elementToBeClickable(SearchBtn)).click();
			Reporter.log("<br>Test passed::Successfully clicked on the Search Button");
//			extentTest.pass("Pass::Successfully clicked on the Search button");
		
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to print the ToolTipTexts");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
//			extentTest.fail("Fail::Failed to click on the Search button");
		}

	}
}
