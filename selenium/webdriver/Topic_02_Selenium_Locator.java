package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Selenium_Locator {
	//Khai báo biến đại diện cho Selenium WebDriver
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
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
	}

	@Test
	public void TC_01_ValidateCurrentUrl() {
		// Bắt id
		driver.findElement(By.id("email")).sendKeys("minhthu160620@gmail.com");
		
		// Name
		driver.findElement(By.className("fb_logo")).isDisplayed();
		
		//Tagname: tìm xem có bao nhiêu element cùng loại
		driver.findElement(By.tagName("a"));
		
		// Link Text: Truyền cả text vào
		driver.findElement(By.linkText("Tiếng Việt"));
		driver.findElement(By.partialLinkText("Tiếng")); // độ chính xác không cao
		
		//Css
		driver.findElement(By.cssSelector("input[id='email']"));
		driver.findElement(By.cssSelector("input#email"));
		driver.findElement(By.cssSelector("#email"));
		
		driver.findElement(By.cssSelector("img[class='fb_logo _8ilh img']"));
		driver.findElement(By.cssSelector("img.fb_logo"));
		driver.findElement(By.cssSelector(".fb_logo"));
		
		driver.findElement(By.cssSelector("input[name='email']"));
		driver.findElement(By.cssSelector("a"));
		
		//Css không làm việc với text (dùng thuộc tính khác của a để thao tác)
		driver.findElement(By.cssSelector("a[title='Vietnamese']"));
		driver.findElement(By.cssSelector("a[onclick*='vi_VN']"));
		driver.findElement(By.cssSelector("a[title*='Vietnam']"));
		
		//Xpath
		driver.findElement(By.xpath("//input[@id='email']"));
		driver.findElement(By.xpath("//img[@class='fb_logo _8ilh img']"));
		driver.findElement(By.xpath("//img[contains(@class,'fb_logo')]"));
		driver.findElement(By.xpath("//img[starts-with(@class,'fb_logo')]"));
		driver.findElement(By.xpath("//input[@name='email']"));
		driver.findElement(By.xpath("//a"));
		driver.findElement(By.xpath("//a[text()='Tiếng Việt']"));
		driver.findElement(By.xpath("//a[contains(text(),'Tiếng')]"));
	}

	@Test
	public void TC_02_ValidatePageTitle() {
		
	}

	@Test
	public void TC_03_LoginFormDisplayed() {
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}