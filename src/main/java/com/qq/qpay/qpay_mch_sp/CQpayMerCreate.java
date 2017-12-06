package com.qq.qpay.qpay_mch_sp;

/**
 * project: qpay_mch_sp_demo
 * package: com.qq.qpay.qpay_mch_sp
 * todo:
 * author: yanghao
 * date: 2017/9/27.
 */
public class CQpayMerCreate extends CQpayMchSpBase{
   public CQpayMerCreate(IQpayMchSpKeyFinder keyFinder) {
            super(keyFinder);
        }

    protected boolean needClientPem() {
        return true;
    }

    @Override
    public String getUrl() {
        // return "https://api.qpay.qq.com/cgi-bin/merchant/qpay_submch_add.cgi";
        return "https://api.qpay.qq.com/cgi-bin/merchant/qpay_submch_manage.cgi";

    }
}
