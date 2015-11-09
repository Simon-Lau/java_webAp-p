package cn.edu.hit.dao;

import cn.edu.hit.entity.Location;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.db.DataDAO;

public class LocationDAO {

	public int insertLocation(String uid, String location, String lang,
			String lat) {
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();
		String insertLocationSql = "insert into location set uid=?,"
				+ "location=?,lang=?,lat=?";
		try {
			result = dd.update("insertlocation", insertLocationSql, uid,
					location, lang, lat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer) result.get("resultflag");
	}

	public JSONArray selectAllLocation() {
		JSONArray items = new JSONArray();
		String selectAllLocationSql = "select * from location";
		DataDAO dd = new DataDAO();
		try {
			items = dd.query(Location.class, selectAllLocationSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public JSONArray selectLocation(String uid) {
		JSONArray items = new JSONArray();
		String selectLocationSql = "select * from location where uid=?";
		DataDAO dd = new DataDAO();
		try {
			items = dd.query(Location.class, selectLocationSql, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public int updateLocation(String uid, String location, String lang,
			String lat) {
		JSONObject result = new JSONObject();
		String updateLocationSql = "update location set location=?, "
				+ "lang=?, lat=? where uid = ?";
		DataDAO dd = new DataDAO();
		try {
			result = dd.update("update location", updateLocationSql, location,
					lang, lat, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer) result.get("resultflag");
	}
}
