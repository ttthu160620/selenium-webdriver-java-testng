package webdriver;

import java.io.File;
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

public class Topic_17_Wait_Static_Explicit {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	String pikachu1 = "OIP.jpg";
	String pikachu2 = "R.jpg";
	String pikachu3 = "R (1).jpg";
	
	String uploadFileForderPath = projectPath + File.separator + "\\uploadFiles\\";
	String pikachu1FilePath = uploadFileForderPath + pikachu1;
	String pikachu2FilePath = uploadFileForderPath + pikachu2;
	String pikachu3FilePath = uploadFileForderPath + pikachu3;
	
	//wait rõ ràng với các dkien cụ thể
	WebDriverWait explicitWait;
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
//		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 15);
		//cách 0.5s thì tìm 1 lần
		//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); 
		driver.manage().window().maximize();
	}
	
	//@Test
	public void TC01_Find_Element() throws InterruptedException {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		Thread.sleep(5000);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(),"Hello World!");
	}
	
	//@Test
	public void TC02_ExplicitWait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		
		//cách 1: chờ loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));
		
		//cách 2: chờ đến khi text appear
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='finish']/h4")));
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(),"Hello World!");
	}
	
	//@Test
	public void TC03_AjaxLoading() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		//wait cho Date blocker xuất hiện
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceholder1_Panel1")));
		
		//verify chưa chọn date
		WebElement selectedDateText = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']"));
		Assert.assertEquals(selectedDateText.getText(),"No Selected Dates to display.");
		
		//wait cho đến khi có thể click và Click ngày bất kỳ
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='7']"))).click();
		
		//wait cho đến khi icon loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id*='RadCalendar1'] div[class='raDiv']")));
		
		//verify text
		selectedDateText = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']"));
		Assert.assertEquals(selectedDateText.getText(),"Tuesday, June 7, 2022");
		
		//wait cho ngày được selected thành công
		WebElement todaySelected = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='7']")));
		//verify ngày được chọn
		Assert.assertTrue(todaySelected.isDisplayed());
	}
	
	@Test
	public void TC04_UploadFile() {
		driver.get("https://gofile.io/uploadFiles");
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(pikachu1FilePath + "\n" + pikachu2FilePath + "\n" + pikachu3FilePath);
		
		//wait cho đến khi upload file thành công
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress"))));
		
		//wait cho text dc visible
		WebElement uploadedText =  explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));
		Assert.assertTrue(uploadedText.isDisplayed());
		
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