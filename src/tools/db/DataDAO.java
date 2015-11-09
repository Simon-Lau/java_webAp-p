package tools.db;

//import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tools.mistools.DataBase;

public class DataDAO {

	private QueryRunner runner = new QueryRunner(true);
	//private static Connection con = null;

	public DataDAO() {
		/*con = DataBase.GetConnection();
		if (con == null)
			System.out.println("null");
		else
			System.out.println("not null");*/
	}

	/*
	 * 执行SELECT语句.(sql语句有参数)
	 * 
	 * @param sql
	 * 
	 * @param params
	 */
	@SuppressWarnings("unchecked")
	public <T> JSONArray query(Class<T> beanClass, String sql, Object... params) {
		JSONArray jsonArray = null;
		try {
			List<T> list = (List<T>) runner.query(DataBase.getConnection(), sql,
					new BeanListHandler(beanClass), params);
			jsonArray = JSONArray.fromObject(list);
			System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	/*
	 * 返回ResultSet对象
	 * 
	 * @param sql sql语句
	 * 
	 * @return resultSet 获得的结果集
	 */
	public ResultSet getResultSet(String sql) {
		ResultSet resultSet = null;
		try {
			Statement statement = DataBase.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/*
	 * 执行INSERT、UPDATE、DELETE语句.(sql语句有参数)
	 * 
	 * @param sql
	 * 
	 * @param params
	 */
	public JSONObject update(String operation, String sql, Object... params) {
		String resultmsg = "";
		int resultFlag = 0;
		try {
			// 语句执行成功
			if (runner.update(DataBase.getConnection(), sql, params) > 0) {
				resultmsg += operation + "执行成功！";
				resultFlag = 1;
			} else
				resultmsg += operation + "执行失败！";
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultmsg", resultmsg);
		jsonObject.put("resultflag", resultFlag);
		return jsonObject;
	}

	public void close() {
		DataBase.CloseConnection();
	}
}
