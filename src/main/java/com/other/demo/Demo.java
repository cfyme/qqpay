package com.other.demo;

import java.io.File;

import com.qq.qpay.qpay_mch_sp.*;

public class Demo {
	//public static final String DEMO_MCH_ID = "1900000109";
	public static final String DEMO_MCH_ID = "1489217791";
	public static final String DEMO_SUB_MCH_ID = "1900005911";

	public static final String DEMO_OUT_TRADE_NO = "20160512161914_BBC_cfyme";
	public static final String CERT_FILE = "/qqcert/apiclient_cert.p12";
	public static final String CA_FILE = "/qqcert/api_qpay_qq_com_ca.crt";

	public void qpay_close_order() throws Exception {
		CQpayCloseOrder obj = new CQpayCloseOrder(new DemoQpayMchKeyFinder());
		obj.set("mch_id", DEMO_MCH_ID);
		obj.set("nonce_str", "b49b24740506ac9b5e36aadccd8237fe");
		obj.set("out_trade_no", DEMO_OUT_TRADE_NO);
		obj.set("sub_mch_id", "1900005911");

		obj.setMch_id(DEMO_MCH_ID);
		System.out.println("=========================qpay_close_order=======");
		System.out.println(obj.getPostXml());
		System.out.println(obj.call());
		System.out.println(obj.checkSign(obj.parseResponse()));
	}

	public void qpay_micro_pay() throws Exception {
		CQpayMicroPay obj = new CQpayMicroPay(new DemoQpayMchKeyFinder());

		obj.set("mch_id", DEMO_MCH_ID);
		obj.set("nonce_str", "b49b24740506ac9b5e36aadccd8237fe");
		obj.set("out_trade_no", DEMO_OUT_TRADE_NO+"A");
		obj.set("sub_mch_id", "1900005911");
		obj.set("body", "body_test_中文");
		obj.set("device_info", "WP00000001");
		obj.set("fee_type", "CNY");
		obj.set("sub_mch_id", "1900005911");
		obj.set("notify_url", "http://10.222.146.71:80/success.xml");
		obj.set("spbill_create_ip", "127.0.0.1");
		obj.set("total_fee", "1");
		obj.set("trade_type", "MICROPAY");
		obj.set("auth_code","910728310408849937");
		obj.setMch_id(DEMO_MCH_ID);
		System.out
				.println("=========================qpay_unified_order=======");
		System.out.println(obj.getPostXml());
		System.out.println(obj.call());
		System.out.println(obj.checkSign(obj.parseResponse()));
	}

	public void qpay_unified_order() throws Exception {
		CQpayUnifiedOrder obj = new CQpayUnifiedOrder(
				new DemoQpayMchKeyFinder());

		obj.set("mch_id", DEMO_MCH_ID);
		obj.set("nonce_str", "b49b24740506ac9b5e36aadccd8237fe");
		obj.set("out_trade_no", DEMO_OUT_TRADE_NO);
		obj.set("sub_mch_id", "1900005911");
		obj.set("body", "body_test_中文");
		obj.set("device_info", "WP00000001");
		obj.set("fee_type", "CNY");
		obj.set("sub_mch_id", "1900005911");
		obj.set("notify_url", "http://10.222.146.71:80/success.xml");
		obj.set("spbill_create_ip", "127.0.0.1");
		obj.set("total_fee", "1");
		obj.set("trade_type", "NATIVE");

		obj.setMch_id(DEMO_MCH_ID);
		System.out
				.println("=========================qpay_unified_order=======");
		System.out.println(obj.getPostXml());
		System.out.println(obj.call());
		System.out.println(obj.checkSign(obj.parseResponse()));
	}

	public void qpay_order_query() throws Exception {
		CQpayOrderQuery obj = new CQpayOrderQuery(new DemoQpayMchKeyFinder());

		obj.set("mch_id", DEMO_MCH_ID);
		obj.set("nonce_str", "b49b24740506ac9b5e36aadccd8237fe");
		obj.set("out_trade_no", DEMO_OUT_TRADE_NO);
		obj.set("sub_mch_id", "1900005911");
		obj.setMch_id(DEMO_MCH_ID);
		System.out.println("=========================qpay_order_query=======");
		System.out.println(obj.getPostXml());
		System.out.println(obj.call());
		System.out.println(obj.checkSign(obj.parseResponse()));
	}

	public void qpay_refund() throws Exception {
		CQpayRefund obj = new CQpayRefund(new DemoQpayMchKeyFinder());
		obj.set("mch_id", DEMO_MCH_ID);
		obj.set("nonce_str", "b49b24740506ac9b5e36aadccd8237fe");
		obj.set("out_trade_no", DEMO_OUT_TRADE_NO);
		obj.set("sub_mch_id", "1900005911");
		obj.set("out_refund_no", DEMO_OUT_TRADE_NO + "_out_refund_1");
		obj.set("refund_fee", "1");
		obj.set("op_user_id", "1900005911");
		obj.set("op_user_passwd", "");

		/*
		 * 设置证书
		 */
		obj.setCertInfo(CERT_FILE, "532398");
		obj.setCaInfo(CA_FILE);

		System.out.println("=========================qpay_refund=======");
		System.out.println(obj.getPostXml());
		System.out.println(obj.call());
		System.out.println(obj.checkSign(obj.parseResponse()));
	}

	public void qpay_reverse() throws Exception {
		CQpayReverse obj = new CQpayReverse(new DemoQpayMchKeyFinder());
		obj.set("mch_id", DEMO_MCH_ID);
		obj.set("nonce_str", "b49b24740506ac9b5e36aadccd8237fe");
		obj.set("out_trade_no", DEMO_OUT_TRADE_NO);
		obj.set("sub_mch_id", "1900005911");
		obj.set("op_user_id", "1900005911");
		obj.set("op_user_passwd", "");


		/*
		 * 设置证书
		 */
		obj.setCertInfo(CERT_FILE, "532398");
		obj.setCaInfo(CA_FILE);

		System.out.println("=========================qpay_reverse=======");
		System.out.println(obj.getPostXml());
		System.out.println(obj.call());
		System.out.println(obj.checkSign(obj.parseResponse()));
	}


	public void qpay_merchant_create() throws Exception {
		CQpayMerCreate obj = new CQpayMerCreate(new DemoQpayMchKeyFinder());

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
		obj.setCertInfo(CERT_FILE, DEMO_MCH_ID);
		System.out
				.println("===============qpay_merchant_create=======");
		System.out.println(obj.getPostXml());
		System.out.println(obj.call());
		System.out.println(obj.checkSign(obj.parseResponse()));
	}

	//商户名称
	public static String QPAY_MCH_NAME = "平安银行股份有限公司温州分行";

	//商户别称
	public static String QPAY_MCH_SHORT_NAME = "平安银行QQ钱包商户";

	public static void main(String[] args) throws Exception {
		Demo demo = new Demo();
//		demo.qpay_micro_pay();
//		demo.qpay_unified_order();
//		demo.qpay_order_query();
//		demo.qpay_close_order();
//		demo.qpay_refund();
//		demo.qpay_reverse();
//		demo.qpay_order_query();

		demo.qpay_merchant_create();
	}
}
