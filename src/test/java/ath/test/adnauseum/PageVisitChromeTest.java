package ath.test.adnauseum;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import ath.adnauseum.Websites;
import ath.adnauseum.util.OSValidator;

public class PageVisitChromeTest {
	
	// Assuming the path of AdNauseam2 repo is next to ADN2AutomatedTest's
	// Change the following path if needed
	private static String extensionFilePath = 
			System.getProperty("user.dir") + "/../AdNauseam2/bin/build/adnauseam.chromium.crx";
	
	private static String exportJSONAdsURL = "http://rednoise.org/ad-auto-export";

	private static ChromeDriverService service;
	private WebDriver driver;

	@BeforeClass
	public static void createAndStartService() throws IOException {
		String path = "driver/" + OSValidator.getOS() + "/chromedriver";

		service = new ChromeDriverService.Builder().usingDriverExecutable(new java.io.File(path))
				.usingAnyFreePort().build();
		service.start();
	}

	@AfterClass
	public static void createAndStopService() {
		service.stop();
	}

	@Before
	public void createDriver() {
		
		ChromeOptions options = new ChromeOptions();
		try {
			options.addExtensions(new java.io.File(extensionFilePath));
		} catch (Exception e) {
			throw new RuntimeException("Unable to load: "+extensionFilePath);
		}
		
		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("extensions.adnauseam@rednoise.org.automated", true);
		options.setExperimentalOption("prefs", prefs);
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		driver = new RemoteWebDriver(service.getUrl(), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void quitDriver() {
		driver.quit();
	}

	@Test
	public void testAllURLs() {
		
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
		// finished testing and export collected ads as JSON
		driver.get(exportJSONAdsURL);
	}

	@Test
	public void testTopRecreation() {
		
		String [] testURLs = Websites.topRecreation;
		
		for (int i = 0; i < testURLs.length; i++) {
			driver.get(testURLs[i]);
			
			// rest of the test...
		}
		// finished testing and export collected ads as JSON
		driver.get(exportJSONAdsURL);
	}
}
