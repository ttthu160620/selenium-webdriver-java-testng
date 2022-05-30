package webdriver;

import static org.testng.Assert.assertFalse;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_06_Textbox_TextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}

	@BeforeMethod //chạy lặp lại theo từng testcase
	public void beforeMethod() {
		driver.get("https://opensource-demo.orangehrmlive.com");
	}
	
	
	@Test
	public void TC01_Add_Employee() {
		
		//Textbox
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		
		driver.findElement(By.id("btnLogin")).click();
		sleepInSecond(5);
		
		//At Dashboard page: Add Employee sub-menu link is not displayed
		Assert.assertFalse(driver.findElement(By.cssSelector("a#menu_pim_addEmployee")).isDisplayed());
		
		//Open Add Employee page
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");
		
		//At Dashboard page: Add Employee sub-menu link is displayed
		Assert.assertTrue(driver.findElement(By.cssSelector("a#menu_pim_addEmployee")).isDisplayed());
		
		// Enter to FirstName/LastName
		driver.findElement(By.id("firstName")).sendKeys("Luis");
		driver.findElement(By.id("lastName")).sendKeys("Suarez");
		
		String employeeID = driver.findElement(By.id("employeeId")).getAttribute("value");
		
		driver.findElement(By.id("btnSave")).click();
		
		//verify enable
		Assert.assertFalse(driver.findElement(By.id("personal_txtEmpFirstName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.id("personal_txtEmpLastName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.id("personal_txtEmployeeId")).isEnabled());
		
		//Verify thông tin
		Assert.assertEquals(driver.findElement(By.id("personal_txtEmpFirstName")).getAttribute("value"),"Luis");
		Assert.assertEquals(driver.findElement(By.id("personal_txtEmpLastName")).getAttribute("value"),"Suarez");
		Assert.assertEquals(driver.findElement(By.id("personal_txtEmployeeId")).getAttribute("value"),employeeID);
		
		// Edit
		driver.findElement(By.cssSelector("input#btnSave")).click();
		
		Assert.assertTrue(driver.findElement(By.id("personal_txtEmpFirstName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("personal_txtEmpLastName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("personal_txtEmployeeId")).isEnabled());
		
		driver.findElement(By.id("personal_txtEmpFirstName")).clear();
		driver.findElement(By.id("personal_txtEmpLastName")).clear();
		
		driver.findElement(By.id("personal_txtEmpFirstName")).sendKeys("Thu");
		driver.findElement(By.id("personal_txtEmpLastName")).sendKeys("Tran");
		
		driver.findElement(By.id("btnSave")).click();
		
		//verify enable
		Assert.assertFalse(driver.findElement(By.id("personal_txtEmpFirstName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.id("personal_txtEmpLastName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.id("personal_txtEmployeeId")).isEnabled());
		
		//Verify thông tin
		Assert.assertEquals(driver.findElement(By.id("personal_txtEmpFirstName")).getAttribute("value"),"Thu");
		Assert.assertEquals(driver.findElement(By.id("personal_txtEmpLastName")).getAttribute("value"),"Tran");
		Assert.assertEquals(driver.findElement(By.id("personal_txtEmployeeId")).getAttribute("value"),employeeID);

		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		driver.findElement(By.cssSelector("input#btnAdd")).click();
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