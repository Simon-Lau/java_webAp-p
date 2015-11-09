package cn.edu.hit.dao;

import net.sf.json.JSONObject;
import tools.db.DataDAO;

public class BlacklistDAO {
	/**
	 * 将用户加入黑名单
	 * @param uid
	 * @return
	 */
	public int insertBlacklist(String uid) {
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();
		String insertBlacklistSql = "insert into blacklist set uid=?;";
		try {
			result = dd.update("插入日志", insertBlacklistSql, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
	
	/**
	 * 增加举报次数
	 * @param uid
	 * @return
	 */
	public int addReportNum(String uid){
		JSONObject result = new JSONObject();
		String addReportNumSql = "update blacklist set " +
				"reportnum=reportnum+1 where uid=?";
		DataDAO dd = new DataDAO();
		try {
			result = dd.update("增加举报次数userid="+uid, addReportNumSql, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
	/**
	 * 将用户移出黑名单
	 * @param uid
	 * @return
	 */
	public int deleteBlacklist(String uid) {
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();	
		String deleteBlacklistSql = "delete from blacklist where uid=?";
		try {
			result = dd.update("删除用户userid="+uid, deleteBlacklistSql, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlacklistDAO b = new BlacklistDAO();
		//测试getDepartmentLogs()，已测试
	//	System.out.println("getDepartmentLogs(): " + u.getUserById("1"));
		//测试saveReview,已测试
	//	System.out.println("addReportNum(): "+b.addReportNum("3"));
		System.out.println("insertBlacklist(): " + b.insertBlacklist("1"));
	//	System.out.println("deleteBlacklist(): " + b.deleteBlacklist("6"));
	}
}
