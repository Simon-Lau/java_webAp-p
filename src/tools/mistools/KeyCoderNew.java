// this file coding in utf8
package tools.mistools;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.util.Random;

/**
 * DES加解密类 alter by wzhao 2011-10-17
 * 
 * @author wzhao
 * 
 */

public class KeyCoderNew implements KeyCoder {
	// private int systemId = 0;

	private String ranKey = null;
	private String strKey = null;
	private byte[] iv = "12345678".getBytes();

	private String oldKey[] = { "PHQGH", "UMEAY", "LNLFD", "XFIRC", "VSCXG",
			"GBWKF", "NQDUX", "WFNFO", "ZVSRT", "KJPRE", "PGGXR", "PNRVY",
			"STMWC", "YSYYC", "QPEVI", "KEFFM", "ZNIMK", "KASVW", "SRENZ",
			"KYCXF", "XTLSG", "YPSFA", "DPOOE", "FXZBC", "OEJUV", "PVABO",
			"YGPOE", "YLFPB", "NPLJV", "RVIPY", "AMYEH", "WQNQR", "QPMXU",
			"JJLOO", "VAOWU", "XWHMS", "NCBXC", "OKSFZ", "KVATX", "DKNLY",
			"JYHFI", "XJSWN", "KKUFN", "UXXZR", "ZBMNM", "GQOOK", "ETLYH",
			"NKOAU", "GZQRC", "DDIUT", "EIOJW", "AYYZP", "VSCMP", "SAJLF",
			"VGUBF", "AAOVL", "ZYLNT", "RKDCP", "WSRTE", "SJWHD", "IZCOB",
			"ZCNFW", "LQIJT", "VDWVX", "HRCBL", "DVGYL", "WGBUS", "BMBOR",
			"XTLHC", "SMPXO", "HGMGN", "KEUFD", "XOTOG", "BGXPE", "YANFE",
			"TCUKE", "PZSHK", "LJUGG", "GEKJD", "QZJEN", "PEVQG", "XIEPJ",
			"SRDZJ", "AZUJL", "LCHHB", "FQMKI", "MWZOB", "IWYBX", "DUUNF",
			"SKSRS", "RTEKM", "QDCYZ", "JEEUH", "MSRQC", "OZIJI", "PFION",
			"EEDDP", "SZRNA", "VYMMT", "ATBDZ" };

	/**
	 * 获取密钥
	 * 
	 * @return
	 */
	public String getKey() {
		return ranKey;
	}

	/**
	 * 获取随机五位字符串
	 * 
	 * @return 随机的五位字符串
	 */
	private String getRandomString() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 将byte数组转换为16进制字符串
	 * 
	 * @param arrB
	 *            需转化的字符数组
	 * @return 转化后的16进制字符串
	 * @throws Exception
	 *             转化失败
	 */
	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 16进制字符串的长度是byte数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 若为负数,则取其补码
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 若小于16,则需在转化后的16进制数前补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将16进制字符串转化为byte数组
	 * 
	 * @param strIn
	 *            待转化的16进制字符串
	 * @return 转化后的byte数组
	 * @throws Exception
	 *             转化出错
	 */
	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 输出字符数组大小应为输入字符串长度的一半
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 以随机密钥和固定密钥号初始化本类 一般用于解密
	 * 
	 * @param inkey
	 *            随机密钥
	 * @param strid
	 *            固定密钥号,此处一共有100个固定KEY,若参数不在范围内则做取反及求模处理
	 * @throws Exception
	 *             无法初始化
	 */
	public KeyCoderNew(String inkey, int strid) throws Exception {
		// this.systemId = strid;
		this.ranKey = inkey;
		// 处理strid取值
		strid = strid < 0 ? -strid % 100 : strid % 100;
		strKey = this.ranKey + oldKey[strid];
	}

	/**
	 * 以固定密钥号初始化本类 一般用于加密
	 * 
	 * @param strid
	 *            固定密钥号,此处一共有100个固定KEY,若参数不在范围内则做取反及求模处理
	 * @throws Exception
	 *             无法初始化
	 */
	public KeyCoderNew(int strid) throws Exception {
		this.ranKey = this.getRandomString();
		// this.systemId = strid;
		// 处理strid取值
		strid = strid < 0 ? -strid % 100 : strid % 100;
		strKey = this.ranKey + oldKey[strid];
	}

	/**
	 * 对目标字符串加密
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 *             无法加密
	 */
	public String encrypt(String str) throws Exception {
		SecureRandom sr = new SecureRandom();
		IvParameterSpec ips = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getKey(strKey), ips, sr);
		byte[] bt = cipher.doFinal(str.getBytes());
		return byteArr2HexStr(bt);
	}

	/**
	 * 对目标字符串解密
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 *             无法解密
	 */
	public String decrypt(String str) throws Exception {
		SecureRandom sr = new SecureRandom();
		IvParameterSpec ips = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, getKey(strKey), ips, sr);
		byte[] bt = cipher.doFinal(hexStr2ByteArr(str));
		return new String(bt);
	}

	/**
	 * 根据给定的字符数组获取可用三重DES加密KEY
	 * 
	 * @param arrBTmp
	 *            获取KEY用的参数数组
	 * @return 三重DES加密KEY
	 * @throws Exception
	 *             无法获取KEY
	 */
	private SecretKey getKey(String partKey) throws Exception {
		// 三重DES加密需要24位的KEY
		String wholeKey = "";
		// 原KEY为10位
		if (partKey.length() != 10) {
			throw new Exception("partKey length error!");
		}

		// 原KEY+原KEY的3位+反序原KEY+原KEY的2-5位+原KEY的5-7位
		wholeKey += partKey + partKey.substring(3, 4)
				+ new StringBuffer(partKey).reverse() + partKey.substring(5, 8);

		// 生成KEY
		DESedeKeySpec dks = new DESedeKeySpec(wholeKey.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		return securekey;
	}
	
	//CCKID:4wszh>>CSYSID:40>>8aff6e53563f531ecd6c6bf3f3309ab46e27132b0a4c046ef12130d17015ce182121d2e4bbe27b615434db958dafff59742a2bb187d8751e0986c570259edd21e2eb25b9721559a219d918b828c0c52b0533cd8d684cf55aa861ed275c8a27575ff4801fbaad8363932560deb0a3ee569e80ae479d8dce479a1a927dce4b1ac46bbf410f3b56c9215183fcabc782ffbea58dc01fbc715bcb3ab35b564d2d31b7621fe6c693bfbaf0
	public static void main(String[] args){
		try {
			KeyCoderNew k = new KeyCoderNew("4wszh",40);
			String str = k.decrypt("8aff6e53563f531ecd6c6bf3f3309ab46e27132b0a4c046ef12130d17015ce182121d2e4bbe27b615434db958dafff59742a2bb187d8751e0986c570259edd21e2eb25b9721559a219d918b828c0c52b0533cd8d684cf55aa861ed275c8a27575ff4801fbaad8363932560deb0a3ee569e80ae479d8dce479a1a927dce4b1ac46bbf410f3b56c9215183fcabc782ffbea58dc01fbc715bcb3ab35b564d2d31b7621fe6c693bfbaf0");
			System.out.println(str);
			KeyCoderNew k2 = new KeyCoderNew(40);
			str = k2.encrypt("哦哦");
			k = new KeyCoderNew(k2.getKey(),40);
			System.out.println(k.decrypt(str));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}