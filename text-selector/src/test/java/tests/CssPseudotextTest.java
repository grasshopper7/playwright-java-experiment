package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.microsoft.playwright.TimeoutError;

public class CssPseudotextTest extends TextSelectorBaseTest {

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
		locator = page.locator("span:text('Playwright is easy to use')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright is easy to use", locator.textContent());
	}

	@Test
	public void simpleIgnoreCaseMatch() {
		page.setContent(SIMPLE_CONTENT);
		locator = page.locator("span:text('Playwright IS EASY TO USE')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright is easy to use", locator.textContent());
	}

	@Test
	public void simpleSubstringMatch() {
		page.setContent(SIMPLE_CONTENT);
		locator = page.locator("span:text('Playwright is')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright is easy to use", locator.textContent());
	}

	// span id=deep exact match
	@Test
	public void complexSpanIdDeepExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:text('things')");

		assertEquals("deep", locator.getAttribute("id"));
		assertEquals("things", locator.textContent());
	}

	// span id=inner "text node" exact match
	@Test
	public void complexSpanIdInnerExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:text('the irritating')");

		assertEquals("inner", locator.getAttribute("id"));
		assertEquals("the irritating things", locator.textContent());
	}

	// span id=inner "text node + deep span text node" exact match
	@Test
	public void complexSpanIdInnerAllTextNodesExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:text('the irritating things')");

		assertEquals("inner", locator.getAttribute("id"));
		assertEquals("the irritating things", locator.textContent());
	}

	// span id=inner "text node + deep span text node" substring match
	@Test
	public void complexSpanIdInnerAllTextNodesSubstringMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:text('irritating things')");

		assertEquals("inner", locator.getAttribute("id"));
		assertEquals("the irritating things", locator.textContent());
	}

	// span id=outer1 "top text node + inner span text node" substring match
	@Test
	public void complexSpanIdOuter1TopTextNodeAndInnerSpanTextNodeSubstringMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:text('of the')");

		assertEquals("outer1", locator.getAttribute("id"));
		assertEquals(" most of the irritating things about ", locator.textContent());
	}

	// span id=outer1 "deep span text node + bottom text node" substring match
	@Test
	public void complexSpanIdOuter1DeepSpanTextNodeAndBottomTextNodeSubstringMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:text('ings abo')");

		assertEquals("outer1", locator.getAttribute("id"));
		assertEquals(" most of the irritating things about ", locator.textContent());
	}

	// span id=container "bottom text node" substring match
	@Test
	public void complexSpanIdContainerBottomTextNodeExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span:text('learnings!')");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright handles most of the irritating things about browser automation. Happy learnings!",
				locator.textContent());
	}

	// span id=inner span id deep "text node" match FAIL
	@Test(expected = TimeoutError.class)
	public void complexSpanIdInnerSpanIdDeepTextNodeMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span#inner:text('things')");

		assertEquals("inner", locator.getAttribute("id"));
	}

	// span id=outer1 span id inner "all text" match FAIL
	@Test(expected = TimeoutError.class)
	public void complexSpanIdOuter1SpanIdInnerAllTextMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("span#outer1:text('the irritating things')");

		assertEquals("outer1", locator.getAttribute("id"));
	}
}
