package cn.edu.hit.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.hit.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class GetAllUserAction extends ActionSupport{
	private JSONObject jsonObject;

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
		JSONArray jsonArray = new UserDAO().getAllUser();
		for(int i = 0 ;i<jsonArray.size();i++){
			array = new JSONArray();
			array.add(jsonArray.getJSONObject(i).getString("username"));
			array.add(jsonArray.getJSONObject(i).getString("sentence"));
			array.add(jsonArray.getJSONObject(i).getString("userid"));
			items.add(array);
		}
		jsonObject.put("userInfo", items);
		return SUCCESS;
	}
	
}
