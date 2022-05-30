package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

public class Topic_08_Default_Radio_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	Select select;
	JavascriptExecutor jsExecutor;
	WebDriverWait expliciWait;
	Actions action;
	
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
		action = new Actions(driver);
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	
	@Test
	public void TC01_Button() {
		String email = "abc" + getRandomNumber() + "@gmail.com";
		String password = "123456";
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.cssSelector(".popup-login-tab-login")).click();
		By btnLogin = By.cssSelector(".fhs-btn-login");
		//verify btn là disable
		Assert.assertFalse(driver.findElement(btnLogin).isEnabled());
		
		driver.findElement(By.cssSelector("input#login_username")).sendKeys(email);
		driver.findElement(By.cssSelector("input#login_password")).sendKeys(password);
		
		Assert.assertTrue(driver.findElement(btnLogin).isEnabled());
		String loginBackgroundColor = driver.findElement(btnLogin).getCssValue("background-color");
		System.out.println(loginBackgroundColor);
		
		//verify = rgb
		//Assert.assertEquals(loginBackgroundColor, "rgb(201, 33, 39)");
		
		//verify = hexa
		Color.fromString(loginBackgroundColor).asHex();
		Assert.assertEquals(Color.fromString(loginBackgroundColor).asHex().toUpperCase(), "#C92127");
		
		driver.navigate().refresh();
		driver.findElement(By.cssSelector(".popup-login-tab-login")).click();
		//remove disabled attribute
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled');", driver.findElement(btnLogin));
		sleepInSecond(2);
		driver.findElement(btnLogin).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Số điện thoại/Email']/following-sibling::div[@class='fhs-input-alert']")), 
				"Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Mật khẩu']/following-sibling::div[@class='fhs-input-alert']")), 
				"Thông tin này không thể để trống");
		
	}
	
	//@Test
	public void TC02_Default_Radio() {
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		//Default - the input: action là click và verify được 
		//Custom - the input: action không click được và verify được
		WebElement twoPetrol = driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input"));
		//nếu chưa chọn thì click
		if(!twoPetrol.isSelected()) {
			twoPetrol.click();
			Assert.assertTrue(twoPetrol.isSelected());
		}
		else {
			Assert.assertTrue(twoPetrol.isSelected());
		}
	}
	
	//@Test
	public void TC03_Default_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		WebElement dualCheckbox = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dualCheckbox);
		//nếu chưa chọn thì chọn
		if(!dualCheckbox.isSelected()) {
			//dualCheckbox.click();
			jsExecutor.executeScript("arguments[0].click()", dualCheckbox);
			Assert.assertTrue(dualCheckbox.isSelected());
		}
		// nếu chọn rồi thì bỏ chọn sau đó chọn lại
		else {
			Assert.assertTrue(dualCheckbox.isSelected());
		}
		
		//bỏ chọn
		//dualCheckbox.click();
		jsExecutor.executeScript("arguments[0].click()", dualCheckbox);
		Assert.assertFalse(dualCheckbox.isSelected());
		
	}
	
	@Test
	public void TC04_Multiple_Checkbox() {
		
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