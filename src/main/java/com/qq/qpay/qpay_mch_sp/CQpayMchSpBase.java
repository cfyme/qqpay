package com.qq.qpay.qpay_mch_sp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.tenpay.util.QpayHttpUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tenpay.util.HttpClientUtil;

@SuppressWarnings("serial")
public abstract class CQpayMchSpBase extends TreeMap<String, String> {
	private static final String JKS_CA_FILENAME = "tenpay_cacert.jks";
	/** 超时时间,以秒为单位 */
	private int timeOut;

	/** ca证书文件 */
	private String caFile;

	/** 证书文件 */
	private String certFile;

	/** 证书密码 */
	private String certPasswd;

	private String response;

	private static final String JKS_CA_ALIAS = "tenpay";

	private static final String JKS_CA_PASSWORD = "";

	private IQpayMchSpKeyFinder m_keyFinder = null;

	public CQpayMchSpBase(IQpayMchSpKeyFinder keyFinder) {
		this.m_keyFinder = keyFinder;
		this.timeOut = 5;// 默认5秒
		this.certFile = null;
		this.certPasswd = null;
		this.caFile = null;
	}

	/**
	 * 设置证书信息
	 *
	 * @param certFile
	 *            证书文件
	 * @param certPasswd
	 *            证书密码
	 */
	public void setCertInfo(String certFile, String certPasswd) {
		this.certFile = certFile;
		this.certPasswd = certPasswd;
	}

	/**
	 * 设置ca
	 *
	 * @param caFile
	 */
	public void setCaInfo(String caFile) {
		this.caFile = caFile;
	}

	public void setAppid(String appid) {
		this.put("appid", appid);
	}

	public void setMch_id(String mch_id) {
		this.put("mch_id", mch_id);
	}

	public String getMch_id() {
		return this.get("mch_id");
	}

	public void set(String key, String value) {
		this.put(key, value);
	}

	public abstract String getUrl();

	protected boolean needClientPem() {
		return false;
	}

	public TreeMap<String, String> parseResponse()
			throws ParserConfigurationException, SAXException, IOException {
		TreeMap<String, String> responseMap = new TreeMap<String, String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(this.response
				.getBytes(StandardCharsets.UTF_8)));
		NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(i);
			System.out.println(i+" name:"+n.getNodeName()+":"+n.getTextContent());
			responseMap.put(n.getNodeName(), n.getTextContent());
		}
		return responseMap;
	}

	public boolean checkSign(TreeMap<String, String> responseMap)
			throws NoSuchAlgorithmException {
		String responseSign = responseMap.get("sign");
		String calSign = generateSign(responseMap,
				m_keyFinder.getKey(responseMap.get("mch_id")));
		return responseSign.equalsIgnoreCase(calSign);
	}

	public String call() throws Exception {
		URL resourceURL = Thread.currentThread().getContextClassLoader().getResource("");

		System.out.println("resourceURL obj="+resourceURL);
		System.out.println("resourceURL path="+resourceURL.getPath());

		URL url = new URL(this.getUrl());
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("charset", "UTF-8");

		String sPostXml = this.getPostXml();
		conn.setDoOutput(true);
		// 不使用缓存
		conn.setUseCaches(false);

		// 允许输入输出
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// 是否需要双向证书。
		if (this.needClientPem()) {
//            if (null == this.caFile || null == this.certFile || null == this.certPasswd) {
//                throw new Exception("NEED CLIENT PEM");
//            }

			if (null == this.certFile || null == this.certPasswd) {
				throw new Exception("NEED CLIENT PEM");
			}

//            File jksCAFile = new File(resourceURL.getPath()+ "qpaycert/1/"+ JKS_CA_FILENAME);
//            if (!jksCAFile.isFile()) {
//                X509Certificate cert = (X509Certificate) QpayHttpUtil
//                        .getCertificate(this.caFile);
//
//                FileOutputStream out = new FileOutputStream(jksCAFile);
//
//                // store jks file
//                QpayHttpUtil.storeCACert(cert, JKS_CA_ALIAS, JKS_CA_PASSWORD,
//                        out);
//
//                out.close();
//
//            }

			// FileInputStream trustStream = new FileInputStream(resourceURL.getPath()+jksCAFile);
			FileInputStream keyStream = new FileInputStream(resourceURL.getPath()+this.certFile);

			//SSLContext sslContext = QpayHttpUtil.getSSLContext1(keyStream, this.certPasswd);
			SSLContext sslContext = com.qq.qpay.demo2.HttpClientUtil.getSSLContext1(keyStream, this.certPasswd);

			// 关闭流
			keyStream.close();
			//trustStream.close();

			SSLSocketFactory sf = sslContext.getSocketFactory();
			conn.setSSLSocketFactory(sf);
		}

		// 发送请求
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.write(sPostXml.getBytes(StandardCharsets.UTF_8));
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		if (200 != responseCode) {
			throw new Exception("CALL HTTP ERROR " + responseCode);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream(),StandardCharsets.UTF_8));
		String tmpInputLine;
		StringBuffer responseBuffer = new StringBuffer();
		while ((tmpInputLine = in.readLine()) != null) {
			responseBuffer.append(tmpInputLine);
		}
		in.close();
		this.response = responseBuffer.toString();
		return this.response;
	}

	public String getPostXml() throws NoSuchAlgorithmException {
		this.put("sign",
				generateSign(this, m_keyFinder.getKey(this.get("mch_id"))));

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");

		sb.append("<xml>");
		Iterator<String> ite = this.keySet().iterator();
		while (ite.hasNext()) {
			String tmpKey = ite.next();
			if (this.get(tmpKey).isEmpty()) {
				continue;
			}
			sb.append("<" + tmpKey + ">");
			sb.append(this.get(tmpKey));
			sb.append("</" + tmpKey + ">");

		}
		sb.append("</xml>");
		return sb.toString();
	}



	public String getPostXml2() throws NoSuchAlgorithmException {
		this.put("sign",
				generateSign(this, m_keyFinder.getKey(this.get("mch_id"))));

		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Iterator<String> ite = this.keySet().iterator();
		while (ite.hasNext()) {
			String tmpKey = ite.next();
			if (this.get(tmpKey).isEmpty()) {
				continue;
			}
			sb.append("<" + tmpKey + ">");
			sb.append(this.get(tmpKey));
			sb.append("</" + tmpKey + ">");

		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 生成商户签名
	 *
	 * @throws NoSuchAlgorithmException
	 */
	private static String generateSign(TreeMap<String, String> srcMap,
									   String key) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] inputByteArray = (generateSignSrc(srcMap) + key)
				.getBytes(StandardCharsets.UTF_8);
		messageDigest.update(inputByteArray);
		byte[] resultByteArray = messageDigest.digest();
		return byteArrayToHex(resultByteArray);
	}

	private static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];

		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}

		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

	/**
	 * 生成签名原串。
	 *
	 * @return
	 */
	private static String generateSignSrc(TreeMap<String, String> srcMap) {
		StringBuffer sb = new StringBuffer();
		Iterator<java.util.Map.Entry<String, String>> ite = srcMap.entrySet().iterator();
		while (ite.hasNext()) {
			java.util.Map.Entry<String,String> tmpEntry = ite.next();
			if ("sign".equalsIgnoreCase(tmpEntry.getKey()) //
					|| tmpEntry.getValue().isEmpty()) {
				continue;
			}
			sb.append(tmpEntry.getKey()).append("=").append(tmpEntry.getValue())
					.append("&");
		}
		sb.append("key=");
		return sb.toString();
	}
}
