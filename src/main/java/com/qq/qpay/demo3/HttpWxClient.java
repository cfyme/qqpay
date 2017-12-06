package com.qq.qpay.demo3;

import com.qq.qpay.demo2.Demo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;

/**
 * 项目：fs-fubei-shop
 * 包名：com.fshows.bank.platform.admin.common.utils.HttpWx
 * 功能：
 * 时间：2017/8/10
 * 作者：PGG
 */
@Service
public class HttpWxClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpWxClient.class);


    /**
     * @param url        请求的url
     * @param data       post json 提交的参数
     */
    public String post(String url, String data) throws IOException {
        return post(url, data, getClient(), requestConfigOasis());
    }

    private String post(String url, String data, CloseableHttpClient httpClient, RequestConfig requestConfig)
            throws IOException {
        StringBuffer sb = new StringBuffer(url);

        //指定url,和http方式
        HttpPost httpPost = new HttpPost(sb.toString());
        httpPost.setConfig(requestConfig);
        //设置类型
        StringEntity se = new StringEntity(data, "utf-8");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(se);
        //请求数据
        return getResponseBody(httpClient, httpPost);
    }

    private String getResponseBody(CloseableHttpClient httpClient, HttpPost httpPost) {
        String responseBody = "";
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                responseBody = EntityUtils.toString(entity);
            }
        } catch (IOException ex) {
            logger.error("http error >> ex = {}", ExceptionUtils.getStackTrace(ex));
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("http close error >> ex = {}", ExceptionUtils.getStackTrace(e));
                }
            }
        }
        return responseBody;
    }

    public CloseableHttpClient getClient() {
        return HttpClients.custom()
                .setConnectionManager(connectionManagerWxScan())
                .build();
    }

    public RequestConfig requestConfigOasis() {
        return RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10000)
                .build();
    }

    public PoolingHttpClientConnectionManager connectionManagerWxScan() {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registryBuilderWxScan());
        connectionManager.setMaxTotal(500);
        connectionManager.setDefaultMaxPerRoute(500);

        return connectionManager;
    }

    public Registry<ConnectionSocketFactory> registryBuilderWxScan() {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslSFWxScan())
                .register("http", new PlainConnectionSocketFactory())
                .build();
    }

    public LayeredConnectionSocketFactory sslSFWxScan() {
        //获取该银行所配置的微信mchId
        //设置个动态获取配置文件的变量
        String bankUrl = "";


        //设置个动态获取配置文件名的变量
        String fileName = "_app";
        //如果存在type值动态获取该文件下的配置文件，如果没有读取默认文件

        LayeredConnectionSocketFactory sslSF = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream instream = null;
            try {
                String certName = "/qqcert/apiclient_cert.p12";
                logger.info("certName={}",certName);
                instream = getClass().getResourceAsStream(certName);
                keyStore.load(instream, Demo.DEMO_MCH_ID.toCharArray());//设置证书密码
            } catch (CertificateException e) {
                logger.info("sslSFWxScan CertificateException", e);
            } catch (Exception e) {
                logger.info("sslSFWxScan exception", e);
            } finally {
                try {
                    instream.close();
                } catch (IOException e) {
                    logger.info("sslSFWxScan error", e);
                }
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, Demo.DEMO_MCH_ID.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);


            return sslsf;
        } catch (Exception e) {
            logger.info("sslSFWxScan error", e);
        }
        return sslSF;
    }
}
