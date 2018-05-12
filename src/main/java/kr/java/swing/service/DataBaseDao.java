package kr.java.swing.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.java.swing.db.DBCon;

public class DataBaseDao {
	private static DataBaseDao instance = new DataBaseDao();
	private Connection connection;
	private PreparedStatement pstmt;

	private DataBaseDao() {
		connection = DBCon.getInstance().getConnection("conf.properties");
	}

	public static DataBaseDao getInstance() {
		return instance;
	}

	public void executeQueryUpdate(String sql) {
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("%s - %s%n", e.getErrorCode(), e.getMessage());
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		pstmt = connection.prepareStatement(sql);
		return pstmt.executeQuery();
	}

	public void close() {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
