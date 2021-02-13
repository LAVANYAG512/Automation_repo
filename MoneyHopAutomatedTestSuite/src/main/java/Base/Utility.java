package Base;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Utility {

		public static WebDriver driver;	
		static Properties prop;
		
		public String path;
		public static void captureScreenshot(WebDriver driver, String filePath) {
			
			TakesScreenshot ts= (TakesScreenshot)driver;
			File src=ts.getScreenshotAs(OutputType.FILE);
			
			File dest= new File(filePath);
			
			try {
				FileUtils.copyFile(src, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	
		}
		
		public static WebDriver driverSelection(String browsername) {
			
			if(browsername.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver","C:\\Users\\meets\\Desktop\\MoneyHopAutomatedTestSuite\\src\\main\\resources\\chromedriver.exe");
				driver= new ChromeDriver();
			}
			
			else if(browsername.equalsIgnoreCase("firefox"))
			{
				System.setProperty("webdriver.gecko.driver","C:\\Users\\meets\\Desktop\\MoneyHopAutomatedTestSuite\\src\\main\\resources\\geckodriver.exe");
				driver= new FirefoxDriver();
			}
			else if(browsername.equalsIgnoreCase("iexplore"))
			{
				System.setProperty("webdriver.ie.driver","C:\\Users\\meets\\Desktop\\MoneyHopAutomatedTestSuite\\src\\main\\resources\\IEDriverServer.exe");
				driver= new InternetExplorerDriver();
			}
		
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			return driver;
			
		}
		
		public static Properties init_properties()
		{
			prop = new Properties();
			
			try {
				FileInputStream file = new FileInputStream("C:\\Users\\meets\\Desktop\\MoneyHopAutomatedTestSuite\\src\\test\\resources\\Test.properties");
				prop.load(file);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return prop;
			
		}
}