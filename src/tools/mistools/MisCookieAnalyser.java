// coding in utf8
package tools.mistools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * alter by wzhao 2011-10-20
 * 
 * @author wzhao
 */
public class MisCookieAnalyser {
	private String userid = "";
	private String username = "";
	private String password = "";
	private String level = "";
	private String dptid = "";
	private String sdptid = "";
	private String[] functions = new String[100];
	private String[] Check = new String[100];
	private String up24 = "";
	private String up48 = "";

	/**
	 * 初始化Cookie解析器
	 * 
	 * @param CookieString
	 *            Cookie内容
	 * @throws Exception
	 *             初始化失败
	 */
	public MisCookieAnalyser(String CookieString) throws Exception {
		this.initCookie(CookieString);
	}

	/**
	 * 初始化Cookie解析器
	 * 
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP回应
	 * @param errorPage
	 *            失败时响应页面
	 */
	public MisCookieAnalyser(HttpServletRequest request,
			HttpServletResponse response, String errorPage) throws Exception{
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			response.sendRedirect(errorPage);
		}
		String CookieString = "";
		int total = cookies.length;
		int count_cookie = 0;
		for (count_cookie = 0; count_cookie < total; ++count_cookie) {
			// 是MIS的COOKIE
			if (cookies[count_cookie].getName().equals("MisCookie")) {
				CookieString = cookies[count_cookie].getValue();
				Cookie tempcookie = cookies[count_cookie];
				// 清除原有数据
				tempcookie.setMaxAge(0);
				tempcookie.setDomain(".hl.bank-of-china.com");
				response.addCookie(tempcookie);

				// 数据重写
				tempcookie = cookies[count_cookie];
				tempcookie.setMaxAge(10000);
				tempcookie.setDomain(".hl.bank-of-china.com");
				response.addCookie(tempcookie);
			} else {
				// 不是MIS的COOKIE,删除
				Cookie tempcookie = cookies[count_cookie];
				tempcookie.setMaxAge(0);
				tempcookie.setDomain(".hl.bank-of-china.com");
				response.addCookie(tempcookie);
			}
		}

		if (CookieString.equals("") || CookieString.length() == 0) {
			System.out.println(errorPage);
			response.sendRedirect(errorPage);
		} else {
			this.initCookie(CookieString);
		}
	}
	
	/**
	 * 初始化COOKIE解析器
	 * 
	 * @param cookieString COOKIE内容
	 * @throws Exception 无法初始化
	 */
	private void initCookie(String cookieString) throws Exception {
		String[] temp = cookieString.split(">>");
		if (temp.length < 3) {
			throw new Exception("Cookie Format Error!");
		}
		String[] ckid = temp[0].split(":");
		if (ckid.length != 2 || !ckid[0].toString().equals("CCKID")) {
			throw new Exception("Cookie Format Error!");
		}
		String CCKID = ckid[1].toString();

		String[] sysid = temp[1].split(":");
		if (sysid.length != 2 || !sysid[0].toString().equals("CSYSID")) {
			throw new Exception("Cookie Format Error!");
		}
		int CSYSID = Integer.parseInt(sysid[1].toString());

		System.out.println("sysid=" + CSYSID);
		// TODO:这里注意,如果特定系统更新至新加密方法,则需要对应的修改此处逻辑
		KeyCoder kcoder = (CSYSID < 15 && CSYSID >= 0) ? new KeyCoderOld(CCKID,CSYSID) : new KeyCoderNew(CCKID, CSYSID);
		String usefulCookie = kcoder.decrypt(temp[2]);
		String[] myCookies = usefulCookie.split(">>");
		int countCookies = 0;
		while (countCookies < myCookies.length) {
			String[] oneCookie = myCookies[countCookies].split(":");
			if (oneCookie.length != 2) {
				++countCookies;
				continue;
			} else {
				if (oneCookie[0].equals("CUSERID")) {
					this.userid = oneCookie[1];
				} else if (oneCookie[0].equals("CUSERNAME")) {
					this.username = oneCookie[1];
				} else if (oneCookie[0].equals("CPWD")) {
					this.password = oneCookie[1];
				} else if (oneCookie[0].equals("CLEVEL")) {
					this.level = oneCookie[1];
				} else if (oneCookie[0].equals("CDEP")) {
					this.dptid = oneCookie[1];
				} else if (oneCookie[0].equals("CSDEP")) {
					this.sdptid = oneCookie[1];
				} else if (oneCookie[0].equals("CFUN")) {
					if (oneCookie[1] != null)
						this.functions = oneCookie[1].split(",");
				} else if (oneCookie[0].equals("CCHECK")) {
					if (oneCookie[1] != null)
						this.Check = oneCookie[1].split(";");
				} else if (oneCookie[0].equals("CU24")) {
					this.up24 = oneCookie[1];
				} else if (oneCookie[0].equals("CU48")) {
					this.up48 = oneCookie[1];
				} 
			}
			countCookies++;
		}
	}

	/**
	 * 获取用户ID
	 * 
	 * @return 用户ID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 获取用户等级
	 * 
	 * @return 用户等级
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * 获取用户所属机构
	 * 
	 * @return 所属机构
	 */
	public String getDptid() {
		return dptid;
	}

	/**
	 * 获取用户所属上级机构
	 * 
	 * @return 上级机构
	 */
	public String getSdptid() {
		return sdptid;
	}

	/**
	 * 获取用户可用功能列表
	 * 
	 * @return 功能列表
	 */
	public String[] getFunctions() {
		return functions;
	}

	/**
	 * 获取授权人列表
	 * 
	 * @return 授权人列表
	 */
	public String[] getCheck() {
		return Check;
	}
	
	/**
	 * 获取所属24家上级
	 * 
	 * @return
	 */
	public String getUp24() {
		return up24;
	}

	/**
	 * 获取所属48家上级
	 * 
	 * @return
	 */
	public String getUp48() {
		return up48;
	}

	/**
	 * 检查输入功能名是否可被此用户使用
	 * 
	 * @param infunction
	 *            待检查功能名
	 * @return 是否成功
	 */
	public boolean checkFunction(String infunction) {
		int countfunction = 0;
		while (countfunction < functions.length) {
			if (infunction.equals(functions[countfunction].toString())) {
				return true;
			}
			++countfunction;
		}
		return false;
	}
}
