package base;

import org.junit.After;
import org.junit.Before;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

import factory.BrowserCreator;

public class BaseTest {

	protected BrowserContext context;

	protected Page page;

	@Before
	public void setup() {
		context = BrowserCreator.fetchBrowser().newContext();
		page = context.newPage();
	}

	@After
	public void tearDown() {
		context.close();
	}

}
