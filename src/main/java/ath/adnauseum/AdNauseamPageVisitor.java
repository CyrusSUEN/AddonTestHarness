
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

		// top 15 media
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
		"http://www.niemanlab.org/",
		
		// top 15 business
		"http://www.cnet.com/",
		"http://www.mashable.com/",
		"http://www.techcrunch.com/",
		"http://www.wired.com/",
		"http://www.zdnet.com/",
		"http://www.webpronews.com/",
		"http://www.arstechnica.com/",
		"http://www.venturebeat.com/",
		"http://www.thenextweb.com/",
		"http://www.gigaom.com/",
		"http://www.informationweek.com/",
		"http://www.sitepoint.com/",
		"http://www.readwrite.com/",
		"http://www.searchengineland.com/",
		"http://forums.digitalpoint.com/",
		
		// top 15 sports
		"http://www.sports.yahoo.com/",
		"http://www.espn.com/",
		"http://www.bleacherreport.com/",
		"http://www.cbssports.com/",
		"http://www.si.com/",
		"http://www.nbcsports.com/",
		"http://www.sbnation.com/",
		"http://www.foxsports.com/",
		"http://www.rantsports.com/",
		"http://www.deadspin.com/",
		"http://www.thepostgame.com/",
		"http://www.sportingnews.com/",
		"http://www.fansided.com/",
		"http://www.yardbarker.com/",
		
		// top 15 blogs
		"http://www.huffingtonpost.com/",
		"http://www.tmz.com/",
		"http://www.businessinsider.com/",
		"http://mashable.com/",
		"http://gizmodo.com/",
		"http://lifehacker.com/",
		"http://gawker.com/",
		"http://www.thedailybeast.com/",
		"http://techcrunch.com/",
		"http://perezhilton.com/",
		"http://www.engadget.com/",
		"http://www.cheezburger.com/",
		"http://jezebel.com/",
		"http://deadspin.com/",
		"http://kotaku.com/",
		 
		// top 15 car 
		"https://www.yahoo.com/autos/",
		"http://www.autotrader.com/",
		"http://www.kbb.com/",
		"http://www.cars.com/",
		"http://www.edmunds.com/",
		"http://www.autoblog.com/",
		"http://www.cargurus.com/",
		"http://jalopnik.com/",
		"http://www.carmax.com/",
		"http://www.motortrend.com/",
		"http://www.caranddriver.com/",
		"http://www.carfax.com/",
		"http://www.thecarconnection.com/",
		"http://www.carsdirect.com/",
		"http://www.topspeed.com/",
		 
		// top 15 gadget 
		"https://www.yahoo.com/tech",
		"http://www.theverge.com/",
		"http://www.tomshardware.com/",
		"http://www.digitaltrends.com/",
		"http://www.techrepublic.com/",
		"http://www.gizmag.com/",
		"http://www.anandtech.com/",
		"http://www.imore.com/",
		"http://www.gsmarena.com/",
		"http://www.geek.com/"
	};
	
	public int pageWaitSec = 10;
	public String profileName = "ADN";
	public boolean pauseOnFail, pauseOnSuccess;

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
		driver.switchTo().activeElement();

		try {
			
			WebElement we = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("adnauseam-count")));
			
			count = Integer.parseInt(we.getAttribute("count"));
			
		} catch (org.openqa.selenium.TimeoutException e) {
				
			System.err.println("No count for url(timeout): "+url);
			checkForPause(e);
		
		} catch (Throwable t) {
			
			System.err.println("No count for url(ERROR): "+url+"\n"+t.getMessage());
			checkForPause(t);
		}			

		if (pauseOnSuccess) {
			
			System.err.println("Pausing: count was "+count);
			pauseForever();
		}
		
		driver.quit();
		
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
		
		return this.go(TEST_URLS);
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
	
	public String toCSV(List<Result> results) {
		
		throw new RuntimeException("IMPLEMENT ME");
	}
	
	public static void main(String[] args) {
		
		String profName = null;
		AdNauseamPageVisitor apv = null;
		if (args != null && args.length > 0)
			profName = args[0];
		apv = new AdNauseamPageVisitor(profName);
		//apv.pauseOnFail = true;
		//apv.pauseOnSuccess = true;
		String jsonResult = apv.go();
				//"https://www.google.com.hk/search?q=jewelry");
				//"https://duckduckgo.com/?q=jewelry&t=ffsb&ia=about");
		System.out.println("RESULTS:\n"+jsonResult);
	}

}
