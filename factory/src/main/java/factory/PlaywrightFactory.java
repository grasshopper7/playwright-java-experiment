package factory;

import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {

	public static Playwright instantiate() {
		// Default creation
		return Playwright.create();
	}
}
