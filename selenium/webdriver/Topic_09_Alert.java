package webdriver;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Alert {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String authenChrome = projectPath + "\\autoITScript\\authen_chrome.exe";
	String authenFireFox = projectPath + "\\autoITScript\\authen_firefox.exe";
	
	JavascriptExecutor jsExecutor;
	WebDriverWait expliciWait;
	Alert alert;
	
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
//		driver = new FirefoxDriver();
		
		// Khởi tạo sau khi driver này được sinh ra
		//JsExecutor/ WebDriverWait/ Actions/...
		jsExecutor = (JavascriptExecutor) driver;
		expliciWait = new WebDriverWait(driver, 30);
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	
	//@Test
	public void TC01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[@onclick='jsAlert()']")).click();
		sleepInSecond(3);
		
		//switch qua alert
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		
		alert.accept();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
	}
	
	//@Test
	public void TC02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[@onclick='jsConfirm()']")).click();
		sleepInSecond(3);
		
		//switch
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		
		//nhấn cancel
		alert.dismiss();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
	}
	
	//@Test
	public void TC03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[@onclick='jsPrompt()']")).click();
		sleepInSecond(3);
		
		//switch
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		
		alert.sendKeys("abc");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: abc");
	}
	
	//@Test
	public void TC04_Authentication_Alert() {
		//selenium library do not support for Authentication alert
		//Sử dung By pass qua link -> truyền thẳng vào url
		String username = "admin";
		String password = "admin";
		driver.get("http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");
	
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']/p")).getText(), 
				"Congratulations! You must have the proper credentials.");
	}
	
	//@Test
	public void TC05_Authentication_Alert_II() {
		//get attribute value rồi by pass link
		String username = "admin";
		String password = "admin";
		driver.get("http://the-internet.herokuapp.com");
		
		String basicAuthLink = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		
		String[] basicAuth = basicAuthLink.split("//");
		
		basicAuthLink = basicAuth[0] + "//" + username + ":" + password + "@" + basicAuth[1];
		driver.get(basicAuthLink);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']/p")).getText(), 
				"Congratulations! You must have the proper credentials.");
	}
	
	@Test
	public void TC06_Authentication_Alert_AutoIT() throws IOException {
		// SỬ dụng AutoIT -> chỉ dùng được cho mỗi win, không dùng được cho MAC, Linux
		String username = "admin";
		String password = "admin";
		driver.get("http://the-internet.herokuapp.com");
		
		//Script sẽ chạy trước để chờ authen alert bật lên sau 
		
		if(driver.toString().contains("Chrome")) {
			Runtime.getRuntime().exec(new String[] {authenChrome, username, password});
		}
		if(driver.toString().contains("FireFox")) {
			Runtime.getRuntime().exec(new String[] {authenFireFox, username, password});
		}
		
		driver.findElement(By.xpath("//a[text()='Basic Auth']")).click();
		sleepInSecond(7);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='example']/p")).getText(), 
				"Congratulations! You must have the proper credentials.");
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