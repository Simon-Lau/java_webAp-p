package tools.mistools;

import java.security.*;
import javax.crypto.*;
import java.util.Random;

/**
 * 旧版加解密器,用于1-7号系统
 * 
 * alter by wzhao in 2011-10-20
 * 
 * @author hit802group
 * 
 */
public class KeyCoderOld implements KeyCoder {
	// private static int systemId = 0;

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;
	private String ranKey = null;
	private String strKey = null;

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
			"EEDDP", "SZRNA", "VYMMT", "ATBDZ"

	};

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
	 *            固定密钥号
	 * @throws Exception
	 *             无法初始化
	 */
	public KeyCoderOld(String inkey, int strid) throws Exception {
		// this.systemId = strid;
		this.ranKey = inkey;
		strKey = this.ranKey + oldKey[strid];
	}

	/**
	 * 以固定密钥号初始化本类 一般用于加密
	 * 
	 * @param strid
	 *            固定密钥号
	 * @throws Exception
	 *             无法初始化
	 */
	public KeyCoderOld(int strid) throws Exception {
		this.ranKey = this.getRandomString();
		// this.systemId = strid;
		strKey = this.ranKey + oldKey[strid];
	}

	/**
	 * 对目标字符数组加密
	 * 
	 * @param arrB
	 *            需加密的字符数组
	 * @return 加密后的字符数组
	 * @throws Exception
	 *             无法加密
	 */
	private byte[] encrypt(byte[] arrB) throws Exception {

		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key keyword = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, keyword);

		return encryptCipher.doFinal(arrB);
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
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 对目标字符数组解密
	 * 
	 * @param arrB
	 *            需解密的字符数组
	 * @return 解密后的字符数组
	 * @throws Exception
	 *             无法解密
	 */
	private byte[] decrypt(byte[] arrB) throws Exception {

		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key keyword = getKey(strKey.getBytes());
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, keyword);
		return decryptCipher.doFinal(arrB);
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
	public String decrypt(String strIn) throws Exception {
		try {
			return new String(decrypt(hexStr2ByteArr(strIn)));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据给定的字符数组获取可用DES加密KEY
	 * 
	 * @param arrBTmp
	 *            获取KEY用的参数数组
	 * @return DES加密KEY
	 * @throws Exception
	 *             无法获取KEY
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// DES加密需要8位的KEY
		byte[] arrB = new byte[8];

		// 取出组合KEY的前8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成KEY
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

}
