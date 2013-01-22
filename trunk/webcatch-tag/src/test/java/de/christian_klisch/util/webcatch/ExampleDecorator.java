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

/**
 * Example decorator implementation for <em>WebCatchDecorator</em>. This example
 * manipulates the HTML-code by change the character 'F' into 'W'.
 * 
 * @author klisch
 * 
 */
public class ExampleDecorator implements WebCatchDecorator
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.christian_klisch.util.webcatch.WebCatchDecorator#changeHTMLCode(java
	 * .lang.String)
	 */
	public String changeHTMLCode(String htmlCode)
	{
		if (htmlCode == null)
			return null;

		String newCode = htmlCode.replaceAll("[o]+","uu");
		return newCode;
	}

}
