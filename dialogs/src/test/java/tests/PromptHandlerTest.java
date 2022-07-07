package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static tests.Constants.promptLocator;
import static tests.Constants.resultLocator;

import org.junit.Test;

import com.microsoft.playwright.Dialog;

public class PromptHandlerTest extends DialogBaseTest {

	@Test
	public void promptDefaultBehavior() {
		page.locator(promptLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You entered: null");
	}

	@Test
	public void promptAcceptHandler() {
		page.onceDialog((Dialog dialog) -> {
			assertEquals("prompt", dialog.type());
			assertEquals("I am a JS prompt", dialog.message());
			dialog.accept("Hello World");
		});

		page.locator(promptLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You entered: Hello World");
	}

	@Test
	public void promptCancelHandler() {
		page.onceDialog((Dialog dialog) -> {
			assertEquals("prompt", dialog.type());
			assertEquals("I am a JS prompt", dialog.message());
			dialog.dismiss();
		});

		page.locator(promptLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You entered: null");
	}

}
