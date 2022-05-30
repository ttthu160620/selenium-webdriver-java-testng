package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_04_Xpath_Exercise_Login {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	By emailTextboxBy = By.xpath("//div[@class='main']//input[@type='email']"); // //form[@id='login-form']//input[@type='email']
	By passwordTextboxBy = By.xpath("//form[@id='login-form']//input[@type='password']");
	By emailErrorMessage = By.xpath("//form[@id='login-form']//div[@id='advice-required-entry-email']");
	By passwordErrorMessage = By.xpath("//form[@id='login-form']//div[@id='advice-required-entry-pass']");
	By myAccountBtn = By.xpath("//div[@class = 'footer']//a[@title = 'My Account']");
	By loginBtn = By.xpath("//form//button[@title='Login']");
	//form[@id='login-form']//div[@id='advice-validate-email-email']
	String firstName, lastName, email, password, fullName;
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
		firstName = "Thu";
		lastName = "Tran";
		email = "thutran" + getRandomNumber() + "@gmail.com";
		password = "123456789";
		fullName = firstName + " " + lastName;
	}

	@BeforeMethod //chạy lặp lại theo từng testcase
	public void beforeMethod() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(myAccountBtn).click();
	}
	
	
	@Test
	public void Login_01_Empty_Data() {
		driver.findElement(emailTextboxBy).clear();
		driver.findElement(passwordTextboxBy).clear();
		
		driver.findElement(loginBtn).click();
		
		Assert.assertEquals(driver.findElement(emailErrorMessage).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(passwordErrorMessage).getText(), "This is a required field.");
	}

	@Test
	public void Login_02_Invalid_Email() {
		driver.findElement(emailTextboxBy).sendKeys("123434234@12312.123123");
		driver.findElement(passwordTextboxBy).sendKeys("123456");
		
		driver.findElement(loginBtn).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//form[@id='login-form']//div[@id='advice-validate-email-email']")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");	
	}
	
	@Test
	public void Login_03_Incorrect_Password() {
		driver.findElement(emailTextboxBy).sendKeys("automation@gmail.com");
		driver.findElement(passwordTextboxBy).sendKeys("123");
		
		driver.findElement(loginBtn).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//form[@id='login-form']//div[@id='advice-validate-password-pass']")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	@Test
	public void Login_04_Invalid_Email_Or_Password() {
		//incorrect password
		driver.findElement(emailTextboxBy).sendKeys("automation@gmail.com");
		driver.findElement(passwordTextboxBy).sendKeys("123123123");
		
		driver.findElement(loginBtn).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
		
		//incorrect email
	}
	
	@Test
	public void Login_05_Create_New_Account() {
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		
		
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		
		//Verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div.page-title>h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class= 'success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='hello']/strong")).getText(), "Hello, " + fullName + "!");
		
		String contactInfor = driver.findElement(By.xpath("//h3[text() = 'Contact Information']/parent::div/following-sibling::div/p")).getText();
		Assert.assertTrue(contactInfor.contains(fullName));
		Assert.assertTrue(contactInfor.contains(email));
		
		//logout
		driver.findElement(By.xpath("//header//span[text()='Account']")).click();
		driver.findElement(By.xpath("//header//a[@title='Log Out']")).click();
		
		//Verify home page
		driver.findElement(By.cssSelector("div.page-title img[src$='logo.png']")).isDisplayed();
		
	}
	
	@Test
	public void Login_06_Valid_Data() {
		driver.findElement(emailTextboxBy).sendKeys(email);
		driver.findElement(passwordTextboxBy).sendKeys(password);
		
		driver.findElement(loginBtn).click();
		
		//Verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div.page-title>h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='hello']/strong")).getText(), "Hello, " + fullName + "!");
		
		String contactInfor = driver.findElement(By.xpath("//h3[text() = 'Contact Information']/parent::div/following-sibling::div/p")).getText();
		Assert.assertTrue(contactInfor.contains(fullName));
		Assert.assertTrue(contactInfor.contains(email));
	
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInSecond (long second) {
		try {
			Thread.sleep( second * 1000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public int getRandomNumber() {
		Random ran = new Random();
		return ran.nextInt(999);
	}
}