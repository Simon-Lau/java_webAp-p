package tools.mistools;

import java.sql.Connection;
import java.sql.*;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;

import com.mchange.v2.c3p0.PooledDataSource;

public class DataBase {

	// ThreadLocal变量
	private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	// 数据源
	private static DataSource dataSource;

	/*
	 * 只调用一次 InitDataSource()方法
	 */
	static {
		InitDataSource(); // 初始化连接池
	}

	/*
	 * 初始化连接池
	 */
	private final static void InitDataSource() {
		try {
			Properties dbProperties = new Properties();
			dbProperties.load(DataBase.class
					.getResourceAsStream("db.properties"));
			Properties cp_props = new Properties(); // c3p0的Properties
			for (Object key : dbProperties.keySet()) {
				String skey = (String) key; // 取得属性名
				if (skey.startsWith("jdbc.")) {
					String name = skey.substring(5);
					cp_props.put(name, dbProperties.getProperty(skey));
				}

			}
			dataSource = (DataSource) Class.forName(
					cp_props.getProperty("datasource")).newInstance();
			if (dataSource.getClass().getName().indexOf("c3p0") > 0) {
				// Disable JMX in C3P0
				System
						.setProperty(
								"com.mchange.v2.c3p0.management.ManagementCoordinator",
								"com.mchange.v2.c3p0.management.NullManagementCoordinator");
			}

			BeanUtils.populate(dataSource, cp_props);

			System.out.println("初始化连接池完成.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取一个连接
	 */
	@SuppressWarnings("deprecation")
	public final static Connection getConnection() {
		Connection conn = conns.get();
		PooledDataSource pds = (PooledDataSource) dataSource;
		try {
			if (conn == null || conn.isClosed()) {
				conn = dataSource.getConnection();
				conns.set(conn);
				System.out.println("获取一个连接，新的.");
				System.out.println("当前连接数量: " + pds.getNumConnections());
			}
			System.out.println("获取连接，自动的.");
			System.out.println("当前连接数量: " + pds.getNumConnections());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/*
	 * 关闭连接
	 */
	@SuppressWarnings("deprecation")
	public final static void CloseConnection() {
		Connection conn = conns.get();
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
				System.out.println("关闭一个连接.");
				PooledDataSource pds = (PooledDataSource) dataSource;
				System.out.println("当前连接数量: " + pds.getNumConnections());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conns.set(null);
	}

	/*
	 * 关闭连接池
	 */
	public final static void CloseDataSource() {
		try {
			dataSource.getClass().getMethod("close").invoke(dataSource);
			System.out.println("关闭连接池.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
