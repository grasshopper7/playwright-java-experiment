package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.microsoft.playwright.TimeoutError;

public class MatcherQuotedTest extends TextSelectorBaseTest {

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
		locator = page.locator("text='Playwright is easy to use'");

		assertEquals("container", locator.getAttribute("id"));
		assertEquals("Playwright is easy to use", locator.textContent());
	}

	@Test(expected = TimeoutError.class)
	public void simpleIgnoreCaseMatch() {
		page.setContent(SIMPLE_CONTENT);
		locator = page.locator("text='Playwright IS EASY TO USE'");

		assertEquals("container", locator.getAttribute("id"));
	}

	@Test(expected = TimeoutError.class)
	public void simpleSubstringMatch() {
		page.setContent(SIMPLE_CONTENT);
		locator = page.locator("text='Playwright is'");

		assertEquals("container", locator.getAttribute("id"));
	}

	// span id=deep exact match
	@Test
	public void complexSpanIdDeepExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("text='things'");

		assertEquals("deep", locator.getAttribute("id"));
		assertEquals("things", locator.textContent());
	}

	// span id=inner "text node" exact match
	@Test
	public void complexSpanIdInnerExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("text='the irritating'");

		assertEquals("inner", locator.getAttribute("id"));
		assertEquals("the irritating things", locator.textContent());
	}

	// span id=inner "text node + deep span text node" exact match FAIL
	@Test(expected = TimeoutError.class)
	public void complexSpanIdInnerAllTextNodesExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("text='the irritating things'");

		assertEquals("inner", locator.getAttribute("id"));
	}

	// span id=inner "text node + deep span text node" substring match FAIL
	@Test(expected = TimeoutError.class)
	public void complexSpanIdInnerAllTextNodesSubstringMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("text='irritating things'");

		assertEquals("inner", locator.getAttribute("id"));
	}

	// span id=outer1 "top text node" exact match
	@Test
	public void complexSpanIdOuter1TopTextNodeExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("text='most of'");

		assertEquals("outer1", locator.getAttribute("id"));
		assertEquals(" most of the irritating things about ", locator.textContent());
	}

	// span id=outer1 "bottom text node" exact match
	@Test
	public void complexSpanIdOuter1BottomTextNodeExactMatch() {
		page.setContent(COMPLEX_CONTENT);
		locator = page.locator("text='about'");

		assertEquals("outer1", locator.getAttribute("id"));
		assertEquals(" most of the irritating things about ", locator.textContent());
	}
}
