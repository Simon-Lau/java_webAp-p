package cn.edu.hit.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import cn.edu.hit.entity.Notice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.db.DataDAO;

public class NoticeDAO {
	
	public int insertNotice(String aid, String content) {
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String insertNoticeSql = "insert into notice set aid=?, content=?, time=?";
		try {
			result = dd.update("insert notice", insertNoticeSql, aid, content, sdf.format(new Date()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
	public void deleteNotice() {
		
	}
	public void updateNotice() {
		
	}

	public JSONArray selectNotice(String aid) {
		JSONArray items = new JSONArray();
		String selectNoticeSql = "select * from notice where aid=? order by time desc";
		DataDAO dd = new DataDAO();
		try {
			items = dd.query(Notice.class, selectNoticeSql, aid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return items;
	}
}
