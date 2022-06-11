package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_JavascriptExecutor {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	
	
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass //Chạy trước cho tc đầu tiên - Pre-Condition
	public void beforeClass() {
		//mở browser
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		
		jsExecutor = (JavascriptExecutor) driver;
		
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDriver\\geckodriver.exe");
//		driver = new FirefoxDriver();
		
		//Hàm này sẽ áp dụng cho việc tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//phóng to browser
		driver.manage().window().maximize();
	}
	
	
		
	//@Test
	public void TC01_Live_Guru() {
		//mở link
		navigateToUrlByJS("http://live.techpanda.org");
		sleepInSecond(5);
		//lấy domain của page
		String homePageDomain = (String) executeForBrowser("return document.domain");
		Assert.assertEquals(homePageDomain, "live.techpanda.org");
		
		//lấy url
		String homePageURL = (String) executeForBrowser("return document.URL");
		Assert.assertEquals(homePageURL, "http://live.techpanda.org/");
		
		//Open Mobile
		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(3); 
		clickToElementByJS("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div/button");
		sleepInSecond(3); 
		
		String shoppingCartText = getInnerText();
		Assert.assertTrue(shoppingCartText.contains("Samsung Galaxy was added to your shopping cart."));
		
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(5);
		scrollToElementOnTop("//input[@type='email']");
		sleepInSecond(2);
		sendkeyToElementByJS("//input[@type='email']", "abc2gmail.com");
		clickToElementByJS("//span[text()='Subscribe']");
		sleepInSecond(3);
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription."));
		
	}
	
	@Test
	public void TC02_Html5_Validation_Message() {
		driver.get("https://www.pexels.com/vi-vn/join-contributor/");
		By firstName = By.id("user_first_name");
		By email = By.id("user_email");
		By createButton = By.xpath("//button[contains(text(),'Tạo tài khoản mới')]");
		
		scrollToElementOnDown("//button[contains(text(),'Tạo tài khoản mới')]");
		driver.findElement(createButton).click();
		sleepInSecond(2);
		Assert.assertEquals(getElementValidationMessage(firstName),"Please fill out this field.");
		
		driver.findElement(firstName).sendKeys("abctest");
		driver.findElement(createButton).click();
		sleepInSecond(2);
		Assert.assertEquals(getElementValidationMessage(email),"Please fill out this field.");
		
		driver.findElement(email).sendKeys("1123@@@@");
		sleepInSecond(2);
		Assert.assertEquals(getElementValidationMessage(email),"A part following '@' should not contain the symbol '@'.");
		
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
	
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(By byLocator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", driver.findElement(byLocator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
		if (status) {
			return true;
		}
		return false;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
}