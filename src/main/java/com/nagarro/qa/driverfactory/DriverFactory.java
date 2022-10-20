package com.nagarro.qa.driverfactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.nagarro.qa.utils.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	public static Properties prop;
	private WebDriver driver;
	public static ExtentReports extentreport;
	public static ExtentSparkReporter report;
	public static String browser;
	//public static final ThreadLocal<ExtentTest> threadtest = new ThreadLocal<>();
	
	public static final ThreadLocal<WebDriver> threadlocal = new ThreadLocal<>();
	
	
   /*
	 * public DriverFactory(WebDriver driver) { this.driver = driver; }
	 */
	 
	/*
	 * public WebDriver getDriver(String browser) {
	 * 
	 * if(threadlocal.get()==null) { driver = init_driver(browser); } return driver;
	 * }
	 */
	
	public  WebDriver init_driver(String browser)
	{
		if(threadlocal.get()==null)
		{
		System.out.println("Thread info..."+Thread.currentThread().getId());
		switch(browser)
		{
		case "Chrome":
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver();
			threadlocal.set(new ChromeDriver());
			break;
			
		case "Firefox":
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver();
			threadlocal.set(new FirefoxDriver());
			break;
		case "Edge":
			WebDriverManager.edgedriver().setup();
			threadlocal.set(new EdgeDriver());
			//driver = new EdgeDriver();
			break;
	    default :
	    	System.out.println("Please pass the correct browser");
	   
		}
		
		threadlocal.get().manage().window().maximize();
		
		threadlocal.get().manage().deleteAllCookies();
		threadlocal.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constants.PAGE_LOAD_TIMEOUT));
		threadlocal.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.DEFAULT_TIMEOUT));
		threadlocal.get().get(prop.getProperty("url"));
		}	
		return threadlocal.get();
		
	}
	
	
	
	
	
	
	public Properties init_prop()
	{
		prop = new Properties();
		try {
			FileInputStream fileIO = new FileInputStream("./src/main/java/com/nagarro/qa/config/config.properties");
			prop.load(fileIO);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return prop;
	}

	
	
	public String captureScreenshot()
	{
		TakesScreenshot ts = (TakesScreenshot)threadlocal.get();
		File src = ts.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/Screenshots/" +System.currentTimeMillis()+".png";
		try {
			FileUtils.copyFile(src, new File(path));
		} catch (IOException e) {		
			e.printStackTrace();
		}
		return path;
	}
	
	@BeforeSuite
 	public ExtentReports ExtentReportSetup()
 	{  Path path = Paths.get("./Results");
	   if(!Files.exists(path))
	   {
		   try {
			   Files.createDirectories(path);
		   }
		   catch(Exception e)
		   {
			e.printStackTrace();   
		   }
	   }
	   extentreport = new ExtentReports();
	   report = new ExtentSparkReporter(System.getProperty("user.dir")+"/Results/ExtentReport"+System.currentTimeMillis()+".png");
	   extentreport.attachReporter(report);
	   extentreport.setSystemInfo("Author", "Riyasree");
	  
	   return extentreport;
	  }
}
