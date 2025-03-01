package SFDC;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		driver.navigate().to("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();	
		
		String productName = "ZARA COAT 3";
		driver.findElement(By.cssSelector("#userEmail")).clear();
		driver.findElement(By.cssSelector("#userEmail")).sendKeys("anuj1234567@gmail.com");
		driver.findElement(By.cssSelector("#userPassword")).clear();
		driver.findElement(By.cssSelector("#userPassword")).sendKeys("P@ssw0rd");
		driver.findElement(By.cssSelector("#login")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".mb-3"))));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product ->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".btn.w-10.rounded")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#toast-container"))));
	//	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ngx-spinner-overlay")));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@routerlink='/dashboard/cart']")));
		WebElement cartbutton=	driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']"));
		cartbutton.click();
	/*	JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click", cartbutton); */
		
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cart li"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		//Assert.assertTrue(match);
		driver.findElement(By.cssSelector("li[class='items even ng-star-inserted'] button[class='btn btn-primary']")).click();
		
		Actions action = new Actions(driver);
		action.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "India").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='ta-item list-group-item ng-star-inserted'])[2]")));
		driver.findElement(By.xpath("(//button[@class='ta-item list-group-item ng-star-inserted'])[2]")).click();
		
		driver.findElement(By.xpath("//a[contains(text(),\"Place Order \")]")).click();
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
		driver.close();
	}

}
