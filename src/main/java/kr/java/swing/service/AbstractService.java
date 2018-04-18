package kr.java.swing.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService {
	protected DataBaseDao dao;

	public AbstractService() {
		dao = DataBaseDao.getInstance();
	}

	public List<String> getTables() {
		List<String> tables = new ArrayList<>();
		try (ResultSet rs =dao.executeQuery("show tables")){
			while (rs.next()) {
				tables.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.close();
		}
		return tables;
	}

	public abstract String getFilePath(String tableName);
	
	public abstract void service();
}