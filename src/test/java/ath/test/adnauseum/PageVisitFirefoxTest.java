package ath.test.adnauseum;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import ath.adnauseum.Websites;

public class PageVisitFirefoxTest {

	private WebDriver driver;
	
	private static String exportJSONAdsURL = "http://rednoise.org/ad-auto-export";

	@Before
	public void createDriver() {
		
		String profileName = "dev";
		
		FirefoxProfile ffp = new ProfilesIni().getProfile(profileName);
		if (ffp == null)
			throw new RuntimeException("Unable to load profile: "+profileName);
		
		ffp.setPreference("extensions.adnauseam2@rednoise.org.automated", true);
		
		driver = new FirefoxDriver(ffp);
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
	}

	@After
	public void quitDriver() {
		driver.quit();
	}
	
	@Test
	public void testAllURLs() throws InterruptedException {
		
		String [][] testURLs = {
				// Websites.textAds, 
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
			
			for (int j = 0; j < testURLs[i].length; j++)
			{
				driver.get(testURLs[i][j]);
				Thread.sleep(5000);
			}
		}
		// finished testing and export collected ads as JSON
		driver.get(exportJSONAdsURL);
	}

	@Test
	public void testTopNews() {
		
		String [] testURLs = Websites.topNews;
		
		for (int i = 0; i < testURLs.length; i++) {
			driver.get(testURLs[i]);
			
			// rest of the test...
		}
	}

	@Test
	public void testTopRecreation() {
		
		String [] testURLs = Websites.topRecreation;
		
		for (int i = 0; i < testURLs.length; i++) {
			driver.get(testURLs[i]);
			
			// rest of the test...
		}
	}

	@Test
	public void testGoogleSearch() {
		driver.get("http://www.google.com");
		// rest of the test...
		// finished testing and export collected ads as JSON
		driver.get(exportJSONAdsURL);
	}

	@Test
	public void testBingSearch() {
		driver.get("http://www.bing.com");
		// rest of the test...
	}

}
