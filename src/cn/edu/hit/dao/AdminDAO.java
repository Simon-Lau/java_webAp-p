package cn.edu.hit.dao;

import net.sf.json.JSONArray;
import tools.db.DataDAO;
import cn.edu.hit.entity.Admin;

public class AdminDAO {
	public JSONArray selectAllAdmin() {
		JSONArray items = new JSONArray();
		String selectAllAdminSql = "select * from admin";
		DataDAO dd = new DataDAO();
		try {
			items = dd.query(Admin.class, selectAllAdminSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dd.close();
		return items;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//OK
		AdminDAO a = new AdminDAO();
		System.out.println("selectAllAdmin(): " + a.selectAllAdmin());
	}
}
