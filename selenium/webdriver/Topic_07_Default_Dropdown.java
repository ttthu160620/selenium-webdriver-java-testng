package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Default_Dropdown {
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
	public void TC01_Rode() {
		driver.get("https://rode.com/en/support/where-to-buy");
		
		// khởi tạo select để thao tác với element (country dropdown)
		select = new Select(driver.findElement(By.id("country")));
		
		// Khong support mutiple select
		Assert.assertFalse(select.isMultiple());
		
		//Select gia tri Vietnam
		select.selectByVisibleText("Vietnam");
		sleepInSecond(2);
		
		//Verify Vietnam selected success
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");
		
		//Click Search
		driver.findElement(By.xpath("//button[text()='Search']")).click();
		sleepInSecond(2);
		
		//Verify result
		List<WebElement> storeName = driver.findElements(By.xpath("//div[@title='click to show on map']//h4"));
		
		//Verify tổng số lượng store name
		Assert.assertEquals(storeName.size(), 37);
		
		for(WebElement store : storeName) {
			System.out.println(store.getText());
		}
	}
	

	@Test
	public void TC02_NopCommerce() {
		String firstName = "Thu";
		String lastName = "TT";
		String email = "thutt" + getRandomNumber() + "@gmail.com";
		String passWord = "123456789";
		String date = "10";
		String month = "June";
		String year = "2000";
		
		driver.get("https://demo.nopcommerce.com");
		
		driver.findElement(By.xpath("//a[@class='ico-register']")).click();
		
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);
		
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		List<WebElement> listDate = driver.findElements(By.xpath("//select[@name='DateOfBirthDay']/option"));
		Assert.assertEquals(listDate.size(), 32);
		select.selectByVisibleText(date);
		
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		List<WebElement> listMonth = driver.findElements(By.xpath("//select[@name='DateOfBirthMonth']/option"));
		Assert.assertEquals(listMonth.size(), 13);
		
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		List<WebElement> listYear = driver.findElements(By.xpath("//select[@name='DateOfBirthYear']/option"));
		Assert.assertEquals(listYear.size(), 112);
		
		driver.findElement(By.id("Email")).sendKeys(email);
		//driver.findElement(By.id("Company")).sendKeys();
		driver.findElement(By.id("Password")).sendKeys(passWord);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(passWord);
		driver.findElement(By.id("register-button")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='result']")).getText(), "Your registration completed");
		
		driver.findElement(By.xpath("//a[@class='ico-account']")).click();
		
		//Page HTML render lại
		//Verify
		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), lastName);
		
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);
		
		Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"), email);
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