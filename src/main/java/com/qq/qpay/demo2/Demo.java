package com.qq.qpay.demo2;

import com.other.demo.DemoQpayMchKeyFinder;

import java.io.File;


public class Demo {
    public static final String DEMO_MCH_ID = "1489217791";
    public static final String DEMO_SUB_MCH_ID = "9141811366";

    public static final String DEMO_OUT_TRADE_NO = "20160512161914_BBC";
    public static final String CERT_FILE = "resources/qqcert/apiclient_cert.p12";
    public static final String CA_FILE = "resources/qpay/rootca.pem";






    public void qpay_merchant() throws Exception {
        CQpayMerchantCreate obj = new CQpayMerchantCreate(new DemoQpayMchKeyFinder());
        obj.set("action", "add");
        obj.set("mch_id", DEMO_MCH_ID);
        obj.set("merchant_name", "平安测试商户2");
        obj.set("merchant_shortname", "平安银行QQ钱包商户");
        obj.set("service_phone", "18221229057");
        obj.set("business", "线下零售—超市");
        obj.set("merchant_remark", "yh");

		/*
		 * 设置证书
		 */
        obj.setCertInfo(new File(CERT_FILE), "1489217791");
       // obj.setCaInfo(new File(CA_FILE));

        String CERT_FILE = Demo.class.getClassLoader().getResource("qqcert/apiclient_cert.p12").getPath();

        System.out.println("====="+CERT_FILE);
        //设置证书
        obj.setCertInfo(new File(CERT_FILE), Demo.DEMO_MCH_ID);

        System.out.println("=========================qpay_merchant=======");
        System.out.println(obj.getPostXml());
        System.out.println(obj.call());
        //System.out.println(obj.checkSign(obj.parseResponse()));
    }

    public static void main(String[] args) throws Exception {
        Demo demo = new Demo();
        demo.qpay_merchant();
    }
}
