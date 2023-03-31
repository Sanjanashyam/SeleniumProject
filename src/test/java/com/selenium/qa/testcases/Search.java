package com.selenium.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.selenium.qa.base.Base;

public class Search extends Base {

public Search() {
		super();
	}

	WebDriver driver;

	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenAppUrl(prop.getProperty("browserName"));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void verifySearchWithValidProduct() {
		driver.findElement(By.name("search")).sendKeys(dataProp.getProperty("vaildProduct"));
		driver.findElement(By.xpath("//div[@id='search']/descendant::button")).click(); // Path

		Assert.assertTrue(driver.findElement(By.linkText("HP LP3065")).isDisplayed(),
				"Valid product is not displayed in the search");
	}

	@Test(priority = 2)
	public void verifySearchWithInvalidProduct() {
		driver.findElement(By.name("search")).sendKeys(dataProp.getProperty("invalidProduct"));
		driver.findElement(By.xpath("//div[@id='search']/descendant::button")).click();
		String actualSearchMsg = driver.findElement(By.xpath("//div[@id='content']/h2/following-sibling::p")).getText(); // Path

		Assert.assertEquals(actualSearchMsg, dataProp.getProperty("invalidProductSearchResultMessage"),
				"Message for No product matches the search criteria is NOT displayed");
	}

	@Test(priority = 3)
	public void verifySearchWithoutAnyProduct() {
		driver.findElement(By.name("search")).sendKeys("");
		driver.findElement(By.xpath("//div[@id='search']/descendant::button")).click(); // Path
		String actualSearchMsg = driver.findElement(By.xpath("//div[@id='content']/h2/following-sibling::p")).getText();

		Assert.assertEquals(actualSearchMsg, dataProp.getProperty("invalidProductSearchResultMessage"),
				"Message for No product matches the search criteria is NOT displayed");
	}
}
