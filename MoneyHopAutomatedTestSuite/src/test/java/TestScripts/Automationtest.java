package TestScripts;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.Utility;
import junit.framework.Assert;

public class Automationtest  {

	WebDriver driver;
	Properties prop;

	@BeforeClass
	public void setup()
	{
		//System.setProperty("webdriver.chrome.driver","C:\\Users\\ADMIN\\eclipse-workspace\\Selenium\\MoneyHopAutomatedTestSuite\\src\\main\\resources\\chromedriver.exe");
		//driver= new ChromeDriver();
		Utility.driverSelection("chrome");


	}

	@Test
	public void moneyTransfer() throws InterruptedException {

		//Login into application and verify title
		Utility.driver.get("https://dev-app.moneyhop.co/");
		prop=Utility.init_properties();
		Utility.driver.findElement(By.id("email")).sendKeys(prop.getProperty("username"));
		Utility.driver.findElement(By.id("password")).sendKeys(prop.getProperty("password"));
		Utility.driver.findElement(By.xpath(prop.getProperty("loginbutton"))).click();
		String actualTitle=Utility.driver.getTitle();
		System.out.println(actualTitle);
		Assert.assertEquals("HOP Remittance", actualTitle);
		Utility.captureScreenshot(Utility.driver, "./Screenshots/title.png");

		//*****************Initiate transfer*************
		WebElement textbox= Utility.driver.findElement(By.xpath(prop.getProperty("amounttextbox")));
		WebDriverWait wait= new WebDriverWait(Utility.driver,120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("amounttextbox"))));
		textbox.click();
		textbox.clear();
		textbox.sendKeys("20000");
		textbox.sendKeys(Keys.TAB);
		Thread.sleep(2000);
		String initialvalue=Utility.driver.findElement(By.xpath(prop.getProperty("receivetextbox"))).getAttribute("value");
		System.out.println("USD value is"+initialvalue);

		//select any other currency value from dropdown
		Utility.driver.findElement(By.xpath(prop.getProperty("currencydropdwn"))).click();
		List<WebElement> values=Utility.driver.findElements(By.xpath("//*[@id='menu-']/div[3]/ul/li"));
		int size=values.size();
		System.out.println("Total number of currencies is"+size);
		for(WebElement valuelist: values)
		{
			String currency=valuelist.getAttribute("data-value");
			if(currency.equalsIgnoreCase("GBP"))
			{
				//Actions action= new Actions(Utility.driver);
				//action.moveToElement(valuelist).click();
				valuelist.click();
				break;
			}
		}
		Thread.sleep(3000);
		String changedvalue=Utility.driver.findElement(By.xpath(prop.getProperty("receivetextbox"))).getAttribute("value");
		System.out.println("GBP value is"+changedvalue);


		Utility.captureScreenshot(Utility.driver, "./Screenshots/updatedcurrency.png");
		Utility.driver.findElement(By.xpath(prop.getProperty("initiatetransferbtn"))).click();
		Thread.sleep(2000);

		//select purpose ofpayment

		WebElement purposedropdown=Utility.driver.findElement(By.xpath(prop.getProperty("purposedrpdown")));
		purposedropdown.click();
		purposedropdown.sendKeys(Keys.DOWN);
		purposedropdown.sendKeys(Keys.ENTER);
		Utility.driver.findElement(By.xpath(prop.getProperty("continuetransferbtn"))).click();

		//********************Recipient details*****************************//

		Utility.driver.get("https://dev-app.moneyhop.co/pickrecipient");
		Utility.driver.findElement(By.xpath(prop.getProperty("addrecipientbtn"))).click();
		Thread.sleep(4000);
		WebElement bankacct=Utility.driver.findElement(By.xpath(prop.getProperty("bankaccountcountry")));
		bankacct.click();
		bankacct.sendKeys(Keys.DOWN);
		bankacct.sendKeys(Keys.DOWN);
		bankacct.sendKeys(Keys.ENTER);

		Utility.driver.findElement(By.xpath(prop.getProperty("firstname"))).sendKeys("Tom");
		Utility.driver.findElement(By.xpath(prop.getProperty("lastname"))).sendKeys("Edward");
		Utility.driver.findElement(By.xpath(prop.getProperty("relationshipdrpdown"))).click();
		Utility.driver.findElement(By.xpath(prop.getProperty("selectrelationship"))).click();


		WebElement bankname=Utility.driver.findElement(By.xpath(prop.getProperty("bankname")));
		JavascriptExecutor js= (JavascriptExecutor)Utility.driver;
		js.executeScript("arguments[0].scrollIntoView(true);", bankname);
		bankname.sendKeys("XYZ");

		Utility.driver.findElement(By.xpath(prop.getProperty("swiftcode"))).sendKeys("12567698");
		Utility.driver.findElement(By.xpath(prop.getProperty("bankaccountnumber"))).sendKeys("87654323376");
		Utility.driver.findElement(By.xpath(prop.getProperty("ibancode"))).sendKeys("hg554");
		Utility.driver.findElement(By.xpath(prop.getProperty("address"))).sendKeys("markstrt1");
		Utility.driver.findElement(By.xpath(prop.getProperty("city"))).sendKeys("mexi");
		Utility.driver.findElement(By.xpath(prop.getProperty("postalcode"))).sendKeys("78625");

		WebElement savebtn=Utility.driver.findElement(By.xpath(prop.getProperty("savebtn")));
		JavascriptExecutor js1= (JavascriptExecutor)Utility.driver;
		js1.executeScript("arguments[0].scrollIntoView(true);", savebtn);
		savebtn.click();

		// Confirm recipient

		WebElement radiobtn=Utility.driver.findElement(By.xpath(prop.getProperty("radiobtn")));
		WebDriverWait wait1=new WebDriverWait(Utility.driver,120);
		wait1.until(ExpectedConditions.elementToBeClickable(radiobtn));

		Actions action=new Actions(Utility.driver);
		action.moveToElement(radiobtn).click().perform();
		Utility.driver.findElement(By.xpath(prop.getProperty("confirmbtn"))).click();
		Thread.sleep(4000);
		Utility.captureScreenshot(Utility.driver, "./Screenshots/reviewpage.png");

		WebElement payfull=Utility.driver.findElement(By.name("paymentoptions"));
		JavascriptExecutor js2= (JavascriptExecutor)Utility.driver;
		js2.executeScript("arguments[0].scrollIntoView(true);", payfull);
		payfull.click();

		Utility.driver.findElement(By.xpath(prop.getProperty("checkbox1"))).click();
		Utility.driver.findElement(By.xpath(prop.getProperty("checkbox2"))).click();
		Utility.driver.findElement(By.xpath(prop.getProperty("confirmcontbtn"))).click();
		Thread.sleep(4000);
		Utility.captureScreenshot(Utility.driver, "./Screenshots/paymentspage.png");


	}

	@AfterClass
	public void tearDown() {
		Utility.driver.quit();
	}

}
