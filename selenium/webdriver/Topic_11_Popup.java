package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Popup {
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
	public void TC01_Fixed_Popup_I() {
		driver.get("https://ngoaingu24h.vn/");
		
		By loginPopup = By.xpath("//footer/preceding-sibling::div[@id='modal-login-v1']");
		//verify popup k hien thi
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
		
		driver.findElement(By.xpath("//button[@class='login_ icon-before']")).click();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		By account = By.xpath("//footer/preceding-sibling::div[@id='modal-login-v1']//input[@id='account-input']");
		By password = By.xpath("//footer/preceding-sibling::div[@id='modal-login-v1']//input[@id='password-input']");
		driver.findElement(account).sendKeys("automationfc");
		driver.findElement(password).sendKeys("automationfc");
		
		By loginButtonPopup = By.xpath("//footer/preceding-sibling::div[@id='modal-login-v1']//button[@class='btn-v1 btn-login-v1 buttonLoading']");
		driver.findElement(loginButtonPopup).click();
		sleepInSecond(3);
		
		By errorMessage = By.xpath("//footer/preceding-sibling::div[@id='modal-login-v1']//div[@class='row error-login-panel']");
		Assert.assertEquals(driver.findElement(errorMessage).getText(), "Tài khoản không tồn tại!");
		
		driver.findElement(By.xpath("//footer/preceding-sibling::div[@id='modal-login-v1']//button[@class='close']")).click();
	}
	//@Test
	public void TC02_Random_Popup_InDOM() {
		//popup random có 2 loại:
		//Loại 1: Luôn có trong DOM
		// Loại 2: Hiển thị thì có trong DOM và ngược lại
		driver.get("https://vnk.edu.vn/");
		sleepInSecond(15);
		By popup = By.id("tve_editor");
		//Assert.assertTrue(driver.findElement(popup).isDisplayed());
		//nếu hiển thị thì close rồi qua step tiếp theo
		if(driver.findElement(popup).isDisplayed()) {
			//close popup
			driver.findElement(By.cssSelector(".tcb-icon")).click();
			sleepInSecond(2);
			Assert.assertFalse(driver.findElement(popup).isDisplayed());
		}
		
		driver.findElement(By.xpath("//a[@title='Liên hệ']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='title-content']/h1")).getText(), "Liên hệ");
		
	}
	
	@Test
	public void TC03_Random_Popup_NotInDOM() {
		driver.get("https://dehieu.vn/");
		sleepInSecond(5);
		List<WebElement> popupContent = driver.findElements(By.cssSelector("section#popup"));
		if(popupContent.size() > 0) {
			driver.findElement(By.cssSelector("button.close")).click();
			sleepInSecond(2);
			popupContent = driver.findElements(By.cssSelector("section#popup"));
			Assert.assertEquals(popupContent.size(), 0);
		}
		else {
			System.out.println("popup không hiển thị. show message cho zui thoi :v");
		}
		
		driver.findElement(By.xpath("//a[text()='Đăng nhập']")).click();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='Đăng nhập']")).isDisplayed());
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