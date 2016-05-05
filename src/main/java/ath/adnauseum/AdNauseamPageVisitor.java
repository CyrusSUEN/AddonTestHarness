
package ath.adnauseum;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;

public class AdNauseamPageVisitor {

	private static boolean SILENT = false;
	
	public int pageWaitSec = 10;
	public String profileName = "NewADN";
	public boolean pauseOnFail, pauseOnSuccess;
	WebDriver driver = null;
	
	public AdNauseamPageVisitor() {
		
		this(null);
	}
	 
	public AdNauseamPageVisitor(String ffProfileName) {
		if (ffProfileName != null)
			this.profileName = ffProfileName;
	}

	public int getCount(String url) {
		
		int count = 0;
		long ts = -1;

		try {
			
			if (driver == null) {
				driver = createDriver();
			}
			
			driver.manage().timeouts().pageLoadTimeout(pageWaitSec, TimeUnit.SECONDS);
			
			ts = System.currentTimeMillis();
			System.out.println("Waiting...");
			
			driver.get(url);
			driver.switchTo().activeElement();
			
			WebElement we = new WebDriverWait(driver, pageWaitSec).until
					(ExpectedConditions.presenceOfElementLocated(By.id("adnauseam-count")));
			
			count = Integer.parseInt(we.getAttribute("count"));
			
		} catch (org.openqa.selenium.TimeoutException e) {
				
			System.err.println("[WARN] No count (timeout) for url: "+url);
			checkForPause(e);
		
		} catch (Throwable t) {
			
			System.err.println("[WARN] No count for url: "+url+"\nError: "+t.getMessage());
			checkForPause(t);
		}			

		if (pauseOnSuccess) {
			
			System.err.println("Pausing: count was "+count);
			pauseForever();
		}
		
		//driver.quit();
		System.out.println("DONE in "+(System.currentTimeMillis()-ts)/1000+"s");

		return count;
	}

	private void checkForPause(Throwable e) {
		
		if (pauseOnFail) {
			System.err.println("Pausing on failure:\n"+e.getMessage());
			pauseForever();
		}
	}

	private void pauseForever() {
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} 
		catch (InterruptedException x) {}
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
		// ffp.setPreference("webdriver.load.strategy", "unstable"); // wrong counts
		WebDriver driver = new FirefoxDriver(ffp);
		return driver;
	}

	public String go() {
		
		return this.go(UrlLists.TOPS);
	}

	public String go(String url) {
		return this.go(new String[]{ url });
	}
		
	public String go(String[] urls) {
		
		if (!SILENT) System.out.println("Loading "+urls.length+" URL(s)");

		List<Result> results = new ArrayList<Result>();
		
		for (int i = 0; i < urls.length; i++) {
			
			int count = getCount(urls[i]);
			results.add(new Result(urls[i], count));
			
			if (!SILENT) System.out.println("count: "+count+", url: "+urls[i]);
		}
		
		return toJSON(results);
	}

	public String toJSON(List<Result> results) {
		
		return new Gson().toJson(results);
	}
	
	public static void main(String[] args) {
		
		AdNauseamPageVisitor apv = new AdNauseamPageVisitor("automated");
		List<String> asList = Arrays.asList(UrlLists.TOPS);
		Collections.shuffle(asList);
		String[] shuffled = asList.toArray(new String[0]);
		String jsonResult = apv.go(shuffled);//"http://mashable.com");
		System.out.println("\nRESULTS:\n"+jsonResult);

	}

}
