package com.kikipig.util.sdk;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kikipig.util.StringUtil;
import com.kikipig.vo.ResultMsgBean;

import net.sf.json.JSONObject;


/**
 * 云之讯发送短信工具类
 * @author phd
 * @date 2017年7月1日10:15:32. 
 */
public class YzxUtil {
	/**账户Id.*/
	private static final String ACCOUNT_SID = "0e3d61c43ef86c0c90620090ccbbaf0a";
	/**账户授权令牌.*/
	private static final String ACCOUNT_TOKEN = "b51c3d77935ff6f358d1b862fed1b1c6";
	/**应用的APPid.*/
	private static final String APP_ID = "53dd07d4f79d4f05bcfa54a030d047c0";
	/**请求的时间格式.*/
	private static final String REQUEST_DATE_FORMAT = "yyyyMMddHHmmss";
	/**请求的地址.*/
	private static final String REQUEST_URL = "https://api.ucpaas.com/2014-06-30/Accounts/"+ACCOUNT_SID+"/Messages/templateSMS?sig=";
	
	private static final Logger LOG = LogManager.getLogger(YzxUtil.class);
	/**
	 * 发送短信通知
	 * @param templeId 短信模板
	 * @param content 短信内容
	 * @param to 接受者
	 * @return {@link ResultMsgBean}.
	 */
	public static ResultMsgBean sendSms(final String templeId, final String [] content, final String [] to){
		LOG.info("[sendSms request||content="+Arrays.toString(content)+",to="+Arrays.toString(to)+",templeId="+templeId+"]");
//		return new ResultMsgBean(ResultMsgBean.SUCCESS,"发送成功");
		String response = https_post(formatRequestParams(templeId, content, to));
		return parseSendResponse(response);
	}
	
	private static ResultMsgBean parseSendResponse(final String response){
		if(StringUtils.isBlank(response)){
			return new ResultMsgBean("返回数据为空");
		}
		try{
			JSONObject resultJson = JSONObject.fromObject(response);
			JSONObject json = resultJson.getJSONObject("resp");
			if(!json.getString("respCode").equals("000000")){
				LOG.info("[send sms fail||respCode="+json.getString("respCode")+"]");
				return new ResultMsgBean("发送失败");
			}
			return new ResultMsgBean(ResultMsgBean.SUCCESS,"发送成功");
		}catch(Exception e){
			LOG.error("[parseSendResponse error||response="+response+"]",e);
			return new ResultMsgBean("解析返回数据出错");
		}
	}
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		JSONObject jsonContent = new JSONObject();
		jsonContent.accumulate("respCode", "000000");
		jsonContent.accumulate("failure", 1);
		jsonContent.accumulate("respCode", "000000");
		jsonContent.accumulate("respCode", "000000");
		json.accumulate("resp", jsonContent);
		String res = "{\"resp\": {\"respCode\": \"000000\",\"failure\": 1,\"templateSMS\": {\"createDate\": 20140623185016,\"smsId\": \"f96f79240e372587e9284cd580d8f953\"}}";
//		System.out.println(parseSendResponse(res));
		System.out.println(sendSms("85657", new String[]{"我的坑1","我的排拍卖1","网上竞1价","500000",DateFormatUtils.format(DateUtils.addDays(new Date(), 3), "yyyy-MM-dd HH:mm")}, new String[]{"13823289537"}));
	}
	
	private static String formatRequestParams(final String templeId, final String [] content, final String [] to){
		JSONObject json = new JSONObject();
		json.accumulate("appId", APP_ID);
		json.accumulate("param", StringUtils.join(content, ","));
		json.accumulate("templateId", templeId);
		json.accumulate("to", StringUtils.join(to, ","));
		return "{\"templateSMS\":"+json.toString()+"}";
	}
	
	private static String getSignature(final String timestamp){
		String sig = ACCOUNT_SID + ACCOUNT_TOKEN + timestamp;
		return EncryptUtil.md5Digest(sig);
	}
	
	private static CloseableHttpClient httpClient = createSSLClientDefault();

	private static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			LOG.error("[createSSLClientDefault exception||msg="+StringUtil.getTrace(e)+"]");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("[createSSLClientDefault exception||msg="+StringUtil.getTrace(e)+"]");
		} catch (KeyStoreException e) {
			LOG.error("[createSSLClientDefault exception||msg="+StringUtil.getTrace(e)+"]");
		}
		return HttpClients.createDefault();
	}
	private static String https_post(final String body) {
		Date reqDate = new Date();
		if (null == httpClient){
			httpClient = createSSLClientDefault();
		}
		final String timestamp = DateFormatUtils.format(reqDate, REQUEST_DATE_FORMAT);
		String requestUrl = REQUEST_URL.concat(getSignature(timestamp));
		CloseableHttpClient client = createSSLClientDefault();
		String responseText = "";
		HttpPost httpPost = new HttpPost(requestUrl);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();// 设置请求超时时间
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
		httpPost.setHeader("Authorization", EncryptUtil.base64Encoder(ACCOUNT_SID+":"+timestamp));
		HttpEntity entity = null;
		CloseableHttpResponse response = null;
		try {
			BasicHttpEntity requestBody = new BasicHttpEntity();
	        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
	        requestBody.setContentLength(body.getBytes("UTF-8").length);
			httpPost.setEntity(requestBody);
			response = client.execute(httpPost);
			if(response == null){
				return responseText;
			}
			entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			LOG.error("[https_post exception||msg="+StringUtil.getTrace(e)+"]");
		} catch (IOException e) {
			LOG.error("[https_post exception||msg="+StringUtil.getTrace(e)+"]");
		}finally{
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LOG.error("[https_post close response exception msg="+StringUtil.getTrace(e)+"]");
				}
			}
			if(client != null){
				try {
					client.close();
				} catch (IOException e) {
					LOG.error("[https_post close client exception msg="+StringUtil.getTrace(e)+"]");
				}
			}
		}
		LOG.info("[https_post||result="+responseText+"]");
		return responseText;
	}
}
