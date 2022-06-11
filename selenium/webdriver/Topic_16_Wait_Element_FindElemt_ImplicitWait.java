package webdriver;

import static org.testng.Assert.assertTrue;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Wait_Element_FindElemt_ImplicitWait {
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
		
		explicitWait = new WebDriverWait(driver, 10);
		
		//cách 0.5s thì tìm 1 lần
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); 
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC01_Find_Element() {
		driver.get("https://www.facebook.com/");
		// có 1 element duy nhất -> trả về element đó
		//nếu chưa xuất hiện thì cách 0,5s tìm lại 1 lần until timeout
		System.out.println("Start time: "+ getCurrentTime());
		driver.findElement(By.xpath("//input[@id='email']"));
		System.out.println("End time: "+ getCurrentTime());
		
		//không có element: cách 0,5s sẽ tìm 1 lần, đến khi 15s thì failed, không chạy các step còn lại
		//exception: NoSuchElement
		
		//Có nhiều hơn 1 element: Sẽ thao tác với element tìm thấy đầu tiên
		
	}
	
	@Test
	public void TC02_Find_Elements() {
		//có 1 element duy nhất -> trả về list chứa 1 element đó
		
		// không có element: list is empty => khi hết timeout thì không đánh failed
		//-> vẫn chạy tiếp các step sau
		//-> thường ùng để verify element not in DOM
		
		// Có nhiều element -> trả về list chứa all elements
	}
	
	@Test
	public void TC03_Implicit_Wait() {
		// không set implicit -> timeout = 0
		// chỉ ảnh hưởng đến find element/elements
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(),"Hello World!");
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