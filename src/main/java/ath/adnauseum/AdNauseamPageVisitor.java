
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
		"http://www.geek.com/",
		
		// top 100 domain
		"https://www.google.com/",
		"https://www.facebook.com/",
		"https://www.youtube.com/",
		"http://www.baidu.com/",
		"https://us.yahoo.com/",
		"http://en.wikipedia.org/",
		"http://www.amazon.com/",
		"https://twitter.com/",
		"http://hk.taobao.com/",
		"http://www.qq.com/",
		"http://google.co.in",
		"http://linkedin.com",
		"http://live.com",
		"http://sina.com.cn",
		"http://weibo.com",
		"http://yahoo.co.jp",
		"http://tmall.com",
		"http://google.co.jp",
		"http://google.de",
		"http://ebay.com",
		"http://blogspot.com",
		"http://hao123.com",
		"http://bing.com",
		"http://reddit.com",
		"http://t.co",
		"http://google.co.uk",
		"http://google.com.br",
		"http://amazon.co.jp",
		"http://sohu.com",
		"http://tumblr.com",
		"http://google.fr",
		"http://imgur.com",
		"http://wordpress.com",
		"http://instagram.com",
		"http://pinterest.com",
		"http://msn.com",
		"http://apple.com",
		"http://PayPal.com",
		"http://microsoft.com",
		"http://aliexpress.com",
		"http://google.it",
		"http://yandex.ru",
		"http://xvideos.com",
		"http://imdb.com",
		"http://fc2.com",
		"http://alibaba.com",
		"http://stackoverflow.com",
		"http://google.es",
		"http://vk.com",
		"http://ask.com",
		"http://amazon.de",
		"http://360.cn",
		"http://netflix.com",
		"http://163.com",
		"http://Onclickads.net",
		"http://adcash.com",
		"http://go.com",
		"http://google.ca",
		"http://craigslist.org",
		"http://google.com.hk",
		"http://Naver.com",
		"http://diply.com",
		"http://gmail.com",
		"http://gmw.cn",
		"http://Googleadservices.com",
		"http://xhamster.com",
		"http://google.ru",
		"http://rakuten.co.jp",
		"http://bbc.co.uk",
		"http://mail.ru",
		"http://amazon.co.uk",
		"http://google.com.tr",
		"http://amazon.cn",
		"http://ebay.de",
		"http://kickass.to",
		"http://pornhub.com",
		"http://dropbox.com",
		"http://google.pl",
		"http://jd.com",
		"http://Nicovideo.jp",
		"http://soso.com",
		"http://Espn.go.com",
		"http://google.com.au",
		"http://Cntv.cn",
		"http://googleusercontent.com",
		"http://cnn.com",
		"http://adobe.com",
		"http://alipay.com",
		"http://Github.com",
		"http://dailymotion.com",
		"http://youku.com",
		"http://flipkart.com",
		"http://blogger.com",
		"http://pixnet.net",
		"http://google.com.kr",
		"http://Uol.com.br",
		"http://Buzzfeed.com",
		"http://huffingtonpost.com",
		"http://nytimes.com",
		"http://www.indiatimes.com/culture/who-we-are/11-things-people-say-when-theyre-clueless-about-the-stock-market-232479.html",
		
		"http://food.com",
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
		
		WebDriver driver = null;
		try {
			
			driver = createDriver();
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
				//"http://www.zdnet.com/");
				//"https://www.google.com.hk/search?q=jewelry");
				//"https://duckduckgo.com/?q=jewelry&t=ffsb&ia=about");
		System.out.println("\nRESULTS:\n"+jsonResult);
	}

}
