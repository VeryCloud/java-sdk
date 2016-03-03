package com.verycloud.util;

import java.util.Random;


/**
 * ���ܽ���ʵ����
 * @author verycloud
 *
 */
public class EncryptUtil {
	
  //�����������ܳ����ظ��ַ�������A-Z,a-z,0-9,/,=,+,_,
	public final static String lockStream = "st=lDEFABCNOPyzghi_jQRST-UwxkVWXYZabcdefIJK6/7nopqr89LMmGH012345uv";
  
	public static String encrypt(String plain, String publicKey) throws Exception {
		//�����һ�����֣��������������ҵ�һ������ֵ
		int lockLen = lockStream.length();
		Random random = new Random();
		int lockCount = random.nextInt(lockLen - 1);
		String randomLock = lockStream.substring(lockCount, lockCount + 1);
		//����������ֵ����MD5�������
		String password = MD5Util.md5(publicKey + randomLock);
		//��ʼ���ַ�������
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
		//�����������ܳ����ظ��ַ�������A-Z,a-z,0-9,/,=,+,_,
	  //�����һ�����֣��������������ҵ�һ������ֵ
		int lockLen = lockStream.length();
		//����ַ�������
		int txtLen = ciper.length();
		//��ȡ�������ֵ
		String randomLock = ciper.substring(txtLen - 1, txtLen);
		//����������ֵ��λ��
		int lockCount = lockStream.indexOf(randomLock);
		//����������ֵ����MD5�������
		String password = MD5Util.md5(publicKey + randomLock);
		//��ʼ���ַ�������
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
