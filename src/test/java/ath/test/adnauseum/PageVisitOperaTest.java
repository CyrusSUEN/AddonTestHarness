package ath.test.adnauseum;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import ath.adnauseum.Websites;
import ath.adnauseum.util.OSValidator;

public class PageVisitOperaTest {
	
	// Assuming the path of AdNauseam2 repo is next to ADN2AutomatedTest's
	// Change the following path if needed
	private static String extensionFilePath = 
			System.getProperty("user.dir") + "/../AdNauseam2/bin/build/adnauseam.opera.nex";	

	private static OperaDriverService service;
	private WebDriver driver;

	@BeforeClass
	public static void createAndStartService() throws IOException {
		String path = "driver/" + OSValidator.getOS() + "/operadriver";

		service = new OperaDriverService.Builder().usingDriverExecutable(new java.io.File(path))
				.usingAnyFreePort().build();
		service.start();
	}

	@AfterClass
	public static void createAndStopService() {
		service.stop();
	}

	@Before
	public void createDriver() {
		OperaOptions options = new OperaOptions();
		options.addExtensions(new java.io.File(extensionFilePath));
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(OperaOptions.CAPABILITY, options);
		
		driver = new RemoteWebDriver(service.getUrl(), capabilities);
	}

	@After
	public void quitDriver() {
		driver.quit();
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
	}

	@Test
	public void testBingSearch() {
		driver.get("http://www.bing.com");
		// rest of the test...
	}

}
