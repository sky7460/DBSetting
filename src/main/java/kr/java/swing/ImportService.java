package kr.java.swing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.java.dbcon.DBCon;

public class ImportService{
	
	private DataBaseDao dao;
	
	public ImportService() {
		dao= DataBaseDao.getInstance();
	}
	
	public void service() {	
		dao.execSQL("SET FOREIGN_KEY_CHECKS = 0");
		dao.execSQL("use " + DBCon.getInstance().getDbName());
		List<String> tables = getTables();
		for (String tableName : tables) {
			dao.execSQL(String.format("LOAD DATA LOCAL INFILE '%s' IGNORE INTO TABLE %s character set 'UTF8' fields TERMINATED by ','",getFilePath(tableName), tableName));
		}
		dao.execSQL("SET FOREIGN_KEY_CHECKS = 1");		
	}

	private List<String> getTables() {
		List<String> tables = new ArrayList<>();
		try (ResultSet rs =dao.execQueryRes("show tables")){
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

	private String getFilePath(String tableName) {
		String importPath = System.getProperty("user.dir")+ "\\DataFiles\\";
		return String.format("%s%s.txt", importPath, tableName).replace("\\", "/");
	}
}
