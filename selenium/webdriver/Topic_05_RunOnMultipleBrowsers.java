package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_05_RunOnMultipleBrowsers {
	//Khai báo biến đại diện cho Selenium WebDriver
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@Test
	public void TC_01_FireFox() {
		// Set geckodriver: giao tiếp giữa browser và code
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
		
		// Bật trình duyệt firefox
		driver = new FirefoxDriver();
		
		// set time đi tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// Bật browser to lên
		driver.manage().window().maximize();
		
		// mở app
		driver.get("https://www.facebook.com/");
		
		//Trả về URL của page hiện tại
		driver.getCurrentUrl();
		
		//Lấy source code của page hiện tại: html/css/js/jquery -> dùng để verify tương đối 1 giá trị nào đó có trong page
		driver.getPageSource();
		Assert.assertTrue(driver.getPageSource().contains("Welcome to our store"));
		
		//Lấy title của page hiện tại
		driver.getTitle();
		Assert.assertTrue(driver.getTitle().contains(""));
		
		//WebDriver API - windows/Tab
		//Trả về 1 ID của tab hiện tại
		String signUpTabID = driver.getWindowHandle();
		
		//Trả về ID của tất cả các tab hiện tại
		Set<String> allTabID = driver.getWindowHandles();
		
		//Xử lý cookie (Framework)
		driver.manage().getCookies();
		
		//Lấy vị trí của trình duyệt so với độ phân giải màn hình
		Point browserPosition = driver.manage().window().getPosition();
		
		//Set vị trí của browser tại 0x250
		driver.manage().window().setPosition(new Point(0, 250));
		
		//Lấy ra chiều rộng/cao của browser
		Dimension browserSize = driver.manage().window().getSize();
		
		//Set browser mở với kích thước nào. -> test responsive
		driver.manage().window().setSize(new Dimension(1920, 1080));
		
		//WebDriver API - Alert/Authentication Alertl
		driver.switchTo().alert();
		driver.quit(); // đóng trình duyệt
	}

	@Test
	public void TC_02_Edge() {
		System.setProperty("webdriver.edge.driver", projectPath + "\\browserDriver\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.get("https://www.facebook.com/");
		driver.quit();
	}

	@Test
	public void TC_03_Chrome() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.facebook.com/");
		driver.quit();
		
	}


//	@AfterClass
//	public void afterClass() {
//		driver.quit();
//	}
}