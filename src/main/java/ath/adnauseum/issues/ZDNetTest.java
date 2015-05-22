package ath.adnauseum.issues;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ZDNetTest {
	
	public static void main(String[] args) {

		WebDriver driver = new FirefoxDriver();

		driver.get("http://www.zdnet.com/");
		driver.switchTo().activeElement();

		WebElement we = new WebDriverWait(driver, 10)
				.until(ExpectedConditions.presenceOfElementLocated(By.id("yad-widget-2")));
		
		System.out.println(we.getText());

		driver.quit();
	}
}
