package com.qq.qpay.qpay_mch_sp;

@SuppressWarnings("serial")
public class CQpayMicroPay extends CQpayMchSpBase {

	public CQpayMicroPay(IQpayMchSpKeyFinder keyFinder) {
		super(keyFinder);
	}

	@Override
	public String getUrl() {
		return "https://qpay.qq.com/cgi-bin/pay/qpay_micro_pay.cgi";
	}
}
