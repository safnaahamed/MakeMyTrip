package com.makemytrip.pageobject;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class PromoDealValidationPassengerPage {

	WebDriver driver;
	WebDriverWait dwait;
	FluentWait<WebDriver> Wait;
	Actions oactions;
	JavascriptExecutor jse;

	By PromocodeHeader = By.xpath("//div[@class='promoHeader']/span");
	By PromoCode = By.xpath("//span[contains(text(),'FLYFESTIVE')]");
	By EnterPromoCode = By.xpath("//input[@placeholder='Enter promo code here']");
	By ApplyBtn = By.xpath("//span[text()='APPLY']");
	By AppliedPromo = By.xpath("//span[contains(@class,'customRadioBtn')]/parent::span/parent::div");
	By PromocodeText = By.xpath("(//*[text()='Remove']//parent::span/preceding-sibling::span)[3]");
	By Discounts = By.xpath("//span[contains(text(),'Discounts')]/parent::div/following-sibling::span");
	By SecureTrip = By.xpath("//*[text()='Yes, Secure my trip. ']/parent::span/preceding-sibling::span/span");
	By Adult1FN = By.xpath("//input[@placeholder='First & Middle Name']");
	By Adult1LN = By.xpath("//input[@placeholder='Last Name']");
	By Gender1 = By.xpath("//span[contains(text(),'MALE')]/parent::label");
	By ContinueBtnFrame = By.xpath("//button[text()='Continue']/parent::div/parent::form");
	By ContinueBtn = By.xpath("//button[text()='Continue']");
	By AddnewAdult = By.xpath("//button[contains(text(),' ADD NEW ADULT')]");
	By Adult2FN = By.xpath("(//input[@placeholder='First & Middle Name'])[2]");
	By Adult2LN = By.xpath("(//input[@placeholder='Last Name'])[2]");
	By Gender2 = By.xpath("(//span[contains(text(),'FEMALE')])[2]");
	By Mobile = By.xpath("//input[@placeholder='Mobile No']");
	By Email = By.xpath("//input[@placeholder='Email']");
	By ConfirmBtn = By.xpath("//a[text()='CONFIRM']");
	By PromoconfirmBtn = By.xpath("//button[text()='CONFIRM']");
	By YesPleaseBtn = By.xpath("//button[text()='Yes, Please']");
	By ContinueAnyway = By.xpath("//button[text()='CONTINUE ANYWAY']");
	By SliderBtn = By.xpath("//button[@class='sliderNextBtn']");
	By PayBtn = By.xpath("//button[text()='Proceed to pay']");

	public PromoDealValidationPassengerPage(WebDriver driver) {
		this.driver = driver;
		this.dwait = new WebDriverWait(driver, 35);
		this.Wait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(2))
				.withTimeout(Duration.ofSeconds(35)).ignoring(ElementClickInterceptedException.class)
				.ignoring(NoSuchElementException.class);
		this.oactions = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;
	}

	public void applyDeals(String firstName1, String lastname1, String firstName2, String phone, String email,
			String cardNo, String cardName, String cardMonth, String cardYear, String cvv, String country, String state,
			String billingAddress, String city, String pincode) {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		try {
			dwait.until(ExpectedConditions.visibilityOfElementLocated(PromocodeHeader)).isDisplayed();
			promoDeals();
			promoDealsPassengerInfo(firstName1, lastname1, firstName2, phone, email);
			clickOnContinueBtn();
			PaymentPage opay = new PaymentPage(driver);
			opay.paymentInfo(cardNo, cardName, cardMonth, cardYear, cvv, country, state, billingAddress, city, pincode);

		} catch (Exception e) {
			HotDealsPromoPage opromo = new HotDealsPromoPage(driver);
			opromo.hotDeals();
			opromo.clickOnContinue();

			PassengerDetails odetails = new PassengerDetails(driver);
			odetails.hotDealsPassengerDetails(firstName1, lastname1, firstName2, phone, email);
			odetails.clickOnContinue();
			PaymentPage opay = new PaymentPage(driver);
			opay.paymentInfo(cardNo, cardName, cardMonth, cardYear, cvv, country, state, billingAddress, city, pincode);
		}
	}

	public void promoDeals() {

		try {

			Wait.until(ExpectedConditions.visibilityOfElementLocated(PromocodeHeader));
			// to find the text
			String PromoText = Wait.until(ExpectedConditions.visibilityOfElementLocated(PromoCode)).getText();
			Wait.until(ExpectedConditions.elementToBeClickable(EnterPromoCode)).sendKeys(PromoText);
			Wait.until(ExpectedConditions.elementToBeClickable(ApplyBtn)).click();
			Thread.sleep(2000);
			// checking with the checked radio button
			String Promocheck = Wait.until(ExpectedConditions.visibilityOfElementLocated(AppliedPromo))
					.getAttribute("class");

			if (Promocheck.contains("checked")) {
				System.out.println("Promocode " + PromoText + " is applied successfully");

				String PromoValue = Wait.until(ExpectedConditions.visibilityOfElementLocated(PromocodeText)).getText();
				String[] temp = PromoValue.split("\\.");
				String[] couponAmt = temp[1].trim().split(" ");
				System.out.println("amount is " + couponAmt[0]);
				Thread.sleep(2000);
				String discountValue = Wait.until(ExpectedConditions.visibilityOfElementLocated(Discounts)).getText();
				String discamount[] = discountValue.split(" ");
				if (couponAmt[0].equalsIgnoreCase(discamount[2])) {
					System.out.println("Coupon " + discamount[2] + "is applied successfully");
				} else {
					System.out.println("Failed to apply the coupon");
				}
//
//				String text = "Congratulations! Promo Discount of Rs. 700 applied successfully.";
//				//split till the dot
//				String[]temp =text.split("\\.");
//				String[] temp1 = temp[1].trim().split("");
//				System.out.println(temp1);
//				
			} else {
				System.out.println("Promocode " + PromoText + " selection is failed");
			}

			Reporter.log("<br>Test Pass:: Suucessfully applied the coupon " + PromoText);

		} catch (Exception e) {
			Reporter.log("<br>Test Fail:: Failed to apply the coupon ");
			Reporter.log("<br>Exception:: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void promoDealsPassengerInfo(String firstName1, String lastname1, String firstName2, String phone,
			String email) {

		try {

			jse.executeScript("window.scrollBy(0,500)");
			dwait.until(ExpectedConditions.visibilityOfElementLocated(AddnewAdult)).click();
			Wait.until(ExpectedConditions.elementToBeClickable(Adult1FN)).sendKeys(firstName1);
			Wait.until(ExpectedConditions.elementToBeClickable(Adult1LN)).sendKeys(lastname1);
			oactions.moveToElement(driver.findElement(Gender1)).click().build().perform();
			jse.executeScript("window.scrollBy(0,300)");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(AddnewAdult)).click();
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

	public void clickOnContinueBtn() {

		try {
			try {
				oactions.sendKeys(Keys.PAGE_UP).build().perform();
				dwait.until(ExpectedConditions.visibilityOfElementLocated(SecureTrip));

				oactions.moveToElement(driver.findElement(SecureTrip)).click().build().perform();
				oactions.click(driver.findElement(SecureTrip));

				String SecureTripCheck = Wait.until(ExpectedConditions.visibilityOfElementLocated(SecureTrip))
						.getAttribute("class");
				Thread.sleep(2000);
				if (SecureTripCheck.contains("checked")) {
					System.out.println("Radio button clicked successfully");
				} else {
					System.out.println("Failed to click on the radio button");
				}
				WebElement Continue = Wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn));
				oactions.sendKeys(Continue, Keys.ENTER).build().perform();
				dwait.until(ExpectedConditions.visibilityOfElementLocated(PromoconfirmBtn)).click();
				jse.executeScript("window.scrollBy(0,600)");
				Wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//p[text()='Would you like these free seats ? ']")));
				oactions.sendKeys(driver.findElement(YesPleaseBtn), Keys.ENTER).build().perform();
				Wait.until(ExpectedConditions.visibilityOfElementLocated(ContinueBtn)).click();
				Wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//p[text()='Would you like these free seats ? ']")));
				oactions.sendKeys(driver.findElement(YesPleaseBtn), Keys.ENTER).build().perform();
				dwait.until(ExpectedConditions.visibilityOfElementLocated(ContinueBtn)).click();
				Wait.until(ExpectedConditions.visibilityOfElementLocated(PayBtn));
				Wait.until(ExpectedConditions.elementToBeClickable(PayBtn)).click();
				

			} catch (Exception e) {

			}
			WebElement Continue = Wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn));
			oactions.sendKeys(Continue, Keys.ENTER).click().build().perform();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(PromoconfirmBtn)).click();
			jse.executeScript("window.scrollBy(0,-600)");
			Wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//p[text()='Would you like these free seats ? ']")));
			oactions.sendKeys(driver.findElement(YesPleaseBtn), Keys.ENTER).build().perform();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(ContinueBtn)).click();
			Wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//p[text()='Would you like these free seats ? ']")));
			oactions.sendKeys(driver.findElement(YesPleaseBtn), Keys.ENTER).build().perform();
			dwait.until(ExpectedConditions.visibilityOfElementLocated(ContinueBtn)).click();
			try {
				Wait.until(ExpectedConditions.elementToBeClickable(ContinueAnyway)).click();
				oactions.sendKeys(Keys.PAGE_DOWN).build().perform();
				Wait.until(ExpectedConditions.visibilityOfElementLocated(PayBtn));
				Wait.until(ExpectedConditions.elementToBeClickable(PayBtn)).click();
			} catch (Exception e) {
				oactions.sendKeys(Keys.PAGE_DOWN).build().perform();
				dwait.until(ExpectedConditions.visibilityOfElementLocated(PayBtn));
				dwait.until(ExpectedConditions.elementToBeClickable(PayBtn)).click();
			}
			Reporter.log("<br>Successfully clicked and executed the steps");
		} catch (Exception e) {
			Reporter.log("<br> Failed to execute the steps to proceed to payment page");
			Reporter.log("<br>Exception::" + e.getMessage());
			e.printStackTrace();
		}
	}

}
