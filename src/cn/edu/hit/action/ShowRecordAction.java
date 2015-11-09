package cn.edu.hit.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.hit.dao.RecordDAO;
import cn.edu.hit.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class ShowRecordAction extends ActionSupport {
     /**
	 * 
	 */
	private static final long serialVersionUID = -2060332644269772305L;
	private String uid;
     private String fid;
     private JSONObject jsonObject;
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
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
     
	public String execute(){
		jsonObject = new JSONObject();
		JSONArray array = null;
		JSONArray items = new JSONArray();
		JSONArray jsonArray = new RecordDAO().selectRecord(uid, fid);
		String username = new UserDAO().getUserById(uid).getJSONObject(0).getString("username");
		String fridname = new UserDAO().getUserById(fid).getJSONObject(0).getString("username");
		for(int i =0; i <jsonArray.size();i++){
			array = new JSONArray();
			if(jsonArray.getJSONObject(i).getString("uid").equals(uid)){
				array.add(username);
				array.add(fridname);
			}else{
				array.add(fridname);
				array.add(username);
			}
			array.add(jsonArray.getJSONObject(i).getString("time"));
			array.add(jsonArray.getJSONObject(i).getString("content"));
			items.add(array);
		}
		jsonObject.put("record", items);
		return SUCCESS;
	}
	
}
