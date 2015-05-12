
package ath.adnauseum;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;

public class AdNauseamPageVisitor {

	private static boolean SILENT = false;

	public static String[] TEST_URLS = {
		"https://www.google.com.hk/search?q=jewelry",
		"http://nytimes.com",
		"http://www.ew.com/",
		"http://www.hollywoodreporter.com/",
		"http://variety.com/",
		"http://deadline.com/",
		"http://www.adweek.com/",
		"http://www.thewrap.com/",
		"http://www.mediaite.com/",
		"http://www.mediabistro.com/",
		"http://www.vulture.com/",
		"http://adage.com/",
		"http://www.poynter.org/",
		"http://www.thedrum.com/",
		"http://www.imediaconnection.com/",
		"http://www.niemanlab.org/"
	};
	
	public String profileName = "ADN";
	public int pageWaitSec = 10;
	public boolean pauseOnFail = false;

	public AdNauseamPageVisitor() {
		
		this(null);
	}
	 
	public AdNauseamPageVisitor(String ffProfileName) {
		if (ffProfileName != null)
			this.profileName = ffProfileName;
	}

	public int getCount(String url) {
		
		int count = 0;
		
		WebDriver driver = createDriver();
		WebDriverWait wait = new WebDriverWait(driver, pageWaitSec);
		driver.get(url);

		try {
			
			WebElement we = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("adnauseam-count")));
			
			count = Integer.parseInt(we.getAttribute("count"));
			
		} catch (org.openqa.selenium.TimeoutException e) {
				
			System.err.println("No count for url: "+url);
			if (pauseOnFail) {
				try {
					Thread.sleep(Integer.MAX_VALUE);
				} 
				catch (InterruptedException x) {}
			}
		}			

		driver.quit();
		
		return count;
	}

	class Result {
		public Result(String url, int count) {
			this.url = url;
			this.count = count;
		}
		int count;
		String url;
	}
	
	protected WebDriver createDriver() {
		
		FirefoxProfile ffp = new ProfilesIni().getProfile(profileName);
		if (ffp == null)
			throw new RuntimeException("Unable to load profile: "+profileName);
		ffp.setPreference("extensions.adnauseam@rednoise.org.automated", true);
		return new FirefoxDriver(ffp);
	}

	public String go() {
		
		if (!SILENT) System.out.println("Loading "+TEST_URLS.length+" URLs");

		List<Result> results = new ArrayList<Result>();
		
		for (int i = 0; i < TEST_URLS.length; i++) {
			int count = getCount(TEST_URLS[i]);
			results.add(new Result(TEST_URLS[i], count));
			
			if (!SILENT) System.out.println("count: "+count+", url: "+TEST_URLS[i]);
		}
		
		return new Gson().toJson(results);
	}
	
	public static void main(String[] args) {
		
		String profName = null;
		AdNauseamPageVisitor apv = null;
		if (args != null && args.length > 0)
			profName = args[0];
		apv = new AdNauseamPageVisitor(profName);
		String jsonResult = apv.go();
		System.out.println("RESULTS:\n"+jsonResult);
	}

}
