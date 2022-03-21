package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Xpath {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
		//Mở app lên
		driver.get("https://www.facebook.com/");
	}

	@Test
	public void TC_01_ValidateCurrentUrl() {
		//Tìm - số ít - trả về 1 cái
		//Thao tác trực tiếp không cần khai báo biến - sd 1 lần/ k dùng lại element này
		driver.findElement(By.id("")).click();
		driver.findElement(By.id("")).isDisplayed();
		
		//Khai báo biến, dùng lại element nhiều lần
		WebElement loginButton = driver.findElement(By.id(""));
		loginButton.click();
		loginButton.isDisplayed();
		
		//Tìm (Find) - số nhiều - trả về 1 or >1
		List<WebElement> loginCheckboxes = driver.findElements(By.id(""));
		
		for (int i = 0; i < loginCheckboxes.size(); i++) {
			loginCheckboxes.get(i).click();
		}
		//Thao tác với Email textbox
		
		//Thao tác với Password textbox
		
		// Thao tác Login
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