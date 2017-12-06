package com.qq.qpay.qpay_mch_sp;

public class CQpayReverse extends CQpayRefund {

	public CQpayReverse(IQpayMchSpKeyFinder keyFinder) {
		super(keyFinder);
	}

	@Override
	public String getUrl() {
		return "https://api.qpay.qq.com/cgi-bin/pay/qpay_reverse.cgi";
	}

}
