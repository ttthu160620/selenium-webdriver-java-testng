package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Tab_Window {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
//		driver = new FirefoxDriver();
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	
		
	//@Test
	public void TC01_Tab() {
		driver.get("https://automationfc.github.io/basic-form/index.html");	
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		
		driver.switchTo().window("");
	}
	
	@Test
	public void TC02_Tab_Naukri_ByID() {
		driver.get("https://www.naukri.com");	
		//lấy id của page hiện tại
		String homePageWindowID = driver.getWindowHandle();
		System.out.println("Tab A = " +homePageWindowID);
		// click vào Job/Copanies/Resiger rồi swich qua trang đó.
		//Jobs
		driver.findElement(By.xpath("//a[@title='Search Jobs']")).click();
		
		Set<String> allWindowID = driver.getWindowHandles(); // set chỉ lưu duy nhất, không lưu trùng
		
		//Trong trường hợp chỉ có duuy nhất 2 tab/window thì có thể dùng ID của nó
		//dùng biến tạm để duyệt qua all phần tử
		for(String id : allWindowID) {
			//nếu id khác id page hiện tại thì switch qua
			if(!id.equals(homePageWindowID)) {
				driver.switchTo().window(id);
			}
		}
		System.out.println(driver.getCurrentUrl());		
		//switch từ B về lại home page
		String jobPageWindow = driver.getWindowHandle();
		allWindowID = driver.getWindowHandles(); // set chỉ lưu duy nhất, không lưu trùng
		
		//Cách 1
		//Trong trường hợp chỉ có duuy nhất 2 tab/window thì có thể dùng ID của nó
		//dùng biến tạm để duyệt qua all phần tử
		for(String id : allWindowID) {
			//nếu id khác id page hiện tại thì switch qua
			if(!id.equals(jobPageWindow)) {
				driver.switchTo().window(id);
			}
		}
		sleepInSecond(3);
		
		//Companies
		driver.findElement(By.xpath("//a[@title='Explore top companies hiring on Naukri']")).click();
		String companyPageWindowID = driver.getWindowHandle();
		switchWindowByID(companyPageWindowID);
		
		closeAllWindowsWithoutParentWindow(homePageWindowID);
		
		//Register
		//driver.findElement(By.xpath("//div[text()='Register']")).click();
		
		//driver.switchTo().window("");
	}
	
	//@Test
	public void TC03_Tab_Oxford_By_Title() {
		//Dùng được cho nhiều tab/window
		driver.get("https://dictionary.cambridge.org/vi/");	
		String titleHomepage = driver.getTitle();
		String homepageID = driver.getWindowHandle();
		
		driver.findElement(By.xpath("//i/following-sibling::span[text()='Đăng nhập']")).click();
		swicthWindowByTitle("Login");
		//click login ở widow login - bỏ trống
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//form[@id='gigya-login-form']//input[@name='username']/following-sibling::span")).getText(),
				"This field is required");
		Assert.assertEquals(driver.findElement(By.xpath("//form[@id='gigya-login-form']//input[@name='password']/following-sibling::span")).getText(),
				"This field is required");
		
		//Nhập dữ liệu
		driver.findElement(By.xpath("//form[@id='gigya-login-form']//input[@name='username']")).sendKeys("automationfc.com@gmail.com");
		driver.findElement(By.xpath("//form[@id='gigya-login-form']//input[@name='password']")).sendKeys("Automation000***");
		sleepInSecond(2);
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
		
		//login thành công => về lại homepage => verify
		//close login
		
		swicthWindowByTitle(titleHomepage);
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='tb lpl-2 cdo-username']")).getText(), "Automation FC");
		closeAllWindowsWithoutParentWindow(homepageID);
	}

	@AfterClass
	public void afterClass() {
		//driver.quit();
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