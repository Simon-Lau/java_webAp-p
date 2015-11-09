package cn.edu.hit.dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.db.DataDAO;
import cn.edu.hit.entity.User;

public class UserDAO {
	/**
	 * 删除用户
	 * @param userid
	 * @return
	 */
	public int deleteUser(String userid) {
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();	
		String deleteUserSql = "delete from user where userid=?";
		try {
			result = dd.update("删除用户userid="+userid, deleteUserSql, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
	/**
	 * 插入一个用户
	 * @param username
	 * @param pwd
	 * @param state
	 * @param pic
	 * @param sentence
	 * @param ip
	 * @return
	 */
	public int insertUser(String username, int pwd, int state, String pic, 
			String sentence, String ip) {
		JSONObject result = new JSONObject();
		DataDAO dd = new DataDAO();
		String insertUserSql = "insert into user set username=?, pwd=?, state=?," +
				" pic=?, sentence=?, ip=?;";
		try {
			result = dd.update("新增用户", insertUserSql, username, pwd, state,
					pic, sentence, ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
	/**
	 * 获取某个具体用户的全部信息
	 * @param uid
	 * @return
	 */
	public JSONArray getUserByName(String username) {
	//	uid = 1+"";
		JSONArray items = new JSONArray();
		String SQL = "select * from user where username=?";
		DataDAO dd = new DataDAO();
		try {
			items = dd.query(User.class, SQL, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dd.close();
		return items;
	}
	
	public JSONArray getUserById(String uid){
		JSONArray items = new JSONArray();
		String SQL = "select username, pic,sentence,userid from user where userid=?";
		DataDAO dd = new DataDAO();
		try{
			items = dd.query(User.class, SQL, uid);
		}catch(Exception e){
			e.printStackTrace();
		}
		dd.close();
		return items;
	}
	
	public JSONArray getAllUser(){
		JSONArray items = new JSONArray();
		String SQL = "select userid,username,sentence from user";
		DataDAO dd = new DataDAO();
		try{
			items = dd.query(User.class, SQL);
		}catch(Exception e){
			e.printStackTrace();
		}
		dd.close();
		return items;
	}
	
	/**
	 * 更新某个用户的个性签名
	 * @param uid
	 * @param sentence
	 * @return
	 */
	public int updateSentenceById(String uid, String sentence){
		JSONObject result = new JSONObject();
		String updateSentenceByIdSql = "update user set sentence=?" +
				" where userid=?";
		DataDAO dd = new DataDAO();
		try {
			result = dd.update("更新用户签名userid="+uid, updateSentenceByIdSql,
					sentence, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
	/*
	 * 更新某个用户的状态state
	 */
	public int updateStateByName(String username, int state){
		JSONObject result = new JSONObject();
		String updateSateByIdSql = "update user set state=?" +
				" where username=?";
		DataDAO dd = new DataDAO();
		try {
			result = dd.update("更新用户状态username="+username, updateSateByIdSql,
					state, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer)result.get("resultflag");
	}
}
