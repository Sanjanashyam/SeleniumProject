package com.selenium.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) { // Constructor
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Objects
	@FindBy(xpath = "//span[text()='My Account']")
	private WebElement myAccountDropMenu;

	@FindBy(linkText = "Login")
	private WebElement LoginOption;

	// Actions
	public void clickOnMyAccount() {
		myAccountDropMenu.click();
	}

	public void selectLoginOption() {
		LoginOption.click();
	}

}
