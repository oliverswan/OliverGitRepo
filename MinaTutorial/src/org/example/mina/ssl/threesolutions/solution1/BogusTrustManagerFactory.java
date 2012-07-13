package org.example.mina.ssl.threesolutions.solution1;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;

public class BogusTrustManagerFactory extends TrustManagerFactory {
	
	// 使用本类来产生TrustManager
	public BogusTrustManagerFactory() {
		super(new BogusTrustManagerFactorySpi(), new Provider("MinaBogus", 1.0D, "") {
			private static final long serialVersionUID = -4024169055312053827L;
		}, "MinaBogus");
	}
	
	// 用来判断证书是否能够被信任
	static final X509TrustManager X509 = new X509TrustManager() {

		/**
		 * 验证客户端证书是否可被信任
		 */
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

			if (x509Certificates == null || x509Certificates.length == 0)
				throw new IllegalArgumentException("null or zero-length certificate chain");
			if (s == null || s.length() == 0)
				throw new IllegalArgumentException("null or zero-length authentication type");

			boolean br = false;
			Principal principal = null;
			for (X509Certificate x509Certificate : x509Certificates) {
				principal = x509Certificate.getSubjectDN();
				// 验证客户端证书，在这里只是简单的验证一下客户端证书CN是否为Alice或Bob
				if (principal != null && (StringUtils.contains(principal.getName(), "Alice") || StringUtils.contains(principal.getName(), "Bob"))) {
					br = true;
					return;
				}
			}

			if (!br) {
				throw new CertificateException("连接认证失败！");
			}
		}

		/**
		 * 验证服务器是否可被信任
		 */
		public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			if (x509Certificates == null || x509Certificates.length == 0)
				throw new IllegalArgumentException("null or zero-length certificate chain");
			if (s == null || s.length() == 0)
				throw new IllegalArgumentException("null or zero-length authentication type");

			boolean br = false;
			Principal principal = null;
			for (X509Certificate x509Certificate : x509Certificates) {
				principal = x509Certificate.getSubjectDN();
				// 里只是简单的验证一下服务端的CN是否为sundoctor.com
				if (principal != null && (StringUtils.contains(principal.getName(), "sundoctor.com"))) {
					br = true;
					return;
				}
			}

			if (!br) {
				throw new CertificateException("连接认证失败！");
			}
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	};

	static final TrustManager[] X509_MANAGERS = new TrustManager[] { X509 };

	private static class BogusTrustManagerFactorySpi extends TrustManagerFactorySpi {

		protected TrustManager[] engineGetTrustManagers() {
			return BogusTrustManagerFactory.X509_MANAGERS;
		}

		protected void engineInit(KeyStore keystore1) throws KeyStoreException {
		}

		protected void engineInit(ManagerFactoryParameters managerfactoryparameters)
				throws InvalidAlgorithmParameterException {
		}

		private BogusTrustManagerFactorySpi() {
		}

	}

}
