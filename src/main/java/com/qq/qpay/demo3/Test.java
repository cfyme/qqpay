package com.qq.qpay.demo3;

import com.other.demo.DemoQpayMchKeyFinder;
import com.qq.qpay.demo2.CQpayMerchantCreate;
import com.qq.qpay.demo2.Demo;
import com.qq.qpay.qpay_mch_sp.CQpayUnifiedOrder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) throws  Exception{
        //execute();
        execute2();
    }




    private static void execute2() throws  Exception {
        CQpayUnifiedOrder obj = new CQpayUnifiedOrder(
                new DemoQpayMchKeyFinder());

        obj.set("mch_id", Demo.DEMO_MCH_ID);
        obj.set("nonce_str", "b49b24740506ac9b5e36aadccd8237fe");
        obj.set("out_trade_no", "20171124cfyme001");
        obj.set("sub_mch_id", "1900005911");
        obj.set("body", "body_test_中文");
        obj.set("device_info", "WP00000001");
        obj.set("fee_type", "CNY");
        obj.set("sub_mch_id", "1900005911");
        obj.set("notify_url", "http://10.222.146.71:80/success.xml");
        obj.set("spbill_create_ip", "127.0.0.1");
        obj.set("total_fee", "1");
        obj.set("trade_type", "NATIVE");


        HttpWxClient httpWxClient = new HttpWxClient();

        String str = httpWxClient.post(obj.getUrl(), obj.getPostXml());
        System.out.println("=========="+str);

        System.out.println("-------------------------");

        parseResponse(str);
    }


    private static void execute() throws  Exception {
        CQpayMerchantCreate obj = new CQpayMerchantCreate(new DemoQpayMchKeyFinder());
        //obj.set("action", "update");
        obj.set("mch_id", Demo.DEMO_MCH_ID);
        obj.set("merchant_name", "平安测试商户2");
        obj.set("merchant_shortname", "平安银行QQ钱包商户");
        obj.set("service_phone", "18221229057");
        obj.set("business", "线下零售—超市");
        obj.set("merchant_remark", "yh2");


        HttpWxClient httpWxClient = new HttpWxClient();

        String str = httpWxClient.post(obj.getUrl(), obj.getPostXml());
        System.out.println("=========="+str);

        System.out.println("-------------------------");

        parseResponse(str);
    }


    public static TreeMap<String, String> parseResponse(String str)
            throws Exception {
        TreeMap<String, String> responseMap = new TreeMap<String, String>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
        NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            System.out.println(i+" name:"+n.getNodeName()+":"+n.getTextContent());
            responseMap.put(n.getNodeName(), n.getTextContent());
        }
        return responseMap;
    }
}
