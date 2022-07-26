package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CssPsuedohastextTest extends TextSelectorBaseTest {

	// SIMPLE_CONTENT structure
	// <span id='container'>Playwright is easy to use</span>

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

	@Test
	public void simpleExactMatch() {
		page.setContent(SIMPLE_CONTENT);
		locator = page.locator("span:has-text('Playwright is easy to use')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright is easy to use", locator.textContent());
	}

	@Test
	public void simpleIgnoreCaseMatch() {
		page.setContent(SIMPLE_CONTENT);
		locator = page.locator("span:has-text('Playwright IS EASY TO USE')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright is easy to use", locator.textContent());
	}

	@Test
	public void simpleSubstringMatch() {
		page.setContent(SIMPLE_CONTENT);
		locator = page.locator("span:has-text('Playwright is')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright is easy to use", locator.textContent());
	}

	// span id=deep text multiple match
	@Test
	public void complexSpanIdDeepTextMultipleMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:has-text('things')");

		assertEquals(4, locator.count());
	}

	// span id=deep exact match
	@Test
	public void complexSpanIdDeepExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span#deep:has-text('things')");

		assertEquals("deep", locator.getAttribute("id"));
		assertEquals("things", locator.textContent());
	}

	// span id=inner "text node" exact match
	@Test
	public void complexSpanIdInnerExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span#inner:has-text('the irritating')");

		assertEquals("inner", locator.getAttribute("id"));
		assertEquals("the irritating things", locator.textContent());
	}

	// span id=inner "text node + deep span text node" exact match
	@Test
	public void complexSpanIdInnerAllTextNodesExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span#inner:has-text('the irritating things')");

		assertEquals("inner", locator.getAttribute("id"));
		assertEquals("the irritating things", locator.textContent());
	}

	// span id=inner "text node + deep span text node" substring match
	@Test
	public void complexSpanIdInnerAllTextNodesSubstringMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span#inner:has-text('irritating things')");

		assertEquals("inner", locator.getAttribute("id"));
		assertEquals("the irritating things", locator.textContent());
	}

	// span id=inner "text node + deep span text node" text substring multiple match
	@Test
	public void complexSpanIdInnerAllTextNodesSubstringMultipleMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:has-text('irritating things')");

		assertEquals(3, locator.count());
	}

	// top span id=container nearest text node exact match
	@Test
	public void complexSpanIdContainerNearestTextNodeExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:has-text('Playwright handles')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright handles most of the irritating things about browser automation. Happy learnings!",
				locator.textContent());
	}

	// top span id=container deepest text node exact match
	@Test
	public void complexSpanIdContainerDeepestTextNodeExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span#container:has-text('things')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright handles most of the irritating things about browser automation. Happy learnings!",
				locator.textContent());
	}
}
