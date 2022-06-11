package webdriver;

import static org.testng.Assert.assertTrue;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_Wait_Mix_Implicit_Explicit {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait explicitWait;
	
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
//		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}
	
	// nếu dùng explicit với By: thì implicit và explicit sẽ chạy theo cơ chế async (bất đồng bộ, song song)
	// nếu dùng webelement: thì tìm element trước (ảnh hưởng vởi implicit)
	// nếu pass mới vào hàm của explicit để chạy tiếp
	// nếu fail thì đánh TC fail -> k vào hàm của explicit
	
	//@Test
	public void TC01_Element_Found() {
		driver.get("https://www.facebook.com/");
		
		By emailIDBy = By.id("email");
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.findElement(emailIDBy).isDisplayed();
		
		explicitWait = new WebDriverWait(driver, 15);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(emailIDBy));
	}
	
	@Test
	public void TC02_Element_Not_Found_Mix() {
		//implicit sẽ k bị ảnh hưởng của bất kì loại wait nào
		driver.get("https://www.facebook.com/");
		By emailIDBy = By.id("hihi");
		
		//case 1: implicit < explicit
		//case 2: implicit = explicit
		//case 3: implicit > explicit
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Start time: " + getCurrentTime());
		try {
			driver.findElement(emailIDBy).isDisplayed();
		} catch (Exception e) {
		
		}
		System.out.println("End time: " + getCurrentTime());
		
		explicitWait = new WebDriverWait(driver, 5);
		System.out.println("Start time: " + getCurrentTime());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(emailIDBy));
		} catch (Exception e) {
		}
		System.out.println("End time: " + getCurrentTime());
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
	 public String getCurrentTime() {
		 //date hour minute second milisecond
		 Date date = new Date();
		 return date.toString();
	 }
}