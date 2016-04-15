package ath.test.adnauseum;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import ath.adnauseum.Websites;

public class PageVisitFirefoxTest {

	private WebDriver driver;
	private static String exportJSONAdsURL = "http://rednoise.org/ad-auto-export";

	@Before
	public void createDriver() {

		String profileName = "cfxo";

		FirefoxProfile ffp = new ProfilesIni().getProfile(profileName);
		if (ffp == null)
			throw new RuntimeException("Unable to load profile: " + profileName);

		ffp.setPreference("extensions.adnauseam2@rednoise.org.automated", true);
		
		driver = new FirefoxDriver(ffp);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void quitDriver() {
		driver.quit();
	}
	
	@Test
	public void testAllURLs() {
		
		String [][] testURLs = {
				Websites.textAds, 
				Websites.topBlogs,
				Websites.topBusiness,
				Websites.topCars,
				Websites.topDomain,
				Websites.topGadgets,
				Websites.topMedia,
				Websites.topNews,
				Websites.topRecreation,
				Websites.topSports
		};

		for (int i = 0; i < testURLs.length; i++) {

			for (int j = 0; j < testURLs[i].length; j++) {
				driver.get(testURLs[i][j]);

				(new WebDriverWait(driver, 10)).until(new ExpectedCondition<List<WebElement>>() {
					public List<WebElement> apply(WebDriver d) {
						return d.findElements(By.tagName("body"));
					}
				});
			}
		}
		// finished testing and export collected ads as JSON
		driver.get(exportJSONAdsURL);
	}
}
