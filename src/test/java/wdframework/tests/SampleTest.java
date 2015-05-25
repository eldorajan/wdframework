package wdframework.tests;

import org.testng.annotations.Test;

import wd.common.CommonTest;

/**
 * Sample Test Suite
 * @author Eldo Rajan
 *
 */
public class SampleTest extends CommonTest{

	/**
	 * TestGoogle
	 */
	@Test
	public void TestGoogle() {
		sa.searchInGoogle(getWebDriver(),"Cheese");
	}

	/**
	 * TestGoogle2
	 */
	@Test
	public void TestGoogle2() {
		sa.searchInGoogle(getWebDriver(),"Cheese2");
	}

	/**
	 * TestGoogle3
	 */
	@Test
	public void TestGoogle3() {
		sa.searchInGoogle(getWebDriver(),"Cheese3");

	}

	/**
	 * TestGoogle4
	 */
	@Test
	public void TestGoogle4() {
		sa.searchInGoogle(getWebDriver(),"Cheese4");

	}

	/**
	 * TestGoogle5
	 */
	@Test
	public void TestGoogle5() {
		sa.searchInGoogle(getWebDriver(),"Cheese5");

	}

	/**
	 * TestGoogle6
	 */
	@Test
	public void TestGoogle6() {
		sa.searchInGoogle(getWebDriver(),"Cheese6");

	}

	/**
	 * TestGoogle7
	 */
	@Test
	public void TestGoogle7() {
		sa.searchInGoogle(getWebDriver(),"Cheese7");

	}
}
