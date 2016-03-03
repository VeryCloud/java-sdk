package com.verycloud.client;

import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import com.verycloud.util.Convertor;

/**
 * httpclient工具类，用来发起post请求
 * @author verycloud
 *
 */
public class Request {
  
  /**
   * @var String API URL
   */
  public static final String API_URL = "https://api3.verycloud.cn";
	
  /**
   * post请求
   * @param url
   * @param params
   * @return
   */
	public static Map<String, Object> doPost(String url, Map<String, Object> params) throws Exception {
		Map<String, Object> result = null;
		HttpClient httpClient = new SSLClient();
    HttpPost httpPost = new HttpPost(url);
    
		StringEntity entity = new StringEntity(Convertor.map2json(params), "utf-8");
    entity.setContentEncoding("UTF-8");    
    entity.setContentType("application/json");    
    httpPost.setEntity(entity);  
    
		
		HttpResponse response = httpClient.execute(httpPost);
		if(response != null) {
			HttpEntity resEntity = response.getEntity();
			if(resEntity != null) {
				result = Convertor.json2map(EntityUtils.toString(resEntity, "utf-8"));
			}
		}
		
		return result;
	}
	
}
