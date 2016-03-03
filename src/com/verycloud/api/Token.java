package com.verycloud.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.verycloud.client.Request;
import com.verycloud.util.EncryptUtil;

public class Token {
  
  /**
   * @var String 用户名
   */
  private static final String USERNAME = "wangjianhong668";
  
  /**
   * @var String 密码
   */
  private static final String PASSWORD = "wang1985";
  
  /**
   * @var String token
   */
  private static String token = "";
  
  /**
   * @var long token过期时间
   */
  private static long expires = 0;
  
  /**
   * 获取token
   * @return
   */
  public String token() {
    long now = System.currentTimeMillis() / 1000;
    if(!StringUtils.isEmpty(Token.token) && Token.expires > now) {
      return Token.token;
    }
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("username", Token.USERNAME);
    try {
      map.put("password", EncryptUtil.encrypt(Token.PASSWORD, "verycloud#cryptpass"));
    }
    catch(Exception e) {
      e.printStackTrace();
      return "";
    }
    
    String url = Request.API_URL +  "/API/OAuth/authorize";  // 接口地址
    
    Map<String, Object> result = null;
    
    try {
      result = Request.doPost(url, map);
    }
    catch(Exception e) {
      e.printStackTrace();
      return "";
    }
    
    if(result != null && result.containsKey("code") && ((int) result.get("code")) == 1) {
      Token.token = (String) result.get("access_token");
      Token.expires = Long.parseLong((String) result.get("expires"));
      return Token.token;
    }
    
    return "";
    
  }
  
}
