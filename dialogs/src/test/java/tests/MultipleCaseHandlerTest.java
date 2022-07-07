package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static tests.Constants.*;
import static tests.Constants.resultLocator;

import java.util.function.Consumer;

import org.junit.Test;

import com.microsoft.playwright.Dialog;

public class MultipleCaseHandlerTest extends DialogBaseTest {

	@Test
	public void multipleCaseHandler() {
		Consumer<Dialog> handler = new Consumer<Dialog>() {
			@Override
			public void accept(Dialog dialog) {
				if (dialog.type().equals("alert")) {
					assertEquals("I am a JS Alert", dialog.message());
					dialog.accept();
				} else if (dialog.type().equals("confirm")) {
					assertEquals("I am a JS Confirm", dialog.message());
					dialog.accept();
				} else if (dialog.type().equals("prompt")) {
					assertEquals("I am a JS prompt", dialog.message());
					dialog.accept("Hello World");
				}
			}
		};

		page.onDialog(handler);

		page.locator(alertLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You successfully clicked an alert");

		page.locator(confirmLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You clicked: Ok");

		page.locator(promptLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You entered: Hello World");
	}
}
