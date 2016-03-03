package com.verycloud.util;

import java.util.Random;


/**
 * 加密解密实用类
 * @author verycloud
 *
 */
public class EncryptUtil {
	
  //密锁串，不能出现重复字符，内有A-Z,a-z,0-9,/,=,+,_,
	public final static String lockStream = "st=lDEFABCNOPyzghi_jQRST-UwxkVWXYZabcdefIJK6/7nopqr89LMmGH012345uv";
  
	public static String encrypt(String plain, String publicKey) throws Exception {
		//随机找一个数字，并从密锁串中找到一个密锁值
		int lockLen = lockStream.length();
		Random random = new Random();
		int lockCount = random.nextInt(lockLen - 1);
		String randomLock = lockStream.substring(lockCount, lockCount + 1);
		//结合随机密锁值生成MD5后的密码
		String password = MD5Util.md5(publicKey + randomLock);
		//开始对字符串加密
		plain = Base64Util.encodeBase64(plain);
		String tmpStream = "";
		int i = 0; 
		int j = 0; 
		int k = 0;
		for(i = 0; i < plain.length(); i++) {
			k = (k == password.length()) ? 0 : k;
			j = (lockStream.indexOf(plain.substring(i, i + 1)) + lockCount + ((int) password.charAt(k))) % (lockLen);
			tmpStream += lockStream.substring(j, j + 1);
			k++;
		}
		return tmpStream + randomLock;
	}
	
	public static String decrypt(String ciper, String publicKey) throws Exception {
		//密锁串，不能出现重复字符，内有A-Z,a-z,0-9,/,=,+,_,
	  //随机找一个数字，并从密锁串中找到一个密锁值
		int lockLen = lockStream.length();
		//获得字符串长度
		int txtLen = ciper.length();
		//截取随机密锁值
		String randomLock = ciper.substring(txtLen - 1, txtLen);
		//获得随机密码值的位置
		int lockCount = lockStream.indexOf(randomLock);
		//结合随机密锁值生成MD5后的密码
		String password = MD5Util.md5(publicKey + randomLock);
		//开始对字符串解密
		ciper = ciper.substring(0, txtLen - 1);
		String tmpStream = "";
		int i = 0; 
		int j = 0; 
		int k = 0;
		for(i = 0; i < ciper.length(); i++) { 
			k = (k == password.length()) ? 0 : k;
			j = lockStream.indexOf(ciper.substring(i, i + 1)) - lockCount - ((int) password.charAt(k));
			while(j < 0) {
				j = j + lockLen;
			}
			tmpStream += lockStream.substring(j, j + 1);
			k++;
		}
		return Base64Util.decodeBase64(tmpStream);
	}
}
