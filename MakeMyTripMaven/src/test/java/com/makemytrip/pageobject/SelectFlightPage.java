package com.makemytrip.pageobject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Popup;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class SelectFlightPage {

	WebDriver driver;
	WebDriverWait dwait;
	FluentWait<WebDriver> Wait;
	Actions oactions;
	JavascriptExecutor jse;

	int temp = 0;
	By PopUp = By.xpath("//button[text()='OKAY, GOT IT!']");
	By Source = By.id("fromCity");
	By Destination = By.id("toCity");
	By FlightList = By.xpath("//div[contains(@id,'flight_list_item')]");
	By Pricetab = By.xpath("//span[text()='Price']");
	By LowestPrice = By.xpath("(//div[@class='priceSection']//p)[1]");
	By BookBtn = By.xpath("//button[text()='Book Now']");
	int FlightCount;
	String PriceText;
	int i;

	public SelectFlightPage(WebDriver driver) {
		this.driver = driver;
		this.dwait = new WebDriverWait(driver, 35);
		this.Wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(35))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(ElementClickInterceptedException.class)
				.ignoring(NoSuchElementException.class);
		this.oactions = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;
	}

	public void popUp() {
		try {
			if (driver.findElement(PopUp).isDisplayed()) {
				Wait.until(ExpectedConditions.visibilityOfElementLocated(PopUp)).click();
			} else {
				oactions.moveToElement(driver.findElement(Source)).build().perform();
			}
			Reporter.log("<br>Successfully closed the Popup");
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to close the Popup");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void verifyTheElements(String source, String destination) {
		try {

			String sourceValue = dwait.until(ExpectedConditions.visibilityOfElementLocated(Source))
					.getAttribute("value");
			Assert.assertEquals(sourceValue, source);
			String destValue = dwait.until(ExpectedConditions.visibilityOfElementLocated(Destination))
					.getAttribute("value");
			Assert.assertEquals(destValue, destination);
			System.out.println(source);
			System.out.println(destination);
			Reporter.log("<br>TestPassed::Successfully verified the values");
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to asssert the values;Execution stopped");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void verifyFlights() {
		try {
			driver.findElement(By.xpath("//span[text()='Arrival']")).click();

			jse.executeScript("window.scrollBy(0,document.body.scrollHeight)");

			FlightCount = driver.findElements(By.xpath("//div[@class='listingCard']//span[text()='View Prices']"))
					.size();
			System.out.println(FlightCount);

			int FlightArray[][] = new int[FlightCount][2];

			for (int i = 1; i <= FlightCount; i++) {
				WebElement Price = driver.findElement(By.xpath("(//div[@class='priceSection']//p)[" + i + "]"));
				PriceText = Price.getText().replaceAll("[₹,]", "").trim();

				System.out.println("Price of the Flight " + i + "= " + PriceText);

				// Array of flight price
				FlightArray[i - 1][0] = Integer.parseInt(PriceText);

				// Array for flight index
				FlightArray[i - 1][1] = i;
			}

			for (int i = 1; i < FlightArray.length; i++) {
				for (int j = i; j > 0; j--) {
					if (FlightArray[j][0] < FlightArray[j - 1][0]) {
						temp = FlightArray[j][0];
						FlightArray[j][0] = FlightArray[j - 1][0];
						FlightArray[j - 1][0] = temp;
						temp = FlightArray[j][1];
						FlightArray[j][1] = FlightArray[j - 1][1];
						FlightArray[j - 1][1] = temp;
					}
				}
			}

			for (int i = 0; i < FlightCount; i++) {
				String s = Integer.toString(FlightArray[i][0]) + '-' + Integer.toString(FlightArray[i][1]);
				System.out.println(s);

			}
			Reporter.log("<br>Test Pass:: Successfully sorted the Flight Price");
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to sort the Price in Ascending order");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickOnFlight() {
		try {

			jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(Pricetab));
			jse.executeScript("window.scrollBy(0,-500)");
			Thread.sleep(3000);
			dwait.until(ExpectedConditions.visibilityOfElementLocated(Pricetab)).click();
			String arrowdirection = driver.findElement(By.xpath("//span[text()='Price']/parent::span/parent::div"))
					.getAttribute("class");
			System.out.println(arrowdirection);
			if (arrowdirection.contains("up"))
				jse.executeScript("arguments[0].scrollIntoView(true);", PriceText.substring(temp));

			oactions.moveToElement(
					driver.findElement(By.xpath("//div[@class='listingCard']//span[text()='View Prices'][1]"))).click()
					.build().perform();
			jse.executeScript("window.scrollBy(0,250)");
			oactions.moveToElement(driver.findElement(BookBtn)).click().build().perform();

			Reporter.log("<br>Test Pass:: Lowest Price Flight Clicked successfully");
		} catch (Exception e) {
			Reporter.log("<br>Fail::Failed to click on the Lowest Priced Flight");
			Reporter.log("Exception::" + e.getMessage());
			e.printStackTrace();
		}

	}
}
