/**
 * 
 */
package de.christian_klisch.util.webcatch;

import junit.framework.TestCase;

/**
 * @author klisch
 * 
 */
public class WebCatchUtilTest extends TestCase
{

	private WebCatchUtil	util;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		util = WebCatchUtil.getInstance();

		// insert example html site into map.
		util.putWebHtml("http://www.google.com/footer.htmldividfooter", "<div id=\"footer\">Foot</div>");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();

		util = null;
	}

	/**
	 * Test for cached HTML-code in Map.
	 */
	public void testGetWebTagCached()
	{
		String htmlCode = util.getWebTag("http://www.google.com/footer.html", "div", "id", "footer");
		assertTrue(htmlCode.indexOf("Foot") >= 0);
	}

	/**
	 * Test for not cached HTML-code in Map.
	 */
	public void testGetWebTagNotCached()
	{
		String htmlCode = util.getWebTag("http://www.google.com/search.html", "div", "id", "footer");
		assertFalse(htmlCode.indexOf("Foot") >= 0);
	}
	

}
