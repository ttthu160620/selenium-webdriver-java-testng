package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Xpath_Part_II {
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
		//driver.get("https://www.facebook.com/");
		//driver.get("https://alada.vn/tai-khoan/dang-ky.html");
	}

	@Test
	public void Register_01_Empty_Data() {
		
		//mở app
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		//Click vào Đăng ký button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		//Kiểm tra message lỗi hiển thị ở các field bắt buộc
		driver.findElement(By.id("txtFirstname-error")).getText();
		
		//Kiểm tra 1 điều kiện trả về là bằng với đk mong muốn
		Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Vui lòng nhập lại địa chỉ email");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPassword-error']")).getText(), "Vui lòng nhập mật khẩu");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(), "Vui lòng nhập lại mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
	}

	@Test
	public void Register_02_Invalid_Email() {
		//mở app
		//driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		// Nhập liệu
		driver.findElement(By.id("txtFirstname")).sendKeys("ABC");
		driver.findElement(By.id("txtEmail")).sendKeys("123@123@234");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@123@234");
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456");
		driver.findElement(By.id("txtPhone")).sendKeys("0322222222");
		
		//Click vào Đăng ký button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email hợp lệ");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
	}

	@Test
	public void Register_03_Incorrect_Confirm_Email() {
		//mở app
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		// Nhập liệu
		driver.findElement(By.id("txtFirstname")).sendKeys("ABC");
		driver.findElement(By.id("txtEmail")).sendKeys("123@123");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@123@234");
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123456");
		driver.findElement(By.id("txtPhone")).sendKeys("0322222222");
		
		//Click vào Đăng ký button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		// So sánh kết quả
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
	}
	
	@Test
	public void Register_04_Password_Less_Than_6_Chars(){
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		//Nhập liệu
		driver.findElement(By.id("txtPassword")).sendKeys("123");
		driver.findElement(By.id("txtCPassword")).sendKeys("123");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		//So sánh kết quả
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPassword-error']")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
	}
	
	@Test
	public void Register_05_Incorrect_Confirm_Password() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		
		//Nhập liệu
		driver.findElement(By.id("txtPassword")).sendKeys("123456");
		driver.findElement(By.id("txtCPassword")).sendKeys("123457");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		//So sánh kết quả
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(), "Mật khẩu bạn nhập không khớp");
	}
	
	@Test
	public void Register_06_Incorrect_Phone() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		// sdt <10-11 số và >10-11 số
		driver.findElement(By.id("txtPhone")).sendKeys("0123456");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789999");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại phải từ 10-11 số.");
		
		//sdt không đúng định dạng
		driver.findElement(By.id("txtPhone")).clear();
		driver.findElement(By.id("txtPhone")).sendKeys("12345");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019");
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}