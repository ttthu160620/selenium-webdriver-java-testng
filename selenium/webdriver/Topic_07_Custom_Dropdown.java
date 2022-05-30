package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Custom_Dropdown {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
//		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
//		driver = new ChromeDriver();
		
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		//Wait cho các trạng thái của element: visible/ presence/ invisible/ staleness...
		explicitWait = new WebDriverWait(driver, 10);
		
		//Ép kiểu tường minh
		jsExecutor = (JavascriptExecutor) driver;
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	//@Test
	public void TC01_JQuery_01() {	
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdownList("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "19");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(), "19");
		
		selectItemInCustomDropdownList("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "5");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(), "5");
	}	
	
	//@Test
	public void TC02_JQuery_02() {	
		
	}	
	
	//@Test
	public void TC03_ReactJs() {	
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInCustomDropdownList("#root div[role='listbox']", "div.item span.text", "Christian");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Christian");
		
		selectItemInCustomDropdownList("#root div[role='listbox']", "div.item span.text", "Jenny Hess");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Jenny Hess");
	}	
	
	//@Test
	public void TC04_VueJS() {	
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdownList("li.dropdown-toggle", "ul.dropdown-menu>li>a", "First Option");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.btn-group>li")).getText(), "First Option");
	}	
	
	@Test
	public void TC05_Angular() {	
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		selectItemInCustomDropdownList("ng-select[bindvalue='provinceCode'] span.ng-arrow-wrapper", "div[role='option']>span.ng-option-label", "Tỉnh Thừa Thiên Huế");
		//get text
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='provinceCode'] span[class='ng-value-label ng-star-inserted']")).getText(), "Tỉnh Thừa Thiên Huế");
		
		selectItemInCustomDropdownList("ng-select[bindvalue='districtCode'] span.ng-arrow-wrapper", "div[role='option']>span.ng-option-label", "Huyện Phong Điền");
		//Dùng trip nếu text k nằm ở html
		String actualText = (String) jsExecutor.executeScript("return document.querySelector(\"ng-select[bindvalue='districtCode'] div.ng-value\").innerText");
		Assert.assertEquals(actualText, "Huyện Phong Điền");
		
		selectItemInCustomDropdownList("ng-select[bindvalue='wardCode'] span.ng-arrow-wrapper", "div[role='option']>span.ng-option-label", "Xã Phong Xuân");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='wardCode'] span.ng-value-label")).getAttribute("innerText"), "Xã Phong Xuân");
	}	
	
	public void selectItemInCustomDropdownList(String parentLocator, String childLocator, String expectedTexItem) {
		//Step 1: Click vào dropdown
		driver.findElement(By.cssSelector(parentLocator)).click();
		sleepInSecond(2);
		
		//Step2: Chờ cho các item load hết ra
		//Lưu ý 1: Locator chứa hết all items
		//Lưu ý 2: Locator phải đến last node chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		
		//Step3: Tìm item cần chọn
		//Lấy hết all items rồi duyệt
		List<WebElement> listItem = driver.findElements(By.cssSelector(childLocator));
		
		//Duyệt qua từng item rồi getText
		for(WebElement items : listItem) {
			String actualText = items.getText();
			System.out.println("Actual Text = " + actualText);		
			
			if(actualText.equals(expectedTexItem)) {
				//b1: Nếu item cần chọn nằm trong vùng nhìn thấy thì không cần scroll
				//b2: Nếu item cần chọn không nằm trong vùng nhìn thấy thì cần scroll tới element
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", items);
				sleepInSecond(4);
				//Step 4: Click item muốn chọn
				items.click();
				//thoát khỏi vòng lặp không có kiểm tra element tiếp theo nữa
				break;
			}
		}
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