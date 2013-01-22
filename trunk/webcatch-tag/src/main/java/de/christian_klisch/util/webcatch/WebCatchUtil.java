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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility to request HTML-code and cache it for an defined number of seconds.
 * After this time the cached HTML-codes will be reloaded at function call.
 * 
 * @author Christian Klisch
 * 
 */
public final class WebCatchUtil
{

	/**
	 * Stores all requested HTML-codes for x seconds (seconds are defined in
	 * <em>resetAfter</em>).
	 */
	private Map<String, String>		results			= new HashMap<String, String>();

	/**
	 * Reset stored HTML-Codes after this count of seconds.
	 */
	private long					resetAfter		= 100;

	/**
	 * Stores last reset-time.
	 */
	private long					lastReset		= System.currentTimeMillis();

	/**
	 * Singleton instance.
	 */
	private static WebCatchUtil		instance		= null;

	private List<WebCatchDecorator>	decoratorList	= new ArrayList<WebCatchDecorator>();

	private WebCatchUtil()
	{
	}

	/**
	 * @return the singleton instance.
	 */
	public static WebCatchUtil getInstance()
	{
		if (instance == null)
		{
			instance = new WebCatchUtil();
		}
		return instance;
	}

	/**
	 * Getter for HTML-code. Read from result-Map if cached. If not cached yet,
	 * open function to load HTML-Code from web. In this case website must
	 * contain a tag with the definied attribute and attribute-value which is to
	 * extract. Example: extract footer-div. Match div-tag with attribute
	 * <em>id</em> and attribute-value <em>footer</em>.
	 * 
	 * @param source Source-URL e.g. <em>http://www.google.com</em>.
	 * @param htmltag Tag in website to extract e.g. div-tag.
	 * @param attribute contained in htmltag.
	 * @param value value of attribute to match.
	 * @return extracted HTML-code tag from source-website.
	 */
	public String getWebTag(String source, String htmltag, String attribute, String value)
	{
		checkReset();

		String key = source + htmltag + attribute + value;

		if (results.get(key) != null)
			return callDecorator(results.get(key));

		return callDecorator(readWebTag(key, source, htmltag, attribute, value));
	}

	/**
	 * Call and execute user-implemented <em>WebCatchDecorator</em>.
	 * 
	 * @param htmlCode Input html-code.
	 * @return By decorator manipulated html-code.
	 */
	private String callDecorator(String htmlCode)
	{
		for (WebCatchDecorator d : decoratorList)
			htmlCode = d.changeHTMLCode(htmlCode);
		return htmlCode;
	}

	/**
	 * Reset stored code, if code is older than x seconds.
	 */
	private void checkReset()
	{
		if (lastReset < System.currentTimeMillis() - resetAfter * 1000)
		{
			results.clear();
			lastReset = System.currentTimeMillis();
		}
	}

	/**
	 * Extract specified HTML-Tag from HTML-code from website.
	 * 
	 * @param key Key of result-Map.
	 * @param source Source-URL e.g. <em>http://www.google.com</em>.
	 * @param htmltag Tag in website to extract e.g. div-tag.
	 * @param attribute contained in htmltag.
	 * @param value value of attribute to match.
	 * @return extracted HTML-code tag from source-website.
	 */
	private String readWebTag(String key, String source, String htmltag, String attribute, String value)
	{
		try
		{
			String sourceXML = readSource(source);

			ByteArrayInputStream is = new ByteArrayInputStream(sourceXML.getBytes());

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			NodeList nl = doc.getElementsByTagName(htmltag);

			for (int j = 0; j < nl.getLength(); j++)
			{
				Node node = nl.item(j);
				NamedNodeMap attributes = node.getAttributes();

				for (int k = 0; k < attributes.getLength(); k++)
				{
					Node attr = attributes.item(k);
					if (attr.getNodeName().equals(attribute) && attr.getNodeValue().equals(value))
					{
						StringWriter writer = new StringWriter();
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
						transformer.setOutputProperty(OutputKeys.INDENT, "no");
						transformer.transform(new DOMSource(node), new StreamResult(writer));
						String html = writer.toString();

						results.put(key, html);
						return html;
					}
				}
			}
		}
		catch (Exception e)
		{
			return "WebCatch-Tag: exception occured: " + e.getMessage();
		}
		return "WebCatch-Tag: no HTML found";
	}

	/**
	 * Put an own HTML-code fragment to Map.
	 * 
	 * @param key String of source-url + htmltag + attribute + value;
	 * @param webHmtl HTML-Code.
	 */
	public void putWebHtml(String key, String webHmtl)
	{
		results.put(key, webHmtl);
	}

	/**
	 * Add a new user-implemented decorator to manipulate HTML-code.
	 * 
	 * @param decorator user-implementation of <em>WebCatchDecorator</em>.
	 */
	public void addDecorator(WebCatchDecorator decorator)
	{
		decoratorList.add(decorator);
	}

	/**
	 * Clear list of user-decorators.
	 */
	public void clearDecorator()
	{
		decoratorList.clear();
	}

	/**
	 * Connect to external website and read HTML-code as structured XML.
	 * 
	 * @param source Source website to read.
	 * @return HTML of requested website as String.
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private String readSource(String source) throws IOException, MalformedURLException
	{
		URLConnection connection = new URL(source).openConnection();
		connection.setConnectTimeout(10000);

		// read
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		InputStream input = connection.getInputStream();
		byte[] buffer = new byte[1000];
		int amount = 0;

		while (amount != -1)
		{
			result.write(buffer, 0, amount);
			amount = input.read(buffer);
		}

		String webHtml = result.toString();
		int startindex = webHtml.indexOf("<html");
		webHtml = webHtml.substring(startindex);

		return webHtml;
	}

}
