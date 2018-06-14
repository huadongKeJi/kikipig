package com.kikipig.util.sdk;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kikipig.vo.ResultMsgBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * 云通讯发送短信工具类.
 */
public class YtxUtil {
	private static final Logger LOG = LogManager.getLogger(YtxUtil.class);
	private static final String TemplateSMS = "SMS/TemplateSMS";
	private static final String SERVER_IP = "app.cloopen.com";
	private static final String SERVER_PORT = "8883";
	private static final String ACCOUNT_SID = "8aaf070855c4a7270155c966a7d10782";
	private static final String ACCOUNT_TOKEN = "0dfa22cf684a4848bb7da19c2197112d";
	private static final String App_ID = "8aaf070855c4a7270155c966a8330788";
	
	public static ResultMsgBean sendSms(final String templateId,  final String[] datas, final String to) {
		LOG.info("[sendSms request||to="+to+",templateId="+templateId+",datas="+Arrays.toString(datas)+"]");
		return https_post(buildRequestJsonBody(templateId, datas, to));
	}
	
	private static String buildRequestJsonBody(final String templateId,  final String[] datas, final String to){
		JSONObject requestJson = new JSONObject();
		requestJson.accumulate("appId", App_ID);
		requestJson.accumulate("to", to);
		requestJson.accumulate("templateId", templateId);
		if (datas != null) {
			JSONArray paramsArr = new JSONArray();
			for (String s : datas) {
				paramsArr.add(s);
			}
			requestJson.accumulate("datas", paramsArr);
		}
		return requestJson.toString();
	}
	private static ResultMsgBean https_post(final String requestJsonBody){
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = getHttpSSLClient(SERVER_IP, "TLS", NumberUtils.toInt(SERVER_PORT), "https");
//			httpclient = registerSSL(SERVER_IP, "TLS", NumberUtils.toInt(SERVER_PORT), "https");
		} catch (Exception e1) {
			throw new RuntimeException("初始化httpclient异常" + e1.getMessage());
		}
		String responseText = "";
		HttpEntity entity;
		try {
			HttpPost httppost = (HttpPost) getHttpRequestBase(TemplateSMS);
			BasicHttpEntity requestBody = new BasicHttpEntity();
			requestBody.setContent(new ByteArrayInputStream(requestJsonBody.getBytes("UTF-8")));
			requestBody.setContentLength(requestJsonBody.getBytes("UTF-8").length);
			httppost.setEntity(requestBody);
			response = httpclient.execute(httppost);
			if(response == null){
				return new ResultMsgBean(172002, "无返回");
			}
			entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
			LOG.info("[sendSMS response||responseText="+responseText+"]");
		} catch (IOException e) {
			return new ResultMsgBean(172001, "网络错误");
		} catch (Exception e) {
			return new ResultMsgBean(172002, "无返回");
		} finally {
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
				}
			}
			if (httpclient != null)
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
//		LoggerUtil.info("sendTemplateSMS response body = " + result);
		try {
			return parseResultJson(responseText);
		} catch (Exception e) {
		}
		return new ResultMsgBean(172003, "返回包体错误");
	}
	private static ResultMsgBean parseResultJson(String resultText){
		JSONObject resultJson = JSONObject.fromObject(resultText);
		if(!resultJson.containsKey("statusCode")){
			return new ResultMsgBean(172003, "返回包体错误");
		}
		if(resultJson.getString("statusCode").equals("000000")){
			return new ResultMsgBean(ResultMsgBean.SUCCESS,"发送成功",resultJson);
		}
		return new ResultMsgBean(NumberUtils.toInt(resultJson.getString("statusCode")), resultJson.containsKey("statusMsg") ? resultJson.getString("statusMsg"):"发送失败");
  }
	private static StringBuffer getBaseUrl() {
		return new StringBuffer("https://").append(SERVER_IP).append(":").append(SERVER_PORT).append("/2013-12-26");
	}
	private static HttpRequestBase getHttpRequestBase(String action) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		String signature = EncryptUtil.md5Digest(ACCOUNT_SID + ACCOUNT_TOKEN + timestamp).toUpperCase();
		String url = getBaseUrl().append("/Accounts/").append(ACCOUNT_SID).append("/" + action + "?sig=").append(signature).toString();
		HttpRequestBase mHttpRequestBase = null;
		mHttpRequestBase = new HttpPost(url);
		setHttpHeader(mHttpRequestBase);
		mHttpRequestBase.setHeader("Authorization", EncryptUtil.base64Encoder(ACCOUNT_SID + ":" + timestamp));
		return mHttpRequestBase;
	}

	private static void setHttpHeader(AbstractHttpMessage httpMessage) {
		httpMessage.setHeader("Accept", "application/json");
		httpMessage.setHeader("Content-Type", "application/json;charset=utf-8");
	}

	private static CloseableHttpClient getHttpSSLClient(String hostname, String protocol, int port, String scheme)throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext ctx = SSLContext.getInstance(protocol);
		X509TrustManager tm = new X509TrustManager(){
			public void checkClientTrusted(X509Certificate[] chain,String authType)throws java.security.cert.CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain,String authType)throws java.security.cert.CertificateException {
				if ((chain == null) || (chain.length == 0))
					throw new IllegalArgumentException("null or zero-length certificate chain");
				if ((authType == null) || (authType.length() == 0))
					throw new IllegalArgumentException("null or zero-length authentication type");
				boolean br = false;
				Principal principal = null;
				for (X509Certificate x509Certificate : chain) {
					principal = x509Certificate.getSubjectX500Principal();
					if (principal != null) {
						br = true;
						return;
					}
				}
				if (!(br)){
					throw new CertificateException("服务端证书验证失败！");
				}
			}
			public X509Certificate[] getAcceptedIssuers(){
				return new X509Certificate[0];
			}
		};
		ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx);
//		return HttpClients.custom().setSSLSocketFactory(sslsf).setProxy(new HttpHost(hostname, port, scheme)).build();
		SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = new DefaultHttpClient();
		Scheme sch = new Scheme(scheme, port, socketFactory);
		httpclient.getConnectionManager().getSchemeRegistry().register(sch);
		return httpclient;
	}
}