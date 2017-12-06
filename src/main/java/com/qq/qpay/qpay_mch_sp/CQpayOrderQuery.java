package com.qq.qpay.qpay_mch_sp;

public class CQpayOrderQuery extends CQpayMchSpBase {

	public CQpayOrderQuery(IQpayMchSpKeyFinder keyFinder) {
		super(keyFinder);
	}

	@Override
	public String getUrl() {
		return "https://qpay.qq.com/cgi-bin/pay/qpay_order_query.cgi";
	}

}
