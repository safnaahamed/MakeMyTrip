package com.makemytrip.pageobject;


import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class PaymentPage {

	WebDriver driver;
	WebDriverWait dwait;
	FluentWait<WebDriver> Wait;
	Actions oactions;
	JavascriptExecutor jse;
	ArrayList<String> tabs;

	By AddAdult = By.xpath("//a[contains(text(),'+ ADD ADULT')]");

	By CardNumber = By.id("cardNumber");
	By CardName = By.id("nameOnCard");
	By cmonth = By.xpath("//p[contains(text(),'Month')]");
	By cyear = By.xpath("//p[contains(text(),'Year')]");
	By CVV = By.id("cardCvv");
	By Country = By.xpath("//div[text()='Select Your Country']");
	By State = By.id("billingState");
	By BillingAddress = By.id("billingAddress");
	By City = By.id("billingCity");
	By Pincode = By.id("billingPinCode");
	By PromoconfirmBtn = By.xpath("//button[text()='CONFIRM']");
	By YesPleaseBtn = By.xpath("//button[text()='Yes, Please']");
	By SliderBtn = By.xpath("//button[@class='sliderNextBtn']");
	By PayBtn = By.xpath("//button[text()='Proceed to pay']");

	public PaymentPage(WebDriver driver) {

		this.driver = driver;
		this.dwait = new WebDriverWait(driver, 35);
		this.Wait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(2))
				.withTimeout(Duration.ofSeconds(35)).ignoring(ElementClickInterceptedException.class)
				.ignoring(NoSuchElementException.class);
		this.oactions = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;
		this.tabs = new ArrayList<>(driver.getWindowHandles());
	}

	public void paymentInfo(String cardNo, String cardName, String cardMonth, String cardYear, String cvv,
			String country, String state, String billingAddress, String city, String pincode) {
		try {
			dwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Payment options']")));
			dwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Credit/Debit/ATM Card']")));
			Wait.until(ExpectedConditions.elementToBeClickable(CardNumber)).click();
			oactions.sendKeys(driver.findElement(CardNumber), cardNo).click().build().perform();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(CardName)).sendKeys(cardName);
			Wait.until(ExpectedConditions.elementToBeClickable(cmonth)).click();
			Wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//ul//li[contains(text(),'" + cardMonth + "')]")))
					.click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(cyear)).click();
			Wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//ul//li[contains(text(),'" + cardYear + "')]")))
					.click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(CVV)).sendKeys(cvv);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(Country)).click();
			oactions.moveToElement(driver.findElement(By.xpath("//div[text()='" + country + "']"))).click().build()
					.perform();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(State)).sendKeys(state);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(BillingAddress)).sendKeys(billingAddress);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(City)).sendKeys(city);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(Pincode)).sendKeys(pincode);
			driver.switchTo().window(tabs.get(0));

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
