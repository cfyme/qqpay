package com.qq.qpay.qpay_mch_sp;

@SuppressWarnings("serial")
public class CQpayCloseOrder extends CQpayMchSpBase {

	public CQpayCloseOrder(IQpayMchSpKeyFinder keyFinder) {
		super(keyFinder);
	}

	@Override
	public String getUrl() {
		return "https://qpay.qq.com/cgi-bin/pay/qpay_close_order.cgi";
	}

}
