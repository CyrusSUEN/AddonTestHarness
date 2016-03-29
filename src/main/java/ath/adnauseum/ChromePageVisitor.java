package ath.adnauseum;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import ath.adnauseum.util.OSValidator;

public class ChromePageVisitor {

	public static void main(String[] args) throws InterruptedException {
		
		String path = "driver/" + OSValidator.getOS() + "/chromedriver";
		File driverFile = new java.io.File(path);
		if (!driverFile.exists())
			throw new RuntimeException("Unable to load driver: "+driverFile);
		
		System.setProperty("webdriver.chrome.driver", driverFile.getAbsolutePath());
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.google.com/ncr");

		driver.findElement(By.id("lst-ib")).sendKeys("AdNauseam");

		Thread.sleep(1500);
		System.out.println(driver.getTitle());
		System.out.println(driver.findElement(By.id("resultStats")).getText());
		driver.close();

	}

}
