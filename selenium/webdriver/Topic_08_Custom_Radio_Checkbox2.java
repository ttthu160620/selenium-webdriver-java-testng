package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_Radio_Checkbox2 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	Select select;
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Actions action;
	
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
//		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
//		driver = new ChromeDriver();
		
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		// Khởi tạo sau khi driver này được sinh ra
		//JsExecutor/ WebDriverWait/ Actions/...
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 10);
		action = new Actions(driver);
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	//Custom: Thẻ input không thao tác được
	//@Test
	public void TC01_Custom_Radio() {
		driver.get("https://material.angular.io/components/radio/examples");
		
		//Case 1:  selenium click -> không click input được vì bị che
		//driver.findElement(By.xpath("//input[@value='Summer']")).click();
		
		//Case 2: Click thẻ span - verify thẻ input
		//1 element phải define 2 locator -> dễ bị nhầm lẫn, bảo trì phải code nhìu
//		WebElement summerRadio = driver.findElement(By.xpath("//input[@value='Summer']/preceding-sibling::span[@class='mat-radio-outer-circle']"));
//		summerRadio.click();
		
		//Case 3: Dùng js để click
		
		jsExecutor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//input[@value='Summer']")));
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Summer']")).isSelected());
	}
	
	//@Test
	public void TC02_Custom_Checkbox() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		
		// Checkbox Checked
		By checkedCheckbox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click()", driver.findElement(checkedCheckbox));
		sleepInSecond(1);
		
		//Checkbox Indeterminate
		By indeterminate = By.xpath("//span[text()='Indeterminate']/preceding-sibling::span/input");
		clickByJS(indeterminate);
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());
		Assert.assertTrue(driver.findElement(indeterminate).isSelected());
		
	}
	
	@Test
	public void TC03_googledoc() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		driver.findElement(By.xpath("//span[text()='Cần Thơ']")).click();
		sleepInSecond(2);
		//Verify
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Cần Thơ']")).isDisplayed());
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@data-value='Cần Thơ']")).getAttribute("aria-checked"), "true");
		
	}
		
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void clickByJS(By by) {
		jsExecutor.executeScript("arguments[0].click()", driver.findElement(by));
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