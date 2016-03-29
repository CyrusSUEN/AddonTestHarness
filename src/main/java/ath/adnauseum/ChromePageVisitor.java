package ath.adnauseum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import ath.adnauseum.util.OSValidator;

public class ChromePageVisitor {

	public static void main(String[] args) throws InterruptedException {
		String path = "driver/" + OSValidator.getOS() + "/chromedriver";
		System.setProperty("webdriver.chrome.driver", 
				new java.io.File(path).getAbsolutePath());
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.google.com/ncr");

		driver.findElement(By.id("lst-ib")).sendKeys("AdNauseam");

		Thread.sleep(1500);
		System.out.println(driver.getTitle());
		System.out.println(driver.findElement(By.id("resultStats")).getText());
		driver.close();

	}

}
