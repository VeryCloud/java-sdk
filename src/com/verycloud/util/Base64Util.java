package com.verycloud.util;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.*;

public class Base64Util {
	/**
	 * base64±àÂë
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64(String str) throws Exception {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
			if (b != null) {
				byte[] tmp = Base64.encodeBase64(b);
				s= new String(tmp, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		}
		
		return s;
	}

	/**
	 * base64½âÂë
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static String decodeBase64(String s) throws Exception {
		byte[] b = null;
		String result = null;
		if (s != null) {
			try {
				b = Base64.decodeBase64(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return result;
	}
}