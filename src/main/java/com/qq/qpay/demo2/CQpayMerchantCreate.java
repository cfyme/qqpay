package com.qq.qpay.demo2;

import com.qq.qpay.qpay_mch_sp.IQpayMchSpKeyFinder;

/**
 * project: qpay_mch_sp_demo
 * package: com.qq.qpay.qpay_mch_sp
 * todo:
 * author: yanghao
 * date: 2017/11/13.
 */
public class CQpayMerchantCreate extends Base2 {
    public CQpayMerchantCreate(IQpayMchSpKeyFinder keyFinder) {
        super(keyFinder);
    }

    protected boolean needClientPem() {
        return true;
    }

    @Override
    public String getUrl() {
        return "https://api.qpay.qq.com/cgi-bin/merchant/qpay_submch_manage.cgi";
    }
}
