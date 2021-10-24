package com.makemytrip.pageobject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class PassengerDetails {
	WebDriver driver;
	WebDriverWait dwait;
	FluentWait<WebDriver> Wait;
	Actions oactions;
	JavascriptExecutor jse;

	By Adult1FN = By.xpath("//input[@placeholder='First & Middle Name']");
	By Adult1LN = By.xpath("//input[@placeholder='Last Name']");
	By Gender1 = By.xpath("//span[contains(text(),'MALE')]/parent::label");
	By AddAdult = By.xpath("//a[contains(text(),'+ ADD ADULT')]");
	By Adult2FN = By.xpath("(//input[@placeholder='First & Middle Name'])[2]");
	By Adult2LN = By.xpath("(//input[@placeholder='Last Name'])[2]");
	By Gender2 = By.xpath("(//span[contains(text(),'FEMALE')])[2]");
	By Mobile = By.xpath("//input[@placeholder='Mobile No']");
	By Email = By.xpath("//input[@placeholder='Email']");
	By ConfirmBtn = By.xpath("//a[text()='CONFIRM']");
	By confirmSeats = By.xpath("//a[text()='CONFIRM SEATS']");
	By confirmSeats2 = By.xpath("//a[text()='CONFIRM SEAT(S)']");
	By nextBtn = By.xpath("//a[text()='Next']");
	By ContinueBtnFrame = By.xpath("//button[text()='Continue']/parent::div/parent::form");
	By ContinueBtn = By.xpath("//button[text()='Continue']");

	public PassengerDetails(WebDriver driver) {
		this.driver = driver;
		this.dwait = new WebDriverWait(driver, 35);
		this.Wait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(2))
				.withTimeout(Duration.ofSeconds(35)).ignoring(ElementClickInterceptedException.class)
				.ignoring(NoSuchElementException.class);
		this.oactions = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;
	}

	public void hotDealsPassengerDetails(String firstName1, String lastname1, String firstName2, String phone,
			String email) {

		try {
			Wait.until(ExpectedConditions.elementToBeClickable(Adult1FN)).sendKeys(firstName1);
			Wait.until(ExpectedConditions.elementToBeClickable(Adult1LN)).sendKeys(lastname1);
			oactions.moveToElement(driver.findElement(Gender1)).click().build().perform();
			jse.executeScript("window.scrollBy(0,400)");

			Wait.until(ExpectedConditions.visibilityOfElementLocated(AddAdult)).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(Adult2FN)).sendKeys(firstName2);
			Wait.until(ExpectedConditions.elementToBeClickable(Adult2LN)).sendKeys(lastname1);
			oactions.moveToElement(driver.findElement(Gender2)).click().build().perform();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(Mobile)).sendKeys(phone);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(Email)).sendKeys(email);
			Reporter.log("<br>Successfully entered the Passenger Details");

		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to enter the Passenger Details");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickOnContinue() {
		try {
			oactions.sendKeys(Keys.PAGE_DOWN).click().build().perform();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Acknowledgement']")));
			oactions.sendKeys(driver.findElement(ContinueBtn), Keys.ENTER).build().perform();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(ConfirmBtn)).click();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(confirmSeats2)).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(nextBtn)).click();
			Thread.sleep(2000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(confirmSeats2)).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(nextBtn)).click();

			Reporter.log("<br>Successfully clicked and executed the steps");
		} catch (Exception e) {
			Reporter.log("<br> Failed to execute the steps to proceed to payment page");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
		}
	}

}
