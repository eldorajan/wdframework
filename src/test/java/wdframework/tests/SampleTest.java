package wdframework.tests;

import org.testng.annotations.Test;

import wd.common.CommonTest;

/**
 * Sample Test Suite
 * @author erajan
 *
 */
public class SampleTest extends CommonTest{

	@Test
	public void TestGoogle() {
		sa.searchInGoogle(getWebDriver(),"Cheese");

	}

	@Test
	public void TestGoogle2() {
		sa.searchInGoogle(getWebDriver(),"Cheese2");

	}

	@Test
	public void TestGoogle3() {
		sa.searchInGoogle(getWebDriver(),"Cheese3");

	}

	@Test
	public void TestGoogle4() {
		sa.searchInGoogle(getWebDriver(),"Cheese4");

	}

	@Test
	public void TestGoogle5() {
		sa.searchInGoogle(getWebDriver(),"Cheese5");

	}

	@Test
	public void TestGoogle6() {
		sa.searchInGoogle(getWebDriver(),"Cheese6");

	}

	@Test
	public void TestGoogle7() {
		sa.searchInGoogle(getWebDriver(),"Cheese7");

	}
}
