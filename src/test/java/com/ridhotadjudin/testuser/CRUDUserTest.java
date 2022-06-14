package com.ridhotadjudin.testuser;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CRUDUserTest {

	WebDriver driver;
	JavascriptExecutor javascriptExe;

	public String screenShoot() {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String waktu = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String namaFile = "D:\\selenium-workspace\\TestNG_User\\hasilScreenshoot\\" + waktu + ".png";
		File screenshoot = new File(namaFile);
		try {
			FileUtils.copyFile(srcFile, screenshoot);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return namaFile;
	}

	@BeforeClass
	public void init() {
		System.setProperty("url", "http://localhost/cicool");
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		driver = new ChromeDriver();
		javascriptExe = (JavascriptExecutor) driver;
		driver.get(System.getProperty("url"));
		driver.manage().window().maximize();
	}

	@Test(priority = 0)
	public void login() {
		driver.findElement(By.cssSelector("i.fa.fa-sign-in")).click();
		driver.findElement(By.cssSelector("input[placeholder='Email']")).clear();
		driver.findElement(By.cssSelector("input[placeholder='Email']")).sendKeys("ridhotadjudin@gmail.com");
		driver.findElement(By.cssSelector("input[placeholder='Password']")).clear();
		driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys("123456");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		String username = driver.findElement(By.cssSelector("span[class='hidden-xs']")).getText();
		AssertJUnit.assertEquals(username, "Ridho");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1, dataProvider = "getUserData", dataProviderClass = com.ridhotadjudin.dataprovider.DataProviderUser.class)
	public void cobaDulu(String param1, String param2, String param3, String param4) {
		driver.get("http://localhost/cicool/administrator/crud");
		driver.findElement(By.cssSelector("tbody tr:nth-child(2) td:nth-child(5) a:nth-child(1)")).click();
		driver.findElement(By.cssSelector("#btn_add_new")).click();

		driver.findElement(By.xpath("//input[@placeholder='First Name']")).clear();
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys(param1);

		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).clear();
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(param2);

		driver.findElement(By.xpath("//input[@placeholder='Email']")).clear();
		driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(param3);

		driver.findElement(By.xpath("//input[@placeholder='Gender']")).clear();
		driver.findElement(By.xpath("//input[@placeholder='Gender']")).sendKeys(param4);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement upGambar = driver.findElement(By.cssSelector("input[title='file input']"));

		Random r = new Random();
		int result = r.nextInt(4 - 1) + 1;
		switch (result) {
		case 1:
			upGambar.sendKeys("D:\\selenium-workspace\\TestNG_User\\src\\test\\resources\\buayapalu.jpg");
			break;
		case 2:
			upGambar.sendKeys("D:\\selenium-workspace\\TestNG_User\\src\\test\\resources\\islan.jpg");
			break;
		case 3:
			upGambar.sendKeys("D:\\selenium-workspace\\TestNG_User\\src\\test\\resources\\nexsoft.jpg");
			break;
		default:
			upGambar.sendKeys("D:\\selenium-workspace\\TestNG_User\\src\\test\\resources\\nexsoft.jpg");
			break;
		}

		javascriptExe.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.cssSelector("#btn_save")).click();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.get("http://localhost/cicool/administrator/userbiodata");

		String file = "<img src='file://" + screenShoot() + "'/>";
		Reporter.log(file);

	}
}
