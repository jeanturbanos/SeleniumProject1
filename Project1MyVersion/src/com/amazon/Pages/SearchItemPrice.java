package com.amazon.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchItemPrice 
{
	@FindBy(id="twotabsearchtextbox")
	private WebElement product;
	
	@FindBy(id="nav-search-submit-button")
	private WebElement clickButton;
	
	
	@FindBy(xpath="//span[contains(@class,'a-price')][1]")
	private WebElement price;
	
	@FindBy(xpath="//span[contains(@class,'a-size-medium a-color-base')][1]")
	private WebElement message;
	
	
	public SearchItemPrice(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
	}
	
	
	public void findProduct(String id) 
	{
		
		product.sendKeys(id);
	}
	
	public void searchButton()
	{
		clickButton.click();
	}
	
	
	public String getPrice()
	{
		String itemPrice=price.getText();
		return itemPrice;
	}
	
	
	public boolean errMessage()
	{
		boolean errorMsg = message.isDisplayed();
		return errorMsg;
	}

	public void clearSearch()
	{
		product.clear();
	}
}
