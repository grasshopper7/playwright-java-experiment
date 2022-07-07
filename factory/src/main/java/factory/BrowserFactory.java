package factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {

	public static Browser instantiate(Playwright playwright) {
		return playwright.chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(false).setSlowMo(250));
	}
}
