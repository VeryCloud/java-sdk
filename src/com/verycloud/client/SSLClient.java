package com.verycloud.client;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * SSL连接客户端，继承自DefaultHttpClient，用来绕过ssl双向验证
 * @author verycloud
 *
 */

public class SSLClient extends DefaultHttpClient {
	/**
	 * 构造方法
	 * @throws Exception
	 */
	public SSLClient() throws Exception {
      super();
      SSLContext ctx = SSLContext.getInstance("TLS");
      X509TrustManager tm = new X509TrustManager() {
	      @Override
	      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	      }
	      
	      @Override
	      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	      }
	      
	      @Override
	      public X509Certificate[] getAcceptedIssuers() {
	        return null;
	      }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = this.getConnectionManager();
      SchemeRegistry sr = ccm.getSchemeRegistry();
      sr.register(new Scheme("https", 443, ssf));
    }
}