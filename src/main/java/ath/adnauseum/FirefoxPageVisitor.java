package ath.adnauseum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxPageVisitor {

	public static void main(String[] args) throws InterruptedException {		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("https://www.google.com");
		
		// Thread.sleep(2000);

		driver.findElement(By.id("lst-ib")).sendKeys("AdNauseam");
		driver.findElement(By.name("btnK")).click();

		Thread.sleep(1500);
		System.out.println(driver.getTitle());
		System.out.println(driver.findElement(By.id("resultStats")).getText());
		
		driver.close();

	}

}
