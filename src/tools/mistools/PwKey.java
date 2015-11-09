package tools.mistools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 * 
 * alter by wzhao 2011-10-19
 * 
 * @author wzhao
 * 
 */

public class PwKey {

	// private String initkey = "somestrng";

	public PwKey() {
		;
	}

	/** */
	/**
	 * The hex digits.
	 */
	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/** */
	/**
	 * Transform the byte array to hex string.
	 * 
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/** */
	/**
	 * Transform a byte to hex string.
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;

		// get the first four bit
		int d1 = n / 16;

		// get the second four bit
		int d2 = n % 16;

		return hexDigits[d1] + hexDigits[d2];
	}

	/** */
//	/**
//	 * 做特定模式的MD5加密
//	 * 
//	 * @param origin
//	 *            待加密数据
//	 * @return 加密结果
//	 */
//	public String toCode(String origin) {
//		try {
//			origin = appendedMD5(origin, "DareToDecryptMe!");
//			origin = splitedMD5(origin);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//
//		return origin;
//	}
	
	/**
	 * 做MD5加密
	 * 
	 * @param origin
	 *            待加密数据
	 * 
	 * @return 加密结果
	 */
	public String toCode(String origin) {
		try {
			origin = appendedMD5(origin, "DareToDecryptMe!");
			origin = splitedMD5(origin);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return origin;
	}

	/**
	 * 原始MD5加密方法
	 * 
	 * @param origin
	 *            待加密数据
	 * @return 加密结果
	 * @throws NoSuchAlgorithmException
	 */
	public String simpleMD5(String origin) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		return byteArrayToHexString(md.digest(origin.getBytes()));
	}

	/**
	 * 多次MD5运算
	 * 
	 * @param origin
	 *            待加密数据
	 * @param times
	 *            MD5运算次数
	 * @return 加密结果
	 * @throws NoSuchAlgorithmException
	 */
	private String timesedMD5(String origin, int times)
			throws NoSuchAlgorithmException {
		for (int i = 0; i < times; i++) {
			origin = simpleMD5(origin);
		}
		return origin;
	}

	/**
	 * 附加字符的MD5运算
	 * 
	 * @param origin
	 *            待加密数据
	 * @param append
	 *            附加字符
	 * @return 加密后的数据
	 * @throws NoSuchAlgorithmException
	 */
	private String appendedMD5(String origin, String append)
			throws NoSuchAlgorithmException {
		return simpleMD5(origin + append);
	}

	/**
	 * 简单加密后对MD5码分四组再加密
	 * 
	 * @param origin
	 *            待加密数据
	 * @return 加密结果
	 * @throws NoSuchAlgorithmException
	 */
	private String splitedMD5(String origin) throws NoSuchAlgorithmException {
		// 对原始数据进行一次简单加密
		String s = simpleMD5(origin);
		String temp = "";

		// 对所得MD5值进行分组-两次加密-合并
		for (int i = 0; i < 4; i++) {
			temp += timesedMD5(s.substring(i * 8, i * 8 + 8), 2);
		}

		// 最后把长字串再加密一次，成为 32 字符密文
		return simpleMD5(temp);
	}
	
	public static void main(String[] args) {
		PwKey pk = new PwKey();
		System.out.println(pk.toCode("321"));
	}
}