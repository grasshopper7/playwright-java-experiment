package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static tests.Constants.confirmLocator;
import static tests.Constants.resultLocator;

import org.junit.Test;

import com.microsoft.playwright.Dialog;

public class ConfirmHandlerTest extends DialogBaseTest {

	@Test
	public void confirmDefaultBehavior() {
		page.locator(confirmLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You clicked: Cancel");
	}

	@Test
	public void confirmAcceptHandler() {
		page.onceDialog((Dialog dialog) -> {
			assertEquals("confirm", dialog.type());
			assertEquals("I am a JS Confirm", dialog.message());
			dialog.accept();
		});

		page.locator(confirmLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You clicked: Ok");
	}

	@Test
	public void confirmCancelHandler() {
		page.onceDialog((Dialog dialog) -> {
			assertEquals("confirm", dialog.type());
			assertEquals("I am a JS Confirm", dialog.message());
			dialog.dismiss();
		});

		page.locator(confirmLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You clicked: Cancel");
	}
}
