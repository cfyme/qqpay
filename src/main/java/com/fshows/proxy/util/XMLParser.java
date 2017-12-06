package com.fshows.proxy.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rizenguo Date: 2014/11/1 Time: 14:06
 */
public class XMLParser {

	public static Map<String, String> getMapFromXML(String xmlString)
			throws ParserConfigurationException, IOException, SAXException {
		//xmlString = xmlString.replaceFirst("encoding=\".*\"", "encoding=\"GBK\"");
		// 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = getStringStream(xmlString);
		Document document = builder.parse(is);

		// 获取到document里面的全部结点
		NodeList allNodes = document.getFirstChild().getChildNodes();
		Node node;
		Map<String, String> map = new HashMap<String, String>();
		int i = 0;
		while (i < allNodes.getLength()) {
			node = allNodes.item(i);
			if (node instanceof Element) {
				map.put(node.getNodeName(), node.getTextContent());
			}
			i++;
		}
		return map;

	}

	public static InputStream getStringStream(String sInputString) {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			tInputStringStream = new ByteArrayInputStream(
					sInputString.getBytes());
		}
		return tInputStringStream;
	}


	public static void main(String[] args) throws  Exception{
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><return_code><![CDATA[SUCCESS]]></return_code>\n" +
				"<return_msg><![CDATA[OK]]></return_msg>\n" +
				"<mch_id><![CDATA[1900008881]]></mch_id>\n" +
				"<sub_mch_id>26128859</sub_mch_id>\n" +
				"<result_code><![CDATA[SUCCESS]]></result_code>\n" +
				"<result_msg><![CDATA[新增商户成功]]></result_msg>\n" +
				"</root>";
		str = str.replaceFirst("encoding=\".*\"", "encoding=\"UTF8\"");
		
		System.out.println( "str="+str);
		Map<String, String> map = XMLParser.getMapFromXML(str);
		System.out.println(map.get("sub_mch_id"));
		System.out.println(map.get("result_msg"));
		System.out.println(map.get("result_code"));
	}

}
