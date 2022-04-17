package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_WebBrowser_Excercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Url() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		String myAccountURL = driver.getCurrentUrl();
		Assert.assertEquals(myAccountURL,"http://live.techpanda.org/index.php/customer/account/login/");
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.techpanda.org/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_ValidatePageTitle() {
		driver.get("http://live.techpanda.org/");	
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		String titleLoginPage = driver.getTitle();
		Assert.assertEquals(titleLoginPage, "Customer Login");
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String titleCreateAccountPage = driver.getTitle();
		Assert.assertEquals(titleCreateAccountPage, "Create New Customer Account");
		
	}

	@Test
	public void TC_03_Navigation() {
		driver.get("http://live.techpanda.org/");	
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		
		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.techpanda.org/index.php/customer/account/create/");
		// back lại trang trước - Login Page
		driver.navigate().back();
		
		String myAccountURL = driver.getCurrentUrl();
		Assert.assertEquals(myAccountURL,"http://live.techpanda.org/index.php/customer/account/login/");
		
		driver.navigate().forward();
		//verify title
		String titleCreateAccountPage = driver.getTitle();
		Assert.assertEquals(titleCreateAccountPage, "Create New Customer Account");
		
	}
	
	@Test
	public void TC_04_Source() {
		driver.get("http://live.techpanda.org/");	
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		
		//Verify text có trong page
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}