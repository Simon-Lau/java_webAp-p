package cn.edu.hit.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.hit.dao.LocationDAO;
import cn.edu.hit.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class GetPositionAction extends ActionSupport{

	private JSONObject jsonObject;
	private String uid;
	
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

	public String execute(){
		jsonObject = new JSONObject();
		JSONArray array = null;
		JSONArray items = new JSONArray();
		JSONObject js = null;
		JSONArray jsonArray = new LocationDAO().selectAllLocation();
		for(int i = 0; i<jsonArray.size();i++){
			array = new JSONArray();
			js = jsonArray.getJSONObject(i);
			String uid = js.getString("uid");
			array.add(uid);
			array.add(new UserDAO().getUserById(uid).getJSONObject(0).getString("username"));
			array.add(new UserDAO().getUserById(uid).getJSONObject(0).getString("sentence"));
			array.add(new UserDAO().getUserById(uid).getJSONObject(0).getString("pic"));
			array.add(js.getString("lang"));
			array.add(js.getString("lat"));
			items.add(array);
		}
		jsonObject.put("position", items);
		//重组array数组
		return SUCCESS;
	}
}
