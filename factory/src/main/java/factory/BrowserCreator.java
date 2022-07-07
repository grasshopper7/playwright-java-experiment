package factory;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public class BrowserCreator {

	private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
	private static ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
	private static List<Playwright> playwrights = new ArrayList<>();

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			playwrights.forEach(p -> {
				if (p != null)
					p.close();
			});
		}));
	}

	public static Browser fetchBrowser() {
		if (browserThreadLocal.get() == null) {
			Playwright playwright = PlaywrightFactory.instantiate();
			playwrightThreadLocal.set(playwright);
			playwrights.add(playwright);

			browserThreadLocal.set(BrowserFactory.instantiate(playwright));
		}
		return browserThreadLocal.get();
	}

	public static Playwright fetchPlaywright() throws Exception {
		if (playwrightThreadLocal.get() == null)
			throw new Exception("Call fetchBrowser() method first.");

		return playwrightThreadLocal.get();
	}
}
