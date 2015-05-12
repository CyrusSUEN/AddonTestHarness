
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

	public static String[] TEST_URLS = {
		"https://www.google.com.hk/search?q=jewelry",
		"http://nytimes.com",
		/*"http://www.ew.com/",
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
		"http://www.niemanlab.org/"*/
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
		ffp.setPreference("extensions.adnauseam@rednoise.org.automated", true);
		return new FirefoxDriver(ffp);
	}

	public Result[] go() {
		List<Result> results = new ArrayList<Result>();
		for (int i = 0; i < TEST_URLS.length; i++) {
			int count = getCount(TEST_URLS[i]);
			results.add(new Result(TEST_URLS[i], count));
			System.out.println("{ count: "+count+", url: "+TEST_URLS[i]+" }");
		}
		return results.toArray(new Result[0]);
	}
	
	public static void main(String[] args) {
		
		AdNauseamPageVisitor apv = new AdNauseamPageVisitor("ADN");
		Result[] results = apv.go();
		String json = new Gson().toJson(results);
		System.out.println("RESULTS:\n"+json);
	}

}