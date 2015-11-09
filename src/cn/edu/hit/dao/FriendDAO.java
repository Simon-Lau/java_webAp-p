package cn.edu.hit.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.db.DataDAO;
import cn.edu.hit.entity.Friend;

public class FriendDAO {
	public JSONArray selectFriendById(String uid) {
		//	uid = 1+"";
			JSONArray items = new JSONArray();
			String selectFriendByIdSql = "select fid from friend where uid=?";
			DataDAO dd = new DataDAO();
			try {
				items = dd.query(Friend.class, selectFriendByIdSql, uid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dd.close();
			return items;
		}
	public int insertFriend(String uid, String fid) {
		//plus datetime
		if (Integer.parseInt(uid) > Integer.parseInt(fid)) {
			int tmp = Integer.parseInt(uid);
			uid = fid;
			fid = tmp+"";
		}
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String insertFriendSql = "insert into friend set uid=?, fid=?, time=?";
		try {
			result = dd.update("insertFriend", insertFriendSql, uid, fid, sdf.format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		dd.close();
		return (Integer)result.get("resultflag");
	}
	
	public int deleteFriend(String uid, String fid) {
		if (Integer.parseInt(uid) > Integer.parseInt(fid)) {
			int tmp = Integer.parseInt(uid);
			uid = fid;
			fid = tmp+"";
		}
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();
		String deleteFriendSql = "delete from friend where uid=? and fid=?";
		try {
			result = dd.update("deleteFriend", deleteFriendSql, uid, fid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dd.close();
		return (Integer)result.get("resultflag");
	}
}
