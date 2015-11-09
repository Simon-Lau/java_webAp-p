package cn.edu.hit.action;

import net.sf.json.JSONObject;
import cn.edu.hit.dao.FriendDAO;

import com.opensymphony.xwork2.ActionSupport;

public class DeleteFriendAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3314484362570891424L;
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
	
	public String execute(){
		jsonObject = new JSONObject();
		jsonObject.put("resultflag",new FriendDAO().deleteFriend(uid, fid));
		return SUCCESS;
	}
}
