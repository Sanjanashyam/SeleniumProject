package com.selenium.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.qa.base.Base;
import com.selenium.qa.pages.AccountPage;
import com.selenium.qa.pages.HomePage;
import com.selenium.qa.pages.LoginPage;
import com.selenium.qa.utils.Utils;

public class Login extends Base {
	
	public Login() {
		super();
	}
	
	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		driver = initializeBrowserAndOpenAppUrl(prop.getProperty("browserName"));
		
		HomePage homepage = new HomePage(driver);
		homepage.clickOnMyAccount();
//		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		
		homepage.selectLoginOption();
//		driver.findElement(By.linkText("Login")).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1, dataProvider="validLoginCredentials")
	public void verifyLoginWithValidCredentials(String email, String password) {

		LoginPage loginpage = new LoginPage(driver);
		loginpage.enterEmail(email);
//		driver.findElement(By.id("input-email")).sendKeys(email);
		
		loginpage.enterPassword(password);
//		driver.findElement(By.id("input-password")).sendKeys(password);
		
		loginpage.clickOnLoginButton();
//		driver.findElement(By.xpath("//input[@value='Login']")).click();

		AccountPage accountpage = new AccountPage(driver);
		Assert.assertTrue(accountpage.displayStatusOfEditYourAcctInfoOption(),
				"Edit your account information is NOT displayed");
//		Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).isDisplayed(),
//				"Edit your account information is NOT displayed");

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
