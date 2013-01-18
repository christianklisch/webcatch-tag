package de.christian_klisch.util.webcatch;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MainClass
{

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException,
			SAXException, TransformerFactoryConfigurationError, TransformerException
	{
		URLConnection connection = new URL("http://www.christian-klisch.de/").openConnection();
		connection.setConnectTimeout(10000);

		// XML Daten einlesen
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		InputStream input = connection.getInputStream();
		byte[] buffer = new byte[1000];
		int amount = 0;
		boolean startwrite = false;

		// Inhalt lesen
		while (amount != -1)
		{
			result.write(buffer, 0, amount);
			amount = input.read(buffer);
		}

		System.out.println(result);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		long l = System.currentTimeMillis();

		String t = result.toString();
		int startindex = t.indexOf("<html");
		t = t.substring(startindex);

		ByteArrayInputStream is = new ByteArrayInputStream(t.getBytes());

		Document doc = db.parse(is);
		Node n = doc.getFirstChild();
		NodeList nl = doc.getElementsByTagName("div");

		for (int j = 0; j < nl.getLength(); j++)
		{
			Node node = nl.item(j);
			NamedNodeMap attributes = node.getAttributes();

			for (int k = 0; k < attributes.getLength(); k++)
			{
				Node attr = attributes.item(k);
				if (attr.getNodeName().equals("id") && attr.getNodeValue().equals("footer"))
				{
					System.out.println("gefunden");

					StringWriter writer = new StringWriter();
					Transformer transformer = TransformerFactory.newInstance().newTransformer();
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					transformer.setOutputProperty(OutputKeys.INDENT, "no");
					transformer.transform(new DOMSource(node), new StreamResult(writer));
					String xml = writer.toString();

					System.out.println(xml);

				}
			}
		}


		System.out.println("Ende" + (System.currentTimeMillis() - l));

	}
}
