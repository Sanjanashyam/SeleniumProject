package com.selenium.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.qa.base.Base;
import com.selenium.qa.utils.Utils;

public class Login extends Base {
	
	public Login() {
		super();
	}
	
	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		driver = initializeBrowserAndOpenAppUrl(prop.getProperty("browserName"));
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Login")).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1, dataProvider="validLoginCredentials")
	public void verifyLoginWithValidCredentials(String email, String password) {

		driver.findElement(By.id("input-email")).sendKeys(email);
		driver.findElement(By.id("input-password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).isDisplayed(),
				"Edit your account information is NOT displayed");

	}
	
	@DataProvider (name="validLoginCredentials")
	public Object[][] supplyTestData() {
		Object[][] data = Utils.getTestDataFromExcel("Login");
		return data;
	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredential() {

		driver.findElement(By.id("input-email")).sendKeys(Utils.generateEmailWithTimeStamp());
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("invalidPassword"));
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		String actualWarningMsg = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText();
		String expectedWarningMsg = dataProp.getProperty("EmailPasswordNotMatchingWarning");

		Assert.assertEquals(actualWarningMsg, expectedWarningMsg,"Email/Password not matching warning is NOT displayed");

	}

	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailAndValidPwd() {

		driver.findElement(By.id("input-email")).sendKeys(Utils.generateEmailWithTimeStamp());
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		String actualWarningMsg = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText();
		String expectedWarningMsg = dataProp.getProperty("EmailPasswordNotMatchingWarning");

		Assert.assertEquals(actualWarningMsg, expectedWarningMsg, "Email/Password not matching warning is NOT displayed");

	}

	@Test(priority = 4)
	public void verifyLoginWithValidEmailAndInvalidPwd() {

		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("validEmail"));
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("invalidPassword"));
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		String actualWarningMsg = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText();
		String expectedWarningMsg = dataProp.getProperty("EmailPasswordNotMatchingWarning");

		Assert.assertEquals(actualWarningMsg, expectedWarningMsg, "Email/Password not matching warning is NOT displayed");

	}

	@Test(priority = 5)
	public void verifyLoginWithNoCredentials() {

		/* CAN BE REMOVED
		 * driver.findElement(By.id("input-email")).sendKeys("");
		 * driver.findElement(By.id("input-password")).sendKeys("");
		 */
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		String actualWarningMsg = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText();
		String expectedWarningMsg = "Warning: No match for E-Mail Address and/or Password.";

		Assert.assertEquals(actualWarningMsg, expectedWarningMsg, "Email/Password not matching warning is NOT displayed");

	}

}
