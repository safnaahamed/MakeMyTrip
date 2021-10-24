package com.makemytrip.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.ToolTipManager;
import javax.tools.Tool;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class testingcodesformakemytrip {

	public static void main(String[] args) throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		SoftAssert overify = new SoftAssert();
		Thread.sleep(3000);
//		WebDriverWait wait = new WebDriverWait(driver,45);
//		Actions oactions = new Actions(driver);
//		oactions.moveToElement(driver.findElement(By.xpath("//li[contains(@class,'userLoggedOut')]//p[contains(text(),'Login or Create Account')]"))).build().perform();
		driver.findElement(By.xpath("//ul//li[contains(@class,'userLoggedOut')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("fromCity")).click();
		driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("Kochi");
		driver.findElement(By.xpath("//ul[@role='listbox']//div[contains(text(),'COK')]")).click();
		String Location = "COK, Cochin International Airport Limited India";
		Assert.assertEquals(driver.findElement(By.xpath("//label[@for='fromCity']/p")).getText(), Location);
		System.out.println(Location);
		// driver.findElement(By.xpath("//label[@for='toCity']")).click();
		Thread.sleep(3000);
		// driver.findElement(By.xpath("//input[@placeholder='To']")).click();
		driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys("Bangalore");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ul[@role='listbox']//div[contains(text(),'BLR')]")).click();
		driver.findElement(By.xpath("//span[text()='DEPARTURE']")).click();

		DateFormat df = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String date = df.format(cal.getTime());
		driver.findElement(By.xpath("//div[@class='DayPicker']")).click();
		driver.findElement(By.xpath("//p[contains(text(),'" + date + "')]")).click();
//		driver.findElement(By.xpath("//span[text()='RETURN']")).click();
//		driver.findElement(By.xpath("//div[@class='DayPicker-wrapper']")).click();
//		driver.findElement(By.xpath("//p[contains(text(),'" + date + "')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Travellers & CLASS')]")).click();
		driver.findElement(By.xpath("//li[contains(text(),'Business')]")).click();
		driver.findElement(By.xpath("//ul[contains(@class,'guestCounter')]/li[contains(@data-cy,'2')]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'APPLY')]")).click();
		Actions oactions = new Actions(driver);
		//oactions.clickAndHold(driver.findElement(By.xpath("//ul[@class='specialFareNew']"))).build().perform();
		
		for(int i=1; i<=4; i++) {
		ArrayList<WebElement> FareType = new ArrayList<>(driver.findElements(By.xpath("//ul[@class='specialFareNew']//li")));	
		oactions.clickAndHold(FareType.get(i)).build().perform();
//		
		WebElement ToolTip = driver.findElement(By.xpath("(//div[contains(@class,'specialFareTooltip')]//p[2])["+i+"]"));
		//oactions.moveToElement(ToolTip).build().perform();
		String ToolTipText = ToolTip.getText();
		System.out.println("ToolTipText"+i+"=" + ToolTipText);
//		oactions.click();
//		oactions.release(ToolTip);
//		oactions.perform();	
		}
		
		driver.findElement(By.xpath("//ul[@class='specialFareNew']//li")).click();
		Thread.sleep(4000);
		oactions.moveToElement(driver.findElement(By.xpath("//a[text()='Search']"))).build().perform();
		driver.findElement(By.xpath("//a[text()='Search']")).click();
		String var1 =  "Kochi, India";
		Assert.assertEquals(driver.findElement(By.id("fromCity")).getAttribute("value"), var1);
		System.out.println(var1);
		String var2 = "Bengaluru, India";
		Assert.assertEquals(driver.findElement(By.id("toCity")).getAttribute("value"),var2);
		System.out.println(var2);
		//driver.findElement(By.xpath("//span[text()='Arrival']")).click();
		Thread.sleep(3000);
		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		//jse.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		ArrayList<WebElement>Flights = new ArrayList<>(driver.findElements(By.xpath("//div[contains(@id,'flight_list_item')]")));
		JavascriptExecutor jse1 = (JavascriptExecutor) driver;
		jse1.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		int FlightCount = Flights.size();
		int FlightArray[][] = new int[15][2];
		System.out.println(FlightCount);
		for(int i=1; i<=15;i++) {
			WebElement Price = driver.findElement(By.xpath("(//div[@class='priceSection']//p)["+i+"]"));			
			String PriceText = Price.getText().replaceAll("[â‚¹,]", "").trim();
			System.out.println("Price of the Flight"+i+"="+PriceText);
			FlightArray [i-1][0] =  Integer.parseInt(PriceText);	
			FlightArray [i-1][1] =  i;
		}
		int temp = 0;
		for (int i = 1; i < FlightArray.length; i++) {
            for (int j = i; j > 0; j--) {
                if (FlightArray[j][0] < FlightArray [j - 1][0]) {
                    temp = FlightArray[j][0];
                    FlightArray[j][0] = FlightArray[j - 1][0];
                    FlightArray[j - 1][0] = temp;
                    temp = FlightArray[j][1];
                    FlightArray[j][1] = FlightArray[j - 1][1];
                    FlightArray[j - 1][1] = temp;
                }
            }

        }
		for(int i=0; i<15;i++) {
			String s= Integer.toString(FlightArray[i][0]) + '-' + Integer.toString(FlightArray[i][1]);
			System.out.println(s);
			
		}
		
		oactions.moveToElement(driver.findElement(By.xpath("(//div[@class='priceSection']//p)["+FlightCount+"]"))).build().perform();	
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,250)");
		oactions.moveToElement(driver.findElement(By.xpath("//div[@class='listingCard']//span[text()='View Prices'][1]"))).click().build().perform();
		jse.executeScript("window.scrollBy(0,250)");
		//oactions.moveToElement(driver.findElement(BookBtn)).click().build().perform();
		//oactions.moveToElement(driver.findElement(By.xpath("priceSection["+s+"]"))).click();
		//JavascriptExecutor jse1 = (JavascriptExecutor) driver;
		//jse1.executeScript("arguments[0].scrollIntoView(true);", FlightArray[1][1]);
//		Thread.sleep(3000);
//		 driver.findElement(By.xpath("//span[text()='View Prices']//parent::button/preceding-sibling::div/p["+FlightArray[1][0]+"]")).click();
//		String FlightClick = String.valueOf(FlightArray[0]) ;
//		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='View Prices']//parent::button/preceding-sibling::div/p")).getText().replaceAll("[â‚¹,]","").trim(), FlightClick);
//		System.out.println(FlightClick);
//		WebElement clickOnFlightBtn = driver.findElement(By.xpath("//span[text()='View Prices']//parent::button/preceding-sibling::div/p["+FlightCount+"]"));
//		
//		oactions.moveToElement(clickOnFlightBtn).build().perform();
//		
//		Thread.sleep(3000);
//
//		driver.findElement(By.xpath("//button[text()='Book Now']")).click();
//		WebElement FlightList_Price =driver.findElement(By.xpath("//div[contains(@id,'flight_list_item')]//p[contains(text(),"+PriceText+")]"));
//		boolean LowestPrice = FlightList_Price.toString().equalsIgnoreCase(PriceText);
//		System.out.println(LowestPrice);
	
	}

}
