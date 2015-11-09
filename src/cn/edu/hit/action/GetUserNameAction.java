package cn.edu.hit.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.hit.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class GetUserNameAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4193303718446646664L;
	private JSONObject jsonObject;
	private String uid;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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
			JSONArray jsonArray = new UserDAO().getUserById(uid);	
			String username = jsonArray.getJSONObject(0).getString("username");
			jsonObject.put("username", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
