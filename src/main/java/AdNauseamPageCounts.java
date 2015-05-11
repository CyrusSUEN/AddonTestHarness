
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdNauseamPageCounts {

	public static String FF_PROFILE = "ADN";
	public static boolean PAUSE_ON_FAIL = false;
	
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

	private static int getCount(String url) {
		
		int count = 0;
		
		WebDriver driver = createDriver();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get(url);

		try {
			
			WebElement we = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("adnauseam-count")));
			
			count = Integer.parseInt(we.getAttribute("count"));
			
		} catch (org.openqa.selenium.TimeoutException e) {
				
			System.err.println("No count for url: "+url);
			if (PAUSE_ON_FAIL) {
				try {
					Thread.sleep(Integer.MAX_VALUE);
				} 
				catch (InterruptedException x) {}
			}
		}			

		driver.quit();
		
		return count;
	}

	private static WebDriver createDriver() {
		
		FirefoxProfile ffp = new ProfilesIni().getProfile(FF_PROFILE);
		ffp.setPreference("extensions.adnauseam@rednoise.org.automated", true);
		return new FirefoxDriver(ffp);
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < TEST_URLS.length; i++) {
			int count = getCount(TEST_URLS[i]);
			System.out.println(i+") "+count+" on "+TEST_URLS[i]);
		}
	}
}
