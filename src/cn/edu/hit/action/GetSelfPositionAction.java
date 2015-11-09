package cn.edu.hit.action;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import cn.edu.hit.dao.LocationDAO;

import com.opensymphony.xwork2.ActionSupport;

public class GetSelfPositionAction extends ActionSupport implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7553962892388588955L;
	private JSONObject jsonObject;
    private Map<String,Object> session;
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String execute(){
		jsonObject = new JSONObject();
		String uid = (String) session.get("uid");
		JSONArray jsonArray = new LocationDAO().selectLocation(uid);
		String lng = jsonArray.getJSONObject(0).getString("lang");
		String lat = jsonArray.getJSONObject(0).getString("lat");
		jsonObject.put("lng", lng);
		jsonObject.put("lat", lat);
		jsonObject.put("uid", uid);
		return SUCCESS;
	}
}
