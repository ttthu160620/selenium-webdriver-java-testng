package webdriver;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Upload_File {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String pikachu1 = "OIP.jpg";
	String pikachu2 = "R.jpg";
	String pikachu3 = "R (1).jpg";
	
	//Chỉ support for window
	String uploadFileForderPath = projectPath + File.separator + "\\uploadFiles\\";
	String pikachu1FilePath = uploadFileForderPath + pikachu1;
	String pikachu2FilePath = uploadFileForderPath + pikachu2;
	String pikachu3FilePath = uploadFileForderPath + pikachu3;
	
	//MAC/ Linux:  "/uploadFiles/"
	
	
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
//		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
//		driver = new ChromeDriver();
		
		//run headless: non UI
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");	
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1920x1080");
		driver = new ChromeDriver(options);
		
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
//		driver = new FirefoxDriver();
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	//@Test
	public void TC01_One_File_One_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		//Selenium senkeys - không cần click vào button để bật diaglog -> chỉ sendkey vào input
		//Không nên dùng biến webElement vì sau khi upload 1 file -> update lại status -> element bị thay đổi
		//upload file
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(pikachu1FilePath);
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(pikachu2FilePath);
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(pikachu3FilePath);
		sleepInSecond(2);
		
		//Verify
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + pikachu1+ "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + pikachu2+ "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + pikachu3+ "']")).isDisplayed());
		
		//Click to button upload at each file
		List<WebElement> listUploadbutton = driver.findElements(By.cssSelector("table button.start"));
		for(WebElement button : listUploadbutton) {
			button.click();
			sleepInSecond(1);
		}
		
		//verify upload success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='"+pikachu1+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='"+pikachu2+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='"+pikachu3+"']")).isDisplayed());
	
	}
	
	@Test
	public void TC02_Multiple_File_One_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		//upload 3 files once
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(pikachu1FilePath + "\n" + pikachu2FilePath + "\n" + pikachu3FilePath);
		sleepInSecond(2);
		
		//Verify
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + pikachu1+ "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + pikachu2+ "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + pikachu3+ "']")).isDisplayed());
		
		//Click to button upload at each file
		List<WebElement> listUploadbutton = driver.findElements(By.cssSelector("table button.start"));
		for(WebElement button : listUploadbutton) {
			button.click();
			sleepInSecond(1);
		}
		
		//verify upload success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='"+pikachu1+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='"+pikachu2+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='"+pikachu3+"']")).isDisplayed());
	
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
	
	public void switchWindowByID(String currentWindowID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for(String id : allWindowIDs) {
			//nếu id khác id page hiện tại thì switch qua
			if(!id.equals(currentWindowID)) {
				driver.switchTo().window(id);
			}
		}
	}
	
	//Dùng được cho nhiều tab/window
	public void swicthWindowByTitle(String expectedTitle) {
		//Lấy all id, duyệt qua rồi switch từng cái, nếu đúng thì dừng.
		Set<String> allWindowIDs = driver.getWindowHandles();
		
		for(String id : allWindowIDs) {
			//swich qua rồi mới kiểm tra điều kiện
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if(actualTitle.equals(expectedTitle)) {
				break;
			}
		}
	}
	
	public boolean closeAllWindowsWithoutParentWindow(String parentID) {
		Set<String> allWindowsID = driver.getWindowHandles();
		for(String id : allWindowsID) {
			if(!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
		if(driver.getWindowHandles().size() == 1) {
			return true;
		}
		else {
			return true;
		}
	}
}