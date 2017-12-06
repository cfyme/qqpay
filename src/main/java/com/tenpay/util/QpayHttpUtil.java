package com.tenpay.util;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * project: liquidator-platform-openapi
 * package: com.fshows.liquidator.platform.openapi.common.qpay
 * todo:
 * author: yanghao
 * date: 2017/9/29.
 */
public class QpayHttpUtil {


    public static final String SunX509 = "SunX509";
    public static final String JKS = "JKS";
    public static final String PKCS12 = "PKCS12";
    public static final String TLS = "TLS";


    /**
     *
     * @param trustPasswd
     * @param keyPasswd
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    public static SSLContext getSSLContext(
            FileInputStream trustFileInputStream, String trustPasswd,
            FileInputStream keyFileInputStream, String keyPasswd)
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException, UnrecoverableKeyException,
            KeyManagementException {

        // ca
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(QpayHttpUtil.SunX509);
        KeyStore trustKeyStore = KeyStore.getInstance(QpayHttpUtil.JKS);
        trustKeyStore.load(trustFileInputStream, QpayHttpUtil
                .str2CharArray(trustPasswd));
        tmf.init(trustKeyStore);

        final char[] kp = QpayHttpUtil.str2CharArray(keyPasswd);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(QpayHttpUtil.SunX509);
        KeyStore ks = KeyStore.getInstance(QpayHttpUtil.PKCS12);
        ks.load(keyFileInputStream, kp);
        kmf.init(ks, kp);

        SecureRandom rand = new SecureRandom();
        SSLContext ctx = SSLContext.getInstance(QpayHttpUtil.TLS);
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

        return ctx;
    }

    public static SSLContext getSSLContext1(
            FileInputStream keyFileInputStream, String keyPasswd)
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException, UnrecoverableKeyException,
            KeyManagementException {

        final char[] kp = QpayHttpUtil.str2CharArray(keyPasswd);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(QpayHttpUtil.SunX509);
        KeyStore ks = KeyStore.getInstance(QpayHttpUtil.PKCS12);
        ks.load(keyFileInputStream, kp);
        kmf.init(ks, kp);

        SecureRandom rand = new SecureRandom();
        SSLContext ctx = SSLContext.getInstance(QpayHttpUtil.TLS);
        ctx.init(kmf.getKeyManagers(), null, rand);

        return ctx;
    }

    /**
     * ��ȡCA֤����Ϣ
     * @param cafile CA֤���ļ�
     * @return Certificate
     * @throws CertificateException
     * @throws IOException
     */
    public static Certificate getCertificate(File cafile)
            throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cafile);
        Certificate cert = cf.generateCertificate(in);
        in.close();
        return cert;
    }

    /**
     * �ַ���ת����char����
     * @param str
     * @return char[]
     */
    private static char[] str2CharArray(String str) {
        if(null == str) return null;

        return str.toCharArray();
    }

    /**
     * �洢ca֤���JKS��ʽ
     * @param cert
     * @param alias
     * @param password
     * @param out
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    public static void storeCACert(Certificate cert, String alias,
                                   String password, OutputStream out) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = KeyStore.getInstance("JKS");

        ks.load(null, null);

        ks.setCertificateEntry(alias, cert);

        // store keystore
        ks.store(out, QpayHttpUtil.str2CharArray(password));

    }
}

