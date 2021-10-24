package com.makemytrip.pageobject;

import java.time.Duration;
import java.util.ArrayList;

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

public class HotDealsPromoPage {
	WebDriver driver;
	WebDriverWait dwait;
	FluentWait<WebDriver> Wait;
	Actions oactions;
	JavascriptExecutor jse;

	By HotDealenterPromoCode = By.xpath("//input[@placeholder='Enter Coupon Code']");
	By PromoCode = By.xpath("//span[contains(text(),'FLYFESTIVE')]");
	By ApplyBtn = By.xpath("//span[text()='APPLY']");
	By HotDealDiscounts = By.xpath("//span[contains(text(),'Discounts')]/parent::p/following-sibling::span");
	By HotdealHeader = By.xpath("//span[contains(text(),'DEALS')]/parent::p");
	By DealRadioBtn = By.xpath("//span[@class='coupon-text']/ancestor::label/input");
	By PromoText2 = By.xpath("//span[@class='coupon-text']/parent::p/following-sibling::p//*");
	By ContinueBtnFrame = By.xpath("//button[text()='Continue']/parent::div/parent::form");
	By ContinueBtn = By.xpath("//button[text()='Continue']/parent::div");
	By HotDealContinueBtn = By.xpath("//button[text()='Continue']");
	By HotDealSecureTrip = By.xpath("//span[contains(text(),'Yes, secure my trip')]/parent::div");

	public HotDealsPromoPage(WebDriver driver) {

		this.driver = driver;
		this.dwait = new WebDriverWait(driver, 35);
		this.Wait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(2))
				.withTimeout(Duration.ofSeconds(35)).ignoring(ElementClickInterceptedException.class)
				.ignoring(NoSuchElementException.class);
		this.oactions = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;

	}

	public void hotDeals() {

		try {

			jse.executeScript("window.scrollBy(0,400)");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(HotdealHeader));
			String Hotdealtext = Wait.until(ExpectedConditions.elementToBeClickable(PromoCode)).getText();
			jse.executeScript("window.scrollBy(0,400)");
			Thread.sleep(2000);
			Wait.until(ExpectedConditions.elementToBeClickable(HotDealenterPromoCode)).sendKeys(Hotdealtext);
			Wait.until(ExpectedConditions.elementToBeClickable(ApplyBtn)).click();
			Thread.sleep(2500);

			if (driver.findElement(DealRadioBtn).isSelected()) {
				System.out.println("Coupon" + Hotdealtext + " is selected");

				String dealtext = Wait.until(ExpectedConditions.visibilityOfElementLocated(PromoText2)).getText();
				String[] temp = dealtext.split("\\.");
				String[] couponAmt1 = temp[1].trim().split(" ");
				System.out.println("amount is " + couponAmt1[0]);
				Thread.sleep(2000);
				String discountValue1 = Wait.until(ExpectedConditions.visibilityOfElementLocated(HotDealDiscounts))
						.getText();
				String[] discamount1 = discountValue1.split(" ");

				if (couponAmt1[0].equalsIgnoreCase(discamount1[2])) {
					System.out.println("Coupon code " + discamount1[2] + " is applied successfully");
				} else {
					System.out.println("Faied to apply the coupon");
				}

			} else {
				System.out.println("Coupon failed to select");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void clickOnContinue() {
		try {
			jse.executeScript("window.scrollBy(0,350)");
			Wait.until(ExpectedConditions.elementToBeClickable(HotDealSecureTrip));
			Wait.until(ExpectedConditions.elementToBeClickable(HotDealSecureTrip)).click();
			oactions.sendKeys(Keys.PAGE_DOWN).build().perform();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(HotDealContinueBtn)).click();

		} catch (Exception e) {

			oactions.sendKeys(Keys.PAGE_DOWN).build().perform();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(HotDealContinueBtn)).click();
		}
	}

}
