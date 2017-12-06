package com.other.demo;

import java.util.HashMap;

import com.qq.qpay.qpay_mch_sp.IQpayMchSpKeyFinder;

public class DemoQpayMchKeyFinder implements IQpayMchSpKeyFinder {
    private static HashMap<String, String> MCH_ID_KEY_MAP= new HashMap<String, String>();
    static{
    	//MCH_ID_KEY_MAP.put(Demo.DEMO_MCH_ID,"8934e7d15453e97507ef794cf7b0519d");
		MCH_ID_KEY_MAP.put(Demo.DEMO_MCH_ID, "cb573f187a46cc13779002d032d62f71");

	}
	@Override
	public String getKey(String mch_id) {
		return MCH_ID_KEY_MAP.get(mch_id);
	}

}
