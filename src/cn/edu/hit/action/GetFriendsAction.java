package cn.edu.hit.action;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import cn.edu.hit.dao.FriendDAO;
import cn.edu.hit.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class GetFriendsAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSONObject jsonObject;
	private Map<String,Object> session;
	
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public String execute(){
		jsonObject = new JSONObject();
		String uid = (String) session.get("uid");
		System.out.println("getFriendAction+"+session.hashCode());
		System.out.println("uid="+uid);
		JSONArray array = null;
		JSONArray items = new JSONArray();
		JSONObject js = null;
		try {
				JSONArray jsonArray = new FriendDAO().selectFriendById(uid);
				for(int i = 0; i<jsonArray.size();i++){
					array = new JSONArray();
					String fid = jsonArray.getJSONObject(i).getString("fid");
					js = new UserDAO().getUserById(fid).getJSONObject(0);
					array.add(js.getString("pic"));
					array.add(js.getString("username"));
					array.add(js.getString("sentence"));
					array.add(js.getString("userid"));
					items.add(array);
				}
				System.out.println("success");
				jsonObject.put("friendList", items);
				jsonObject.put("userid", uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
