package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_Page_Ready {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	Actions action;
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
		//System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
		//driver = new FirefoxDriver();
		
		explicitWait = new WebDriverWait(driver, 30);
		action = new Actions(driver);
		jsExecutor =  (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_OrangeHRM_API_Page_Ready () {
		driver.get("https://api.orangehrm.com/");
		//wait cho spinner invisible
		//explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("loader")));
		
		//wait cho page ready
		Assert.assertTrue(isLoadPageSuccess());
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='pull-left']/h1[text()='OrangeHRM REST API Documentation']")).isDisplayed());
	}
	
	@Test
	public void TC_02_Blog_Page_Ready() {
		driver.get("https://blog.testproject.io");
		sleepInSecond(2);
//		Assert.assertEquals(popupAppear(),125);
	
		
		jsExecutor.executeScript("document.querySelector('.mailch-bg').classList.add('open');");
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.id("mailch-bg")).isDisplayed());
		
		
		//handle popup
		if(driver.findElement(By.id("mailch-bg")).isDisplayed()) {
			driver.findElement(By.id("close-mailch")).click();
		}
		
		//hover mouse to search text box
		action.moveToElement(driver.findElement(By.cssSelector("section#search-2 input.search-field"))).perform();
		
//		action.moveToElement(driver.findElement(By.xpath("//ul[@id='menu-top-menu-1']//a[@title='Platform']"))).perform();
//		sleepInSecond(2);
//		action.moveToElement(driver.findElement(By.cssSelector(".fit-vids-style"))).perform();
		
		Assert.assertTrue(isLoadPageSuccess());
		
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("selenium");
		driver.findElement(By.cssSelector("section#search-2 .glass")).click();
		Assert.assertTrue(isLoadPageSuccess());
		
		//verify
		List<WebElement> listResult = driver.findElements(By.cssSelector(".post-on-archive-page"));
		for(WebElement result : listResult) {
			Assert.assertTrue(result.getText().contains("Selenium"));
		}
	}
	
//	@Test
	public void TestCase() {
		driver.get("https://blog.testproject.io");
		sleepInSecond(2);
		
		
	    this.jsExecutor = (JavascriptExecutor) driver;
		//jsExecutor.executeScript("return 10;");

		jsExecutor.executeScript("document.querySelector('.mailch-bg').classList.add('open');");
		sleepInSecond(10);
		//Assert.assertTrue(driver.findElement(By.id("mailch-bg")).isDisplayed());
	}
	
	//Test
	public void TestPopup() {
		driver.get("https://blog.testproject.io");
		sleepInSecond(2);
//		Assert.assertEquals(popupAppear(),125);
		for(int i=0;i<2;i++) {
			var I = driver.findElement(By.xpath("//ul[@id='menu-top-menu-1']//a[@title='Platform']"));
			Actions a = new Actions(driver);
			a.moveToElement(I).click().build().perform();
			sleepInSecond(1);
			var J = driver.findElement(By.xpath("//ul[@id='menu-top-menu-1']"));
			a.moveToElement(I,-10,0).click().build().perform();
			sleepInSecond(1);
			var K = driver.findElement(By.tagName("html"));
			a.moveToElement(K).click().build().perform();
			sleepInSecond(1);
		}
		
		sleepInSecond(5);
//		//handle popup
		//if(driver.findElement(By.id("mailch-bg")).isDisplayed()) {
		//	driver.findElement(By.id("close-mailch")).click();
		//}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public boolean isLoadPageSuccess() {
		explicitWait = new WebDriverWait(driver, 30);
		jsExecutor =  (JavascriptExecutor) driver;
		
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			
			@Override
			public Boolean apply(WebDriver arg0) {
				// TODO Auto-generated method stub
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active == 0)");
			}	
		};
		
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				// TODO Auto-generated method stub
				return (Boolean) jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}	
		};
		
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
	
	public int popupAppear() {
		jsExecutor =  (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 15);
		ExpectedCondition<Integer> jQuery = new ExpectedCondition<Integer>() {
			
			@Override
			public Integer apply(WebDriver arg0) {
				// TODO Auto-generated method stub
				return (Integer) jsExecutor.executeScript("return document.querySelector('.mailch-bg').classList.add('open')");
			}	
		};
		return explicitWait.until(jQuery);
	}
	
	public void sleepInSecond (long second) {
		try {
			Thread.sleep( second * 1000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}