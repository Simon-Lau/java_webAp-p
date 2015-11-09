package cn.edu.hit.action;


import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import cn.edu.hit.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6496684535423223364L;
	private JSONObject jsonObject;
	private String username;
	private String pwd;
	private Map<String, Object> session;
	
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String execute() {
		jsonObject = new JSONObject();
		try {
			JSONArray jsonArray = new UserDAO().getUserByName(username);
			System.out.println("success");
			if (jsonArray == null) {
				jsonObject.put("resultflag", 0);
			} else {
				JSONObject one = jsonArray.getJSONObject(0);
				if (pwd.equals((String)one.getString("pwd"))) {
					new UserDAO().updateStateByName(username,1);
					String userid = jsonArray.getJSONObject(0).getString("userid");
					session.put("state", 1);
					session.put("uid", userid);
					session.put("username",username);
					jsonObject.put("resultflag", 1);
					System.out.println("loginAction+"+session.hashCode());
				} else {
					jsonObject.put("resultflag", -1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
}
