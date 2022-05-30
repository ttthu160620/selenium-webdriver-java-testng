package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Action {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	Actions action;
	Alert alert;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
//		driver = new FirefoxDriver();
		
		action = new Actions(driver);
		explicitWait = new WebDriverWait(driver, 10);
		jsExecutor = (JavascriptExecutor) driver;
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	
	//@Test
	public void TC01_Hover_Element_Tooltip() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		// hover chuột
		action.moveToElement(driver.findElement(By.id("age"))).perform();
		sleepInSecond(2);
		//Verify tooltip
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ui-tooltip-content']")).getText(), 
				"We ask for your age only for statistical purposes.");
	}
	
	//@Test
	public void TC02_Hover_Element() {
		driver.get("https://www.myntra.com");
		action.moveToElement(driver.findElement(By.xpath("//header//a[text()='Kids']"))).perform();
		sleepInSecond(2);
		// hover tới elemt rồi mới click, click của element thì k có hover vào
		action.click(driver.findElement(By.xpath("//a[text()='Home & Bath']"))).perform();
		
		Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Kids Home Bath");
	}
	
	//@Test
	public void TC03_Click_Hold_Element() {
	}
	
	//@Test
	public void TC04_Click_Select_Element() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		//Khai báo và lưu trữ 12 elements
		List<WebElement> listNumbers = driver.findElements(By.cssSelector(".ui-selectable>li"));
		
		// chọn từ 1-4: click and hold -> hover tới 4-> nhả chuột trái ra
		
		action.clickAndHold(listNumbers.get(0)).moveToElement(listNumbers.get(3)).release().perform();
		sleepInSecond(5);
		
		List<WebElement> listSelected = driver.findElements(By.cssSelector("ol>li.ui-selected"));
		Assert.assertEquals(listSelected.size(), 4);
		
		//Chọn random: Nhấn giữ ctrl + click
		Keys control;
		if(osName.contains("Windows") || osName.contains("nux")) {
			control = Keys.CONTROL;
		}else {
			control = Keys.COMMAND;
		}
		action.keyDown(control).click(listNumbers.get(1)).click(listNumbers.get(5)).perform();
		action.keyUp(control).perform();
		sleepInSecond(7);
		Assert.assertEquals(listSelected.size(), 2);
	}
	
	//@Test
	public void TC05_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		action.doubleClick(driver.findElement(By.xpath("//button[@ondblclick='doubleClickMe()']"))).perform();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello Automation Guys!");
	}
	
	//@Test
	public void TC06_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).moveToElement(driver.findElement(By.xpath("//span[text()='Quit']"))).perform();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-hover.context-menu-visible")).isDisplayed());
		action.contextClick(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(2);
		explicitWait.until(ExpectedConditions.alertIsPresent()).accept();
		//alert.accept();
		//Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-hover.context-menu-visible")).isDisplayed());
	}
	
	//@Test
	public void TC07_Drag_Drop_HTML4() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		action.dragAndDrop(driver.findElement(By.cssSelector("div#draggable")), driver.findElement(By.cssSelector("div#droptarget"))).perform();
		Assert.assertEquals(driver.findElement(By.cssSelector("div#droptarget")).getText(), "You did great!");
		
		//background
		String bigCircleBG = driver.findElement(By.cssSelector("div#draggable")).getCssValue("background-color");
		bigCircleBG = Color.fromString(bigCircleBG).asHex().toLowerCase();
		Assert.assertEquals(bigCircleBG, "#03a9f4");
	}
	
	//@Test
	public void TC08_Drag_Drop_HTML5_Css() throws IOException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		
		String sourceACss = "#column-a";
		String targetBCss = "#column-b";
		
		//Lấy được toàn bộ nội dung trong file
		String dragDropHelperContent = getContentFile(projectPath + "\\dragAndDrop\\dragDropJS");
		dragDropHelperContent = dragDropHelperContent + "$(\"" + sourceACss + "\").simulateDragDrop({ dropTarget:\"" + targetBCss + "\"});";
		jsExecutor.executeScript(dragDropHelperContent);
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='column-a']/header")).getText(), "B");
	}
	
	@Test
	public void TC09_Drag_Drop_HTML5_Xpath() throws AWTException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		
		dragAndDropHTML5ByXpath("//div[@id='column-a']", "//div[@id='column-b']");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='column-a']/header")).getText(), "B");
	}
		
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
	
	public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
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