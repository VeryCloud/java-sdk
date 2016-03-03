package com.verycloud.demo;

import java.util.HashMap;
import java.util.Map;
import com.verycloud.api.CDNAPI;

/**
 * ssl post«Î«Û≤‚ ‘¿‡
 * @author verycloud
 *
 */
public class TestAPI {

	public void test() {
		CDNAPI cdnapi = new CDNAPI();
		Map result = cdnapi.sslList(new HashMap<String, Object>(){
		  {
		    put("page", 1);
		    put("limit", 2);
		    //put("id", 661);
		  }
		});
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		TestAPI main = new TestAPI();
		main.test();
	}
}
