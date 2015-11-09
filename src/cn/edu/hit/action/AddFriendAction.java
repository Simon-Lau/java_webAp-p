package cn.edu.hit.action;

import net.sf.json.JSONObject;
import cn.edu.hit.dao.FriendDAO;

import com.opensymphony.xwork2.ActionSupport;

public class AddFriendAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7553137425332211655L;
	private JSONObject jsonObject;
	private String uid;
	private String fid;
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String execute() {
		jsonObject = new JSONObject();
		try {
			jsonObject.put("resultflag", new FriendDAO().insertFriend(uid, fid));
		} catch (Exception e) {
			jsonObject.put("resultflag", 0);
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
