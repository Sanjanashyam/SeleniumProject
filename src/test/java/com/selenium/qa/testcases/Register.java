package com.selenium.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.selenium.qa.base.Base;
import com.selenium.qa.utils.Utils;

public class Register extends Base {
	
	public Register() {
		super();
	}

	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		driver = initializeBrowserAndOpenAppUrl(prop.getProperty("browserName"));
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void verifyRegisterAccountWithMandatoryFields() {

		driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(dataProp.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(Utils.generateEmailWithTimeStamp());
		driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.id("input-confirm")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.xpath("//input[@name='agree']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualSuccessHeading = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		Assert.assertEquals(actualSuccessHeading, dataProp.getProperty("accountCreationMessage"), "Account is not created");
	}

	@Test(priority = 2)
	public void verifyRegisterAccountWithAllFields() {

		driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(dataProp.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(Utils.generateEmailWithTimeStamp());
		driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.id("input-confirm")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click(); // Note path
		driver.findElement(By.xpath("//input[@name='agree']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualSuccessHeading = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		Assert.assertEquals(actualSuccessHeading, dataProp.getProperty("accountCreationMessage"), "Account is not created");
	}

	@Test(priority = 3)
	public void verifyRegisterAccountWithExistingEmailAddress() {

		driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(dataProp.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("validEmail"));
		driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.id("input-confirm")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.xpath("//input[@name='agree']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualExistingEmailWarning = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]"))
				.getText();
		Assert.assertTrue(actualExistingEmailWarning.contains(dataProp.getProperty("duplicateEmailWarning")),
				"Existing Email address warning is not displayed");

	}

	@Test(priority = 4)
	public void verifyRegisterAccountWithNoDetails() {

		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualPrivacyPolicyWarning = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]"))
				.getText();
		Assert.assertTrue(actualPrivacyPolicyWarning.contains(dataProp.getProperty("privacyPolicyAgreementWarning")),
				"Privacy Policy Agreement warning is not displayed");

		String actualFirstNameWarning = driver
				.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).getText(); // Note Path
		Assert.assertEquals(actualFirstNameWarning, dataProp.getProperty("invalidfirstNameWarning"),
				"First Name warning is NOT displayed");

		String actualLastNameWarning = driver
				.findElement(By.xpath("//input[@id='input-lastname']/following-sibling::div")).getText();
		Assert.assertEquals(actualLastNameWarning, dataProp.getProperty("invalidlastNameWarning"),
				"Last Name warning is NOT displayed");

		String actualEmailAddressWarning = driver
				.findElement(By.xpath("//input[@id='input-email']/following-sibling::div")).getText();
		Assert.assertEquals(actualEmailAddressWarning, dataProp.getProperty("invalidemailWarning"),
				"Email Address warning is NOT displayed");

		String actualTelephoneWarning = driver
				.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText();
		Assert.assertEquals(actualTelephoneWarning, dataProp.getProperty("invalidTelephoneWarning"),
				"Telephone warning is NOT displayed");

		String actualPasswordWarning = driver
				.findElement(By.xpath("//input[@id='input-password']/following-sibling::div")).getText();
		Assert.assertEquals(actualPasswordWarning,dataProp.getProperty("invalidPasswordWarning"),
				"Password warning is NOT displayed");

	}
}
