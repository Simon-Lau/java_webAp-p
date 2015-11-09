// coding in utf8
package tools.mistools;

/**
 * 加解密器的接口
 * 
 * @author wzhao
 */
public interface KeyCoder {
	
	/**
	 * 获取密钥
	 * 
	 * @return
	 */
	public String getKey();
	
	/**
	 * 对目标字符串加密
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 *             无法加密
	 */
	public String encrypt(String strIn) throws Exception;
	
	/**
	 * 对目标字符串解密
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 *             无法解密
	 */
	public String decrypt(String strIn) throws Exception;
}
