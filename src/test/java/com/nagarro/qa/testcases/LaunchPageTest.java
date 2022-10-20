package com.nagarro.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.nagarro.qa.base.BaseTest;
import com.nagarro.qa.pages.AboutPage;
import com.nagarro.qa.pages.LaunchPage;
import com.nagarro.qa.utils.Constants;
import com.nagarro.qa.utils.ElementUtils;

public class LaunchPageTest extends BaseTest{
		

	 
	@BeforeMethod
	public void pre_requisite()
	{		
		launch = new LaunchPage();
		aboutus = new AboutPage();
		
	}
	
	
	@Test(priority=1 , description="Nagarro Launch Page Validation")	
	public void launchNagarroPageValidation()
	{
		String pageTitle = launch.launchNagarroWebsiteValidation();
		Assert.assertEquals(pageTitle, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Test(priority=2 , description="Nagarro Sap Services label Validation")
	public void pageLableValidation()
	{
		Assert.assertTrue(launch.verifyNagarroLable().contains(Constants.LOGIN_PAGE_TITLE));
	}
	
	
	
	@Test(priority=3, description="Click on about us button ")

	public void aboutUsButtonClick() throws InterruptedException
	{		
		aboutus= launch.clickAboutUsBtn();
		
	}
   
	@Test(priority=4 , description="Nagarro About US Page Validation")
	public void NagarroAboutUsPageValidation()
	{
		Assert.assertTrue(aboutus.verifyNagarroAboutUsPage());
	}
	
	

}
