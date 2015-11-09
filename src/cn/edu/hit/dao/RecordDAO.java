package cn.edu.hit.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.db.DataDAO;
import cn.edu.hit.entity.Record;

public class RecordDAO {
	public int insertRecord(String uid, String fid, String content) {
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String insertFriendSql = "insert into record set uid=?, fid=?, time=?, content=?";
		try {
			result = dd.update("insertFriend", insertFriendSql, uid, fid,
					sdf.format(new Date()), content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer) result.get("resultflag");
	}

	public JSONArray selectRecord(String uid, String fid) {
		JSONArray items = new JSONArray();
		String selectRecordSql = "select * from record where (uid=? and fid=?) or(uid=? and fid=?) order by rid asc";
		DataDAO dd = new DataDAO();
		try {
			items = dd.query(Record.class, selectRecordSql, uid, fid, fid, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dd.close();
		return items;
	}
}
