package tests;

import static tests.Constants.url;

import org.junit.Before;

import base.BaseTest;

public class DialogBaseTest extends BaseTest {

	@Before
	public void before() {
		page.navigate(url);
	}
}
