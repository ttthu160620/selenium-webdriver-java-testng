package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Iframe_Frame {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Select select;
	Actions action;
	
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
	
	//Frame: Cùng domain
	//Iframe: Khác domain
		
	//@Test
	public void TC01_Iframe() {
		//A
		driver.get("https://kyna.vn/");
		
		//swich vào frame/iframe rồi mới thao tác lên element
		//driver.switchTo().frame(0); // theo index
		// A -> B
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.face-content iframe")));
		
		//verify fanpage có 166k like
		Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::div/following-sibling::div")).getText(), "166K likes");
		
		//B -> A
		driver.switchTo().defaultContent();
		//iframe chat: A -> C
		driver.switchTo().frame("cs_chat_iframe");
		
		driver.findElement(By.cssSelector(".meshim_widget_Widget")).click();
		
		
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("abc");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0123123123");
		select = new Select(driver.findElement(By.id("serviceSelect")));
		select.selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys("automationfc test");
		
		//nhập vào search
		driver.switchTo().defaultContent();
		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='menu-heading']")).getText(),
				"10 Kết quả tìm kiếm từ khóa 'Excel'");
		
	}
	
	@Test
	public void TC_02_Frame() {
		//A
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		//A-> B
		 driver.switchTo().frame("login_page");
		 driver.findElement(By.name("fldLoginUserId")).sendKeys("123456");
		 driver.findElement(By.cssSelector(".login-btn")).click();
		 
		 //B
		 Assert.assertTrue(driver.findElement(By.id("fldPasswordDispId")).isDisplayed());
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