package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static tests.Constants.alertLocator;
import static tests.Constants.resultLocator;

import java.util.function.Consumer;

import org.junit.Test;

import com.microsoft.playwright.Dialog;

public class AlertHandlerTest extends DialogBaseTest {

	@Test
	public void defaultBehavior() {
		page.locator(alertLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You successfully clicked an alert");
	}

	@Test
	public void singleUseHandler() {
		page.onceDialog((Dialog dialog) -> {
			assertEquals("alert", dialog.type());
			assertEquals("I am a JS Alert", dialog.message());
			dialog.accept();
		});

		page.locator(alertLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You successfully clicked an alert");
	}

	@Test
	public void multipleUseHandler() {
		Consumer<Dialog> handler = new Consumer<Dialog>() {
			@Override
			public void accept(Dialog dialog) {
				assertEquals("alert", dialog.type());
				assertEquals("I am a JS Alert", dialog.message());
				dialog.accept();
			}
		};

		page.onDialog(handler);

		page.locator(alertLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You successfully clicked an alert");

		page.locator(alertLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You successfully clicked an alert");
	}

	@Test
	public void onAndOffHandler() {
		Consumer<Dialog> handler = new Consumer<Dialog>() {
			@Override
			public void accept(Dialog dialog) {
				assertEquals("alert", dialog.type());
				assertEquals("I am a JS Alert", dialog.message());
				dialog.accept();
			}
		};

		page.onDialog(handler);

		page.locator(alertLocator).click();
		assertThat(page.locator(resultLocator)).hasText("You successfully clicked an alert");

		page.offDialog(handler);
	}
}
