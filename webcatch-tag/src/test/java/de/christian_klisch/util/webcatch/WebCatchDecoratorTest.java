/*
 * Copyright 2013 Christian Klisch
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.christian_klisch.util.webcatch;

import junit.framework.TestCase;

/**
 * Testclass for interface <em>WebCatchDecorator</em>.
 * 
 * @author klisch
 * 
 */
public class WebCatchDecoratorTest extends TestCase
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

		// add decorator to change some characters
		util.addDecorator(new ExampleDecorator());
		
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

		util.clearDecorator();
		util = null;
	}
	
	/**
	 * Test for changed HTML-code by <em>ExampleDecorator</em> in Map. 
	 */
	public void testGetWebTagCached()
	{
		String htmlCode = util.getWebTag("http://www.google.com/footer.html", "div", "id", "footer");
		
		assertTrue(htmlCode.indexOf("Fuut") >= 0);
	}	
}
