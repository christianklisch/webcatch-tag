# WebCatchDecorator #

You can use own html-code manipulation methods by implementating the WebCatchDecorator. In the test-package _de.christian\_klisch.util.webcatch_ you will find an example implementation of the interface WebCatchDecorator

### Interface ###
```
/**
 * Example decorator implementation for <em>WebCatchDecorator</em>. This example
 * manipulates the HTML-code by change the character 'oo' into 'uu'.
 * 
 * @author klisch
 * 
 */
public interface WebCatchDecorator
{
	/**
	 * Implement this method to manipulate the HTML-code for your needs. For
	 * example add missing server-hosts in html-links.
	 * 
	 * @param htmlCode String to manipulate.
	 * @return The manipulated string.
	 */
	public String changeHTMLCode(String htmlCode);
}
```

### Example implementation ###
```
public class ExampleDecorator implements WebCatchDecorator
{

	public String changeHTMLCode(String htmlCode)
	{
		if (htmlCode == null)
			return null;

		String newCode = htmlCode.replaceAll("[o]+","uu");
		return newCode;
	}
}
```