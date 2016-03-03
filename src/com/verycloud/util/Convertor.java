package com.verycloud.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * map��json���໥ת��
 * @author verycloud
 *
 */
public class Convertor {
  /**
   * mapתjson�ַ���
   * @param map
   * @return
   */
  public static String map2json(Map<String, Object> map) throws Exception {
    JSONObject jsonObject = JSONObject.fromObject(map);
    return jsonObject.toString();
  }
  
  /**
   * json�ַ���תmap
   * @param json
   * @return
   */
  public static HashMap<String, Object> json2map(String json) throws Exception {
    JSONObject jsonObject =JSONObject.fromObject(json);
    return reflect(jsonObject);
  }
  
  /**
   * ��JSONObjec����ת����Map-List����
   * @see JSONHelper#reflect(JSONArray)
   * @param json
   * @return
   */
  private static HashMap<String, Object> reflect(JSONObject json) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    Set<?> keys = json.keySet();
    for(Object key : keys) {
      Object o = json.get(key);
      if(o instanceof JSONArray)
        map.put((String) key, reflect((JSONArray) o));
      else if(o instanceof JSONObject)
        map.put((String) key, reflect((JSONObject) o));
      else
        map.put((String) key, o);
    }
    return map;
  }

  /**
   * ��JSONArray����ת����Map-List����
   * @see JSONHelper#reflect(JSONObject)
   * @param json
   * @return
   */
  private static Object reflect(JSONArray json) {
    List<Object> list = new ArrayList<Object>();
    for(Object o : json){
      if(o instanceof JSONArray)
        list.add(reflect((JSONArray) o));
      else if(o instanceof JSONObject)
        list.add(reflect((JSONObject) o));
      else
        list.add(o);
    }
    return list;
  }
}
