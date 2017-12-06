package com.qq.qpay.qpay_mch_sp;


public class CQpayRefund extends CQpayMchSpBase {

	public CQpayRefund(IQpayMchSpKeyFinder keyFinder) {
		super(keyFinder);
	}
	
	protected boolean needClientPem() {
		return true;
	}

	@Override
	public String getUrl() {
		return "https://api.qpay.qq.com/cgi-bin/pay/qpay_refund.cgi";
	}

}
