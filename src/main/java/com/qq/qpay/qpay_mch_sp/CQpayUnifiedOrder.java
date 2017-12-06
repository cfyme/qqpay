package com.qq.qpay.qpay_mch_sp;

@SuppressWarnings("serial")
public class CQpayUnifiedOrder extends CQpayMchSpBase {

	public CQpayUnifiedOrder(IQpayMchSpKeyFinder keyFinder) {
		super(keyFinder);
	}

	@Override
	public String getUrl() {
		return "https://qpay.qq.com/cgi-bin/pay/qpay_unified_order.cgi";
	}

}
