package com.selenium.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//Objects
	@FindBy(id="input-email")
	private WebElement inputEmail;
	
	@FindBy (id="input-password")
	private WebElement inputPassword;
	
	@FindBy (xpath="//input[@value='Login']")
	private WebElement LoginButton;
	
	
	//Actions
	public void enterEmail(String email) {
		inputEmail.sendKeys(email);
	}
	
	public void enterPassword(String password) {
		inputPassword.sendKeys(password);
	}
	
	public void clickOnLoginButton() {
		LoginButton.click();
	}

}
