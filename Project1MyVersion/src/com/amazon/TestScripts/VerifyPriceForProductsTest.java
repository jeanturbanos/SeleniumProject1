package com.amazon.TestScripts;

import java.io.IOException;
import org.testng.annotations.Test;

import com.amazon.Pages.SearchItemPrice;

import utils.GenericMethod;

public class VerifyPriceForProductsTest extends Base
{
	//class name changed form VerifyPriceForProducts to VerifyPriceForProductsTest

	@Test
	public void verifyProductPrice() throws IOException 
	{
		SearchItemPrice sip = new SearchItemPrice(driver);
		GenericMethod mtd = new GenericMethod();
		String filePath = "D:\\QATesting\\EcommTestData.xlsx";
		String sheetName = "Test";
		String[][] data = GenericMethod.getData(filePath, sheetName);
		for(int i = 1; i<data.length;i++) 
		{
			String productID = data[i][0];
//			driver.findElement(By.id("global-search-input")).sendKeys(productID);
//			driver.findElement(By.cssSelector("#global-search-submit > span > img")).click();
			sip.findProduct(productID);
			sip.searchButton();
			
			String price = "";
			boolean errMsg = false;
			
			try {
				//price = driver.findElement(By.xpath("//span[contains(@class,'price price-main')][1]")).getText();
				price = sip.getPrice();
				String[] splitPrice = price.split("\n");
				price = splitPrice[0];
				System.out.println("Price is "+price);
			}
			catch(Exception ex) 
			{
				//errMsg = driver.findElement(By.cssSelector("#mainSearchContent > span > strong")).isDisplayed();
				errMsg = sip.errMessage();
			}
			
			mtd.writeToExcel(filePath,sheetName, 0, 2, "ActualPrice");
			mtd.writeToExcel(filePath,sheetName, 0, 3, "Status");
			if(price != null) {
				mtd.writeToExcel(filePath, sheetName, i, 2, price);
			}
			
			if(errMsg){
				mtd.writeToExcel(filePath,sheetName, i, 2,"Invalid Product ID");
				mtd.writeToExcel(filePath, sheetName, i, 3,"NA");
			}
			
			else if(price.equals(data[i][1])) {
				mtd.writeToExcel(filePath, sheetName, i, 3,"PASS");
			}
			else {
				mtd.writeToExcel(filePath, sheetName, i, 3,"FAIL");
			}
			
			//driver.findElement(By.id("global-search-input")).clear();
			sip.clearSearch();
			
		}

	}
	
	
}
