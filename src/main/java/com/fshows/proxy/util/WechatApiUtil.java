package com.fshows.proxy.util;

import java.util.*;

/**
 * Created by caofangyi on 2017/5/2.
 */
public class WechatApiUtil {

    /**
     * 获取签名
     * @param params
     * @param seceret
     * @return
     */
    public static String getSign(Map<String, Object> params, String seceret) {
        String str = createLinkString(params) + "&key=" + seceret;
        return Md5.digest32Upper(str);
    }

    /**
     * 链接拼接 排序
     * @param params
     * @return
     */
    public static String createLinkString(Map<String, Object> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        try {
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = params.get(key) + "";
                if ("sign".equalsIgnoreCase(key)) {
                    continue;
                }
                prestr = prestr + key + "=" + value + "&";
            }
        } catch (Exception e) {
        }
        return prestr.substring(0, prestr.length() - 1);
    }

    /**
     * 调用微信接口 拼接的xml请求字符串
     * @param parameters
     * @return
     */
    public static String getRequestXml(Map<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
