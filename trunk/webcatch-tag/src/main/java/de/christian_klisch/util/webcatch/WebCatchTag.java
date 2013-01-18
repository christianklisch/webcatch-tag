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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Tag to include HTML-code from external website. Include caches the external
 * HTML-Code.
 * 
 * @author Christian Klisch
 * 
 */
public final class WebCatchTag extends SimpleTagSupport
{

	private String			source;

	private String			htmltag;

	private String			attribute;

	private String			value;

	private JspWriter		out;

	/**
	 * singleton utility caches the included external HTML-codes.
	 */
	private WebCatchUtil	wcUtil	= WebCatchUtil.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException
	{
		super.doTag();

		// read from utility the external HTML-code und print it to the
		// JspContext.
		String webTag = wcUtil.getWebTag(source, htmltag, attribute, value);

		out = getJspContext().getOut();
		out.write(webTag);
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	/**
	 * @param htmltag the htmltag to set
	 */
	public void setHtmltag(String htmltag)
	{
		this.htmltag = htmltag;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

}
