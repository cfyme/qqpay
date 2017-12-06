//package com.fshows.proxy.util;
//
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.ssl.SSLContexts;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.net.ssl.SSLContext;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//
///**
// * 项目：fs-fubei-shop
// * 包名：com.fshows.fubei.shop.config.http
// * 功能：
// * 时间：2016-11-04
// * 作者：呱牛
// */
//@Configuration
//public class HttpConfiguration {
//
//
//    @Bean
//    public LayeredConnectionSocketFactory sslSF() {
//
//        String mchStr = "1481502132";
//
//        //online
//        mchStr="1377688802";
//
//
//        LayeredConnectionSocketFactory sslSF = null;
//        try {
//            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            InputStream instream = null;
//            try {
//               // instream = new FileInputStream(new File("f:/certificate/apiclient_cert-product.p12"));
//                //instream = getClass().getResourceAsStream("/certificate/apiclient_cert.p12");
//               // instream = new FileInputStream(new File("f:/cert/apiclient_cert.p12"));
//
//                instream = getClass().getResourceAsStream("/certificate/apiclient_cert-product.p12");
//                keyStore.load(instream, mchStr.toCharArray());//设置证书密码
//            } catch (CertificateException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    instream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            // Trust own CA and all self-signed certs
//            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchStr.toCharArray())
//                    .build();
//            // Allow TLSv1 protocol only
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                    sslcontext,
//                    new String[]{"TLSv1"},
//                    null,
//                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//
//
//            return sslsf;
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return sslSF;
//    }
//
//    @Bean
//    public Registry<ConnectionSocketFactory> registryBuilder() {
//        return RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("https", sslSF())
//                .register("http", new PlainConnectionSocketFactory())
//                .build();
//    }
//
//    @Bean
//    public PoolingHttpClientConnectionManager connectionManager() {
//
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registryBuilder());
//        connectionManager.setMaxTotal(500);
//        connectionManager.setDefaultMaxPerRoute(500);
//
//        return connectionManager;
//    }
//
//
//
//    @Bean
//    public CloseableHttpClient httpClient() {
//        return HttpClients.custom()
//                .setConnectionManager(connectionManager())
//              //  .setSSLSocketFactory(sslsf)
//                .build();
//    }
//
//    @Bean
//    public RequestConfig requestConfig() {
//        return RequestConfig.custom()
//                .setConnectTimeout(10000)
//                .setConnectionRequestTimeout(500)
//                .setSocketTimeout(10000)
//                .build();
//    }
//
//    @Bean(destroyMethod = "shutdown")
//    public IdleConnectionEvictor idleConnectionEvictor() {
//        return new IdleConnectionEvictor(connectionManager());
//    }
//}
