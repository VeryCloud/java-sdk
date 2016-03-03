package com.verycloud.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.verycloud.client.Request;

public class CDNAPI {
  /**
   * �û�CDN����
   * @return Map<String, Object>
   */
  public Map<String, Object> profileDetail() {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/profileDetail";
    
    Map<String, Object> params = new HashMap<String, Object>() {
      {
        put("token", token);
      }
    };
    
    try {
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
    
  }
  
  /**
   * Ƶ���б�
   * @param data
   * @return
   */
  public Map<String, Object> domainList(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainList";
    
    try {
      // ��ҳ��Ϣ
      int page = data.containsKey("page") && (int) data.get("page") > 0 ? (int) data.get("page") : 1;
      int limit = data.containsKey("limit") && (int) data.get("limit") > 0 ? (int) data.get("limit") : 30;
      //filter == 1 ��ȡ��������־���ص�Ƶ��
      int filter = data.containsKey("filter") && (int) data.get("filter") > 0 ? (int) data.get("filter") : 0;
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("page", page);
          put("limit", limit);
        }
      };
      
      if(filter == 1) {
        params.put("filter", filter);
      }
      
      //��ID��ѯ
      int id = data.containsKey("id") && (int) data.get("id") > 0 ? (int) data.get("id") : 0;
      if(id > 0) {
        params.put("id", id);
      }
      
      //���±��ѯ
      int mark = data.containsKey("mark") && (int) data.get("mark") > 0 ? (int) data.get("mark") : 99;
      if(mark == 1) {
        params.put("mark", mark);
      }
      
      //���������Ͳ�ѯ ȡֵ��Χ[cloud(�Ʒַ�),download(����),hls(HLS),music(����),rtmp(RTMP),static(��̬)]
      String type = data.containsKey("type") && !"".equals((String) data.get("type")) ? (String) data.get("type") : "";
      if(!"".equals(type)) {
        params.put("type", type);
      }
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ����Ƶ��
   * @param data
   * @return
   */
  public Map<String, Object> domainAdd(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainAdd";
    
    try {
      //Ƶ������
      String name = data.containsKey("name") && StringUtils.isNotEmpty((String) data.get("name"))  ? (String) data.get("name") : "";
      //�������ͣ�ȡֵ��Χ[cloud(�Ʒַ�),download(����),hls(HLS),music(����),rtmp(RTMP),static(��̬)]
      String type = data.containsKey("type") && StringUtils.isNotEmpty((String) data.get("type"))  ? (String) data.get("type") : "";
      //Դվ ��{"1.1.1.1": "80", "2.2.2.2": "80"}
      Map<String, String> ip = data.containsKey("ip") ? (Map<String, String>)data.get("ip") : new HashMap<String, String>();
      //ICP������
      String icp = data.containsKey("icp") && StringUtils.isNotEmpty((String) data.get("icp"))  ? (String) data.get("icp") : "";
      
      if(StringUtils.isEmpty(name) || StringUtils.isEmpty(type) || StringUtils.isEmpty(icp) || ip.isEmpty()) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "name��type and icp is required");
          }
        };
      }
      
      //����Դվ ��{"1.1.1.1": "80", "2.2.2.2": "80"}
      Map<String, String> originback = data.containsKey("originback") ? (Map<String, String>)data.get("originback") : new HashMap<String, String>();
      //��վ���� �������̳� ���˲��͵�
      String siteinfo = data.containsKey("siteinfo") && StringUtils.isNotEmpty((String) data.get("siteinfo"))  ? (String) data.get("siteinfo") : "";
      //�Ƿ���Ȩ 0δ��Ȩ 1����Ȩ
      int authorize = data.containsKey("authorize") && (int) data.get("authorize") > 0  ? (int) data.get("authorize") : 0;
      //����������� ��ѭԴվfollowOrigin ����html�Ⱦ�̬�ļ�cacheStaic �Զ���custom
      String cachetype = data.containsKey("cachetype") && StringUtils.isNotEmpty((String) data.get("cachetype"))  ? (String) data.get("cachetype") : "followOrigin";
      //Ƶ�������������
      final ArrayList<Map> cacheconf = data.containsKey("cacheconf") ? (ArrayList<Map>) data.get("cacheconf") : new ArrayList<Map>();
      //SSL����ID�� ����cdn_sslconf��ID
      int sslconf_id = data.containsKey("sslconf_id") && (int) data.get("sslconf_id") > 0  ? (int) data.get("sslconf_id") : 0;
      //ԴվЭ��
      int origin_protocol = data.containsKey("origin_protocol") && (int) data.get("origin_protocol") > 0  ? (int) data.get("origin_protocol") : 0;
      //�Ƿ�����HTTPS��ת 0������ 1���ã�����ֻ��SSL����ʱ����Ч
      int ssl_redirect = data.containsKey("ssl_redirect") && (int) data.get("ssl_redirect") > 0  ? (int) data.get("ssl_redirect") : 0;
      //��ص�ַ
      String monitorurl = data.containsKey("monitorurl") && StringUtils.isNotEmpty((String) data.get("monitorurl"))  ? (String) data.get("monitorurl") : "";
      //��ע
      String note = data.containsKey("note") && StringUtils.isNotEmpty((String) data.get("note"))  ? (String) data.get("note") : "";
      
      // �߼�����֮������
      String accesskey = data.containsKey("accesskey") && StringUtils.isNotEmpty((String) data.get("accesskey"))  ? (String) data.get("accesskey") : "";
      String accesskey_hashmethod = data.containsKey("accesskey_hashmethod") && StringUtils.isNotEmpty((String) data.get("accesskey_hashmethod"))  ? (String) data.get("accesskey_hashmethod") : "";
      String accesskey_signature = data.containsKey("accesskey_signature") && StringUtils.isNotEmpty((String) data.get("accesskey_signature"))  ? (String) data.get("accesskey_signature") : "";
      String accesskey_exclude = data.containsKey("accesskey_exclude") && StringUtils.isNotEmpty((String) data.get("accesskey_exclude"))  ? (String) data.get("accesskey_exclude") : "";
      
      // �߼�����֮referer
      // referer����
      String referer_enable = data.containsKey("referer_enable") && StringUtils.isNotEmpty((String) data.get("referer_enable"))  ? (String) data.get("referer_enable") : "";
      String referer_white = data.containsKey("referer_white") && StringUtils.isNotEmpty((String) data.get("referer_white"))  ? (String) data.get("referer_white") : "";
      String referer_black = data.containsKey("referer_black") && StringUtils.isNotEmpty((String) data.get("referer_black"))  ? (String) data.get("referer_black") : "";
      String allow_null_referer = data.containsKey("allow_null_referer") && StringUtils.isNotEmpty((String) data.get("allow_null_referer"))  ? (String) data.get("allow_null_referer") : "";
      String access_referer_suffix = data.containsKey("access_referer_suffix") && StringUtils.isNotEmpty((String) data.get("access_referer_suffix"))  ? (String) data.get("access_referer_suffix") : "";
      String access_deny_url = data.containsKey("access_deny_url") && StringUtils.isNotEmpty((String) data.get("access_deny_url"))  ? (String) data.get("access_deny_url") : "";
      
      //user agent����
      String ua_enable = data.containsKey("ua_enable") && StringUtils.isNotEmpty((String) data.get("ua_enable"))  ? (String) data.get("ua_enable") : "";
      String ua_white = data.containsKey("ua_white") && StringUtils.isNotEmpty((String) data.get("ua_white"))  ? (String) data.get("ua_white") : "";
      String ua_black = data.containsKey("ua_black") && StringUtils.isNotEmpty((String) data.get("ua_black"))  ? (String) data.get("ua_black") : "";
      String allow_null_ua = data.containsKey("allow_null_ua") && StringUtils.isNotEmpty((String) data.get("allow_null_ua"))  ? (String) data.get("allow_null_ua") : "";
      String access_ua_suffix = data.containsKey("access_ua_suffix") && StringUtils.isNotEmpty((String) data.get("access_ua_suffix"))  ? (String) data.get("access_ua_suffix") : "";
      
      //�߼�����֮����
      String cache_ignore_query = data.containsKey("cache_ignore_query") && StringUtils.isNotEmpty((String) data.get("cache_ignore_query"))  ? (String) data.get("cache_ignore_query") : "";
      
      //�߼�����֮header
      String add_server_header = data.containsKey("add_server_header") && StringUtils.isNotEmpty((String) data.get("add_server_header"))  ? (String) data.get("add_server_header") : "";
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("name", name);
          put("type", type);
          put("ip", ip);
          put("icp", icp);
          put("siteinfo", siteinfo);
          put("authorize", authorize);
          put("cachetype", cachetype);
          put("cacheconf", cacheconf);
          put("sslconf_id", sslconf_id);
          put("origin_protocol", origin_protocol);
          put("ssl_redirect", ssl_redirect);
          put("monitorurl", monitorurl);
          put("note", note);
          put("accesskey", accesskey);
          put("accesskey_hashmethod", accesskey_hashmethod);
          put("accesskey_signature", accesskey_signature);
          put("accesskey_exclude", accesskey_exclude);
          put("referer_enable", referer_enable);
          put("allow_null_referer", allow_null_referer);
          put("access_referer_suffix", access_referer_suffix);
          put("access_deny_url", access_deny_url);
          put("ua_enable", ua_enable);
          put("allow_null_ua", allow_null_ua);
          put("access_ua_suffix", access_ua_suffix);
          put("cache_ignore_query", cache_ignore_query);
          put("add_server_header", add_server_header);
        }
      };
      
      if(data.containsKey("referer_white")) {
        params.put("referer_white", referer_white);
      }
      else if(data.containsKey("referer_black")) {
        params.put("referer_black", referer_black);
      }
      
      
      if(data.containsKey("ua_white")) {
        params.put("ua_white", ua_white);
      }
      else if(data.containsKey("ua_black")) {
        params.put("ua_black", ua_black);
      }
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ����Ƶ��
   * @param data
   * @return
   */
  public Map<String, Object> domainUpdate(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainUpdate";
    
    try {
      int id = data.containsKey("id") ? (int) data.get("id") : 0;
      if(id <= 0) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("id", id);
        }
      };
      
      //�Ǳ�״̬
      if(data.containsKey("mark")) {
        int mark = (int) data.get("mark");
        if(mark == 1 || mark == 0) {
          params.put("mark", mark);
        }
      }
      
      //ICP������
      if(data.containsKey("icp")) {
        String icp = (String) data.get("icp");
        params.put("icp", icp);
      }
      
      //Դվ ��{"1.1.1.1": "80", "2.2.2.2": "80"}
      if(data.containsKey("ip")) {
        Map<String, String> ip = (Map<String, String>)data.get("ip");
        params.put("ip", ip);
      }

      //����Դվ ��{"1.1.1.1": "80", "2.2.2.2": "80"}
      if(data.containsKey("originback")) {
        Map<String, String> originback = (Map<String, String>)data.get("originback");
        params.put("originback", originback);
      }
      
      //��վ���� �������̳� ���˲��͵�
      if(data.containsKey("siteinfo")) {
        String siteinfo = (String) data.get("siteinfo");
        params.put("siteinfo", siteinfo);
      }
      
      //�Ƿ���Ȩ 0δ��Ȩ 1����Ȩ
      if(data.containsKey("authorize")) {
        int authorize = (int) data.get("authorize");
        params.put("authorize", authorize);
      }
      
      //����������� ��ѭԴվfollowOrigin ����html�Ⱦ�̬�ļ�cacheStaic �Զ���custom
      if(data.containsKey("cachetype")) {
        String cachetype = StringUtils.isNotEmpty((String) data.get("cachetype"))  ? (String) data.get("cachetype") : "followOrigin";
        params.put("cachetype", cachetype);
      }
      
      //Ƶ�������������
      if(data.containsKey("cacheconf")) {
        ArrayList<Map> cacheconf = (ArrayList<Map>) data.get("cacheconf");
        params.put("cacheconf", cacheconf);
      }
      
      //SSL����ID�� ����cdn_sslconf��ID
      if(data.containsKey("sslconf_id")) {
        int sslconf_id = (int) data.get("sslconf_id");
        params.put("sslconf_id", sslconf_id);
      }
     
      //ԴվЭ��
      if(data.containsKey("origin_protocol")) {
        int origin_protocol = (int) data.get("origin_protocol");
        params.put("origin_protocol", origin_protocol);
      }
      
      //�Ƿ�����HTTPS��ת 0������ 1���ã�����ֻ��SSL����ʱ����Ч
      if(data.containsKey("ssl_redirect")) {
        int ssl_redirect =(int) data.get("ssl_redirect");
        params.put("ssl_redirect", ssl_redirect);
      }
      
      //��ص�ַ
      if(data.containsKey("monitorurl")) {
        String monitorurl = (String) data.get("monitorurl");
        params.put("monitorurl", monitorurl);
      }
      
      //��ע
      if(data.containsKey("note")) {
        String note = (String) data.get("note");
        params.put("note", note);
      }
      
      
      // �߼�����֮������
      if(data.containsKey("accesskey")) {
        String accesskey = (String) data.get("accesskey");
        params.put("accesskey", accesskey);
        if(accesskey.equals("on")) {
          String accesskey_hashmethod = data.containsKey("accesskey_hashmethod") && StringUtils.isNotEmpty((String) data.get("accesskey_hashmethod"))  ? (String) data.get("accesskey_hashmethod") : "";
          String accesskey_signature = data.containsKey("accesskey_signature") && StringUtils.isNotEmpty((String) data.get("accesskey_signature"))  ? (String) data.get("accesskey_signature") : "";
          String accesskey_exclude = data.containsKey("accesskey_exclude") && StringUtils.isNotEmpty((String) data.get("accesskey_exclude"))  ? (String) data.get("accesskey_exclude") : "";
          params.put("accesskey_hashmethod", accesskey_hashmethod);
          params.put("accesskey_signature", accesskey_signature);
          params.put("accesskey_exclude", accesskey_exclude);
        }
      }
      
      
      // �߼�����֮referer
      // referer����
      if(data.containsKey("referer_enable")) {
        String referer_enable = (String) data.get("referer_enable");
        params.put("referer_enable", referer_enable);
        if(referer_enable.equals("on")) {
          String referer_white = data.containsKey("referer_white") && StringUtils.isNotEmpty((String) data.get("referer_white"))  ? (String) data.get("referer_white") : "";
          String referer_black = data.containsKey("referer_black") && StringUtils.isNotEmpty((String) data.get("referer_black"))  ? (String) data.get("referer_black") : "";
          if(data.containsKey("referer_white")) {
            params.put("referer_white", referer_white);
          }
          else if(data.containsKey("referer_black")) {
            params.put("referer_black", referer_black);
          }
          
          String allow_null_referer = data.containsKey("allow_null_referer") && StringUtils.isNotEmpty((String) data.get("allow_null_referer"))  ? (String) data.get("allow_null_referer") : "";
          String access_referer_suffix = data.containsKey("access_referer_suffix") && StringUtils.isNotEmpty((String) data.get("access_referer_suffix"))  ? (String) data.get("access_referer_suffix") : "";
          String access_deny_url = data.containsKey("access_deny_url") && StringUtils.isNotEmpty((String) data.get("access_deny_url"))  ? (String) data.get("access_deny_url") : "";
          
          params.put("allow_null_referer", allow_null_referer);
          params.put("access_referer_suffix", access_referer_suffix);
          params.put("access_deny_url", access_deny_url);
        }
        else {
          params.put("allow_null_referer", "off");
          params.put("access_referer_suffix", "");
          params.put("access_deny_url", "");
        }
        
      }
      
      
      //user agent����
      if(data.containsKey("ua_enable")) {
        String ua_enable = (String) data.get("ua_enable");
        params.put("ua_enable", ua_enable);
        if(ua_enable.equals("on")) {
          String ua_white = data.containsKey("ua_white") && StringUtils.isNotEmpty((String) data.get("ua_white"))  ? (String) data.get("ua_white") : "";
          String ua_black = data.containsKey("ua_black") && StringUtils.isNotEmpty((String) data.get("ua_black"))  ? (String) data.get("ua_black") : "";
          if(data.containsKey("ua_white")) {
            params.put("ua_white", ua_white);
          }
          else if(data.containsKey("ua_black")) {
            params.put("ua_black", ua_black);
          }
          
          String allow_null_ua = data.containsKey("allow_null_ua") && StringUtils.isNotEmpty((String) data.get("allow_null_ua"))  ? (String) data.get("allow_null_ua") : "";
          String access_ua_suffix = data.containsKey("access_ua_suffix") && StringUtils.isNotEmpty((String) data.get("access_ua_suffix"))  ? (String) data.get("access_ua_suffix") : "";
          params.put("allow_null_ua", allow_null_ua);
          params.put("access_ua_suffix", access_ua_suffix);
        }
        else {
          params.put("allow_null_ua", "off");
          params.put("access_ua_suffix", "");
        }
      }
      
      
      //�߼�����֮����
      if(data.containsKey("cache_ignore_query")) {
        String cache_ignore_query = (String) data.get("cache_ignore_query");
        params.put("cache_ignore_query", cache_ignore_query);
      }
      
      
      //�߼�����֮header
      if(data.containsKey("add_server_header")) {
        String add_server_header = (String) data.get("add_server_header");
        params.put("add_server_header", add_server_header);
        
      }

      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ����Ƶ��
   * @param data
   * @return
   */
  public Map<String, Object> domainStart(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainStart";
    
    Map<String, Object> params = new HashMap<String, Object>() {
      {
        put("token", token);
      }
    };
    
    try {
      // id ����[1,2,3]
      ArrayList id = data.containsKey("id") ? (ArrayList) data.get("id") : new ArrayList();
      if(id.isEmpty()) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      params.put("id", id);
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * �ر�Ƶ��
   * @param data
   * @return
   */
  public Map<String, Object> domainClose(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainClose";
    
    Map<String, Object> params = new HashMap<String, Object>() {
      {
        put("token", token);
      }
    };
    
    try {
      // id ����[1,2,3]
      ArrayList id = data.containsKey("id") ? (ArrayList) data.get("id") : new ArrayList();
      if(id.isEmpty()) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      params.put("id", id);
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ɾ��Ƶ��
   * @param data
   * @return
   */
  public Map<String, Object> domainDelete(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainDelete";
    
    Map<String, Object> params = new HashMap<String, Object>() {
      {
        put("token", token);
      }
    };
    
    try {
      // id ����[1,2,3]
      ArrayList id = data.containsKey("id") ? (ArrayList) data.get("id") : new ArrayList();
      if(id.isEmpty()) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      params.put("id", id);
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * Ƶ����־�б�
   * @param data
   * @return
   */
  public Map<String, Object> domainLogList(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainLogList";
    
    try {
      //Ŀ¼
      String dir = data.containsKey("dir") ? (String) data.get("dir") : "";
      //Ŀ¼��һ�����ڷ�����
      String dir1 = data.containsKey("dir1") ? (String) data.get("dir1") : "";
      //��־��ʼ����ֹ�����ڣ���2014-12-04��Ĭ��2����ǰ������Ϊֹ
      String  begintime = data.containsKey("begintime") ? (String) data.get("begintime") : "";
      String  endtime = data.containsKey("endtime") ? (String) data.get("endtime") : "";
      //��ҳ
      int page = data.containsKey("page") && (int) data.get("page") > 0 ? (int) data.get("page") : 1;
      int limit = data.containsKey("limit") && (int) data.get("limit") > 0 ? (int) data.get("limit") : 30;
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("dir", dir);
          put("dir1", dir1);
          put("begintime", begintime);
          put("endtime", endtime);
          put("page", page);
          put("limit", limit);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * Ƶ������
   * @param data
   * @return
   */
  public Map<String, Object> domainBandwidth(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainBandwidth";
    
    try {
      //����Ƶ��ID
      String domain_ids = data.containsKey("domain_ids") ? (String) data.get("domain_ids") : "";
      //�������� ȡֵ[cloud(�Ʒַ�),static(��̬),dynamic(��̬),rtmp(RTMP����),music(���Ӽ���),hls(HLS����),download(���ؼ���)]
      String serviceTypes = data.containsKey("serviceTypes") ? (String) data.get("serviceTypes") : "";
      //��־��ʼ����ֹ�����ڣ���2014-12-04��Ĭ��2����ǰ������Ϊֹ
      String  begintime = data.containsKey("begintime") ? (String) data.get("begintime") : "";
      String  endtime = data.containsKey("endtime") ? (String) data.get("endtime") : "";
    //�鿴���ͣ�ȡֵ��Χ[today(����),yesterday(����),week(����),month(����),last_month(����),custom(�Զ���)]
      String  viewtype = data.containsKey("viewtype") ? (String) data.get("viewtype") : "";
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("domain_ids", domain_ids);
          put("serviceTypes", serviceTypes);
          put("begintime", begintime);
          put("endtime", endtime);
          put("viewtype", viewtype);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * Ƶ��ͳ�Ʒ���
   * @param data
   * @return
   */
  public Map<String, Object> domainLogstat(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainLogstat";
    
    try {
      //����Ƶ��ID
      String domain_ids = data.containsKey("domain_ids") ? (String) data.get("domain_ids") : "";
      //�������� ȡֵ[cloud(�Ʒַ�),static(��̬),dynamic(��̬),rtmp(RTMP����),music(���Ӽ���),hls(HLS����),download(���ؼ���)]
      String serviceTypes = data.containsKey("serviceTypes") ? (String) data.get("serviceTypes") : "";
      //��־��ʼ����ֹ�����ڣ���2014-12-04��Ĭ��2����ǰ������Ϊֹ
      String  begintime = data.containsKey("begintime") ? (String) data.get("begintime") : "";
      String  endtime = data.containsKey("endtime") ? (String) data.get("endtime") : "";
      //�鿴���ͣ�ȡֵ��Χ[today(����),yesterday(����),week(����),month(����),last_month(����),custom(�Զ���)]
      String  viewtype = data.containsKey("viewtype") ? (String) data.get("viewtype") : "";
      //��ȡ���ݵ����ͣ�ȡֵ[cache, code, country, province, isp]
      String  types = data.containsKey("types") ? (String) data.get("types") : "";
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("domain_ids", domain_ids);
          put("serviceTypes", serviceTypes);
          put("begintime", begintime);
          put("endtime", endtime);
          put("viewtype", viewtype);
          put("types", types);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * SSL֤���б�
   * @param data
   * @return
   */
  public Map<String, Object> sslList(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/sslList";
    
    try {
      // ��ҳ��Ϣ
      int page = data.containsKey("page") && (int) data.get("page") > 0 ? (int) data.get("page") : 1;
      int limit = data.containsKey("limit") && (int) data.get("limit") > 0 ? (int) data.get("limit") : 30;
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("page", page);
          put("limit", limit);
        }
      };
      
      //��ID��ѯ
      int id = data.containsKey("id") && (int) data.get("id") > 0 ? (int) data.get("id") : 0;
      if(id > 0) {
        params.put("id", id);
      }
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ����SSL֤��
   * @param data
   * @return
   */
  public Map<String, Object> sslAdd(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/sslAdd";
    
    try {
      // ֤������
      String name = data.containsKey("name") && StringUtils.isNotEmpty((String) data.get("name"))  ? (String) data.get("name") : "";
      // ֤���ļ�
      String ssl_crt = data.containsKey("ssl_crt") && StringUtils.isNotEmpty((String) data.get("ssl_crt"))  ? (String) data.get("ssl_crt") : "";
      // ֤����Կ
      String ssl_key = data.containsKey("ssl_key") && StringUtils.isNotEmpty((String) data.get("ssl_key"))  ? (String) data.get("ssl_key") : "";
      // �м�֤��
      String ssl_ca = data.containsKey("ssl_ca") && StringUtils.isNotEmpty((String) data.get("ssl_ca"))  ? (String) data.get("ssl_ca") : "";
      
      if(StringUtils.isEmpty(ssl_crt) || StringUtils.isEmpty(ssl_key)) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "please upload your cert");
          }
        };
      }
     
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("name", name);
          put("ssl_crt", ssl_crt);
          put("ssl_key", ssl_key);
          put("ssl_ca", ssl_ca);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ����SSL֤��
   * @param data
   * @return
   */
  public Map<String, Object> sslUpdate(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/sslUpdate";
    
    try {
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
        }
      };
      
      // ֤������
      if(data.containsKey("name")) {
        String name = StringUtils.isNotEmpty((String) data.get("name"))  ? (String) data.get("name") : "";
        params.put("name", name);
      }
      
      // ֤���ļ�
      if(data.containsKey("ssl_crt")) {
        String ssl_crt = StringUtils.isNotEmpty((String) data.get("ssl_crt"))  ? (String) data.get("ssl_crt") : "";
        params.put("ssl_crt", ssl_crt);
      }
      
      // ֤����Կ
      if(data.containsKey("ssl_key")) {
        String ssl_key = StringUtils.isNotEmpty((String) data.get("ssl_key"))  ? (String) data.get("ssl_key") : "";
        params.put("ssl_key", ssl_key);
      }
      
      // �м�֤��
      if(data.containsKey("ssl_ca")) {
        String ssl_ca = StringUtils.isNotEmpty((String) data.get("ssl_ca"))  ? (String) data.get("ssl_ca") : "";
        params.put("ssl_ca", ssl_ca);
      }
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ɾ��SSL֤��
   * @param data
   * @return
   */
  public Map<String, Object> sslDelete(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/sslDelete";
    
    try {
      
      int id = data.containsKey("id") ? (int) data.get("id") : 0;
      if(id <= 0) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("id", id);
        }
      };

      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * Ƶ��ʵʱ�����顢����
   * @param data
   * @return
   */
  public Map<String, Object> domainCurrentData(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/domainCurrentData";
    
    try {
      String ids = data.containsKey("ids") ? (String) data.get("ids") : "";
      if(StringUtils.isEmpty(ids)) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("ids", ids);
        }
      };

      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ˢ���б�
   * @param data
   * @return
   */
  public Map<String, Object> refreshList(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/refreshList";
    
    try {
      // ��ҳ��Ϣ
      int page = data.containsKey("page") && (int) data.get("page") > 0 ? (int) data.get("page") : 1;
      int limit = data.containsKey("limit") && (int) data.get("limit") > 0 ? (int) data.get("limit") : 30;
      // �Ƿ�Ԥȡ 1 �� 0 ��
      int preload = data.containsKey("preload") && (int) data.get("preload") > 0 ? (int) data.get("preload") : 0;
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("page", page);
          put("limit", limit);
          put("preload", preload);
        }
      };
      
      // �ļ�ˢ��file Ŀ¼ˢ��dir
      String type = data.containsKey("type")  ? (String) data.get("type") : "";
      if(StringUtils.isNotEmpty(type)) {
        params.put("type", type);
      }
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * �ύˢ��
   * @param data
   * @return
   */
  public Map<String, Object> refresh(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/refresh";
    
    try {
      //ˢ������ file �ļ� dir Ŀ¼
      String type = data.containsKey("type")  ? (String) data.get("type") : "";
      //ˢ��url
      String urls = data.containsKey("urls")  ? (String) data.get("urls") : "";
      //url�ָ��������urlʹ�ø÷��ŷָ���Ĭ��,
      String partition = data.containsKey("partition")  ? (String) data.get("partition") : ",";
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("type", type);
          put("urls", urls);
          put("partition", partition);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ��ID�ύˢ��
   * @param data
   * @return
   */
  public Map<String, Object> refreshById(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/refreshById";
    
    try {
      int id = data.containsKey("id") ? (int) data.get("id") : 0;
      
      if(id <= 0) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("id", id);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * �ύԤȡ
   * @param data
   * @return
   */
  public Map<String, Object> preload(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/preload";
    
    try {
      //Ԥȡ���� 1 ȫ��Ԥȡ 0 ��ȫ��
      int preloadtype = data.containsKey("preloadtype")  ? (int) data.get("preloadtype") : 0;
      //Ԥȡurl
      String urls = data.containsKey("urls")  ? (String) data.get("urls") : "";
      //url�ָ��������urlʹ�ø÷��ŷָ���Ĭ��,
      String partition = data.containsKey("partition")  ? (String) data.get("partition") : ",";
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("preloadtype", preloadtype);
          put("urls", urls);
          put("partition", partition);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ��ID�ύԤȡ
   * @param data
   * @return
   */
  public Map<String, Object> preloadById(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/preloadById";
    
    try {
      int id = data.containsKey("id") ? (int) data.get("id") : 0;
      
      if(id <= 0) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("id", id);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ɾ��ˢ�¡�Ԥȡ
   * @param data
   * @return
   */
  public Map<String, Object> refreshDelete(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/refreshDelete";
    
    try {
      String ids = data.containsKey("ids") ? (String) data.get("ids") : "";
      
      if(StringUtils.isEmpty(ids)) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "id is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("ids", ids);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * CDN�˵������û���
   * @param data
   * @return
   */
  public Map<String, Object> getBillByUser(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/getBillByUser";
    
    try {
      //�������ͣ�ȡֵ��Χ[cloud(�Ʒַ�),download(����),hls(HLS),music(����),rtmp(RTMP),static(��̬)]
      ArrayList type = data.containsKey("type") ? (ArrayList) data.get("type") : new ArrayList();
      //�·� ��201506��ʾ2015��6�·ݣ�����ѯ���12���µ�����
      String months = data.containsKey("months") ? (String) data.get("months") : "";
      //���˺�ID����ѯ���˺��˵�ʱ��ѡ
      int sub_uid = data.containsKey("sub_uid") ? (int) data.get("sub_uid") : 0;
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("type", type);
          put("months", months);
          put("sub_uid", sub_uid);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * CDN�˵�����Ƶ����
   * @param data
   * @return
   */
  public Map<String, Object> getBillByDomainID(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/getBillByDomainID";
    
    try {
      //Ƶ��ID,���� ���� ��[1,2,3]
      ArrayList domain_ids = data.containsKey("type") ? (ArrayList) data.get("domain_ids") : new ArrayList();
      //��ʼʱ�䣬ȡֵ[unixtime]
      int begintime = data.containsKey("begintime") ? (int) data.get("begintime") : 0;
      //����ʱ�䣬ȡֵ[unixtime]
      int endtime = data.containsKey("endtime") ? (int) data.get("endtime") : 0;
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("domain_ids", domain_ids);
          put("begintime", begintime);
          put("endtime", endtime);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ��ȡCDN�ڵ�
   * @param data
   * @return
   */
  public Map<String, Object> nodes(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/nodes";
    
    try {
      //���� core ���� edge ��Ե
      String type = data.containsKey("type") ? (String) data.get("type") : "";
      //Ƶ������
      String name = data.containsKey("name") ? (String) data.get("name") : "";
      
      if(StringUtils.isEmpty(type) || StringUtils.isEmpty(name)) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "type and name is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("type", type);
          put("name", name);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
  /**
   * ���ݿͻ���IP�������ŵ�CDN�ڵ�
   * @param data
   * @return
   */
  public Map<String, Object> getNodesByClientIP(Map<String, Object> data) {
    String token = (new Token()).token();
    if(StringUtils.isEmpty(token)) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", "unable to get token");
        }
      };
    }
    
    String url = Request.API_URL + "/API/cdn/getNodesByClientIP";
    
    try {
      //�ͻ���IP
      String client_ip = data.containsKey("client_ip") ? (String) data.get("client_ip") : "";
      //Ƶ������
      String name = data.containsKey("name") ? (String) data.get("name") : "";
      
      if(StringUtils.isEmpty(client_ip) || StringUtils.isEmpty(name)) {
        return new HashMap<String, Object>() {
          {
            put("code", 0);
            put("message", "client_ip and name is required");
          }
        };
      }
      
      Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("token", token);
          put("client_ip", client_ip);
          put("name", name);
        }
      };
      
      Map<String, Object> result = Request.doPost(url, params);
      return result;
    } catch (Exception e) {
      return new HashMap<String, Object>() {
        {
          put("code", 0);
          put("message", e.getMessage());
        }
      };
    }
  }
  
}
