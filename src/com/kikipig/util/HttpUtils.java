package com.kikipig.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 发送HTTP请求工具类
 * @date 2017年6月14日16:39:40.
 */
public class HttpUtils {
	/**日记记录.*/
	private final static Logger LOG = LogManager.getLogger(HttpUtils.class);
	
	/**
	 * 发送POST请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return {@link String}.
	 */
	public static final String post(final String url, final Map<String, String> params){
		LOG.info("[post request||url="+url+",params="+params+"]");
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(buildPair(params));
		return commonRequest(url, httpPost);
	}
	/**
	 * 发送POST请求
	 * @param url 请求地址
	 * @param xmlStr 接受XML内容
	 * @return {@link String}.
	 */
	public static final String post(final String url, final String xmlStr){
		LOG.info("[post xml request||url="+url+",xmlStr="+xmlStr+"]");
		StringEntity postEntity = new StringEntity(xmlStr, "UTF-8");
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		return commonRequest(url, httpPost);
	}
	/**
	 * 公用的请求类
	 * @param url 请求的地址
	 * @param httpPost post请求
	 * @return {@link String}.
	 */
	private static String commonRequest(final String url, final HttpPost httpPost){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String result = StringUtils.EMPTY;
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				result = EntityUtils.toString(entity, Charset.forName("UTF-8"));
				LOG.info("[post response||result="+result+"]");
				EntityUtils.consume(entity);
			}
			if(response != null){
				response.close();
			}
		} catch (IOException e) {
			LOG.error("[post request exception||url="+url+"]",e);
		} finally{
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					LOG.error("[post close httpClient exception]",e);
				}
			}
		}
		return result;
	}
	/**
	 * 构建form参数 
	 * @param paramMap 参数
	 * @return {@link UrlEncodedFormEntity}.
	 */
	private static UrlEncodedFormEntity buildPair(final Map<String, String> paramMap){
		if(MapUtils.isEmpty(paramMap)){
			return null;
		}
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		Iterator<String> iterator = paramMap.keySet().iterator();
		String key = null;
		String value = null;
		while(iterator.hasNext()){
			key = iterator.next();
			value = paramMap.get(key);
			formParams.add(new BasicNameValuePair(key, value));
		}
		UrlEncodedFormEntity result = null;
		try {
			result = new UrlEncodedFormEntity(formParams);
		} catch (UnsupportedEncodingException e) {
			LOG.error("[buildPair exception||paramMap="+paramMap.toString()+"]",e);
		}
		return result;
	}	
}