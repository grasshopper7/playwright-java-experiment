package tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TextSelectorBaseTest {

	protected static Playwright playwright;
	protected static Browser browser;

	protected static final String SIMPLE_CONTENT = "<span id='container'>Playwright is easy to use</span>";
	protected static final String COMPLEX_CONTENT = "<span id='container'>Playwright handles<span id='outer1'> most of <span id='inner'>the irritating <span id='deep'>things</span></span> about </span>browser <span id='outer2'>automation. </span>Happy learnings!</span>";

	// @formatter:off
	// COMPLEX_CONTENT structure
	/*
	<span id=container>
		Playwright handles
		<span id=outer1>
			most of
			<span id=inner>
				the irritating
				<span id=deep>
					things
				</span>
				about
			</span>
		browser
		<span id=outer2>
			automation.
		</span>
		Happy learnings!
	</span>	
	*/
	// @formatter:on

	@BeforeClass
	public static void setup() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch();
	}

	@AfterClass
	public static void teardown() {
		playwright.close();
	}

	protected BrowserContext browserContext;
	protected Page page;
	protected Locator locator;

	@Before
	public void before() {
		browserContext = browser.newContext();
		page = browserContext.newPage();
		page.setDefaultTimeout(2000);
	}

	@After
	public void after() {
		browserContext.close();
	}
}
