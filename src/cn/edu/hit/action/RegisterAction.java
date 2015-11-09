package cn.edu.hit.action;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.hit.dao.LocationDAO;
import cn.edu.hit.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSONObject jsonObject;
	private String username;
	private String pwd;
	private String pic;
	private String location;
	private String lang;
	private String lat;
	private String ip = "127.0.0.1";
	private String sentence = "欢迎加入天天聊！";

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String execute() {
		String local = null;
		try {
			local = new String(location.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(username+pwd+pic);
		jsonObject = new JSONObject();
		try {
			if (!new UserDAO().getUserByName(username).isEmpty()) {
				jsonObject.put("resultflag", -1);
			} else {
				int flag = new UserDAO().insertUser(username,
						Integer.parseInt(pwd), 0, pic, sentence, ip);
				JSONArray jsonArray = new UserDAO().getUserByName(username);
				String uid = jsonArray.getJSONObject(0).getString("userid");
				new LocationDAO().insertLocation(uid, local,lang,lat);
				System.out.println("success");
				jsonObject.put("resultflag", flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
