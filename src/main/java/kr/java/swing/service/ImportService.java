package kr.java.swing.service;

import java.util.List;

import kr.java.swing.db.DBCon;

public class ImportService extends AbstractService{
	
	public ImportService() {
		dao= DataBaseDao.getInstance();
	}
	
	@Override
	public void service() {	
		dao.executeQueryUpdate("SET FOREIGN_KEY_CHECKS = 0");
		dao.executeQueryUpdate("use " + DBCon.getInstance().getDbName());
		List<String> tables = getTables();
		for (String tableName : tables) {
			dao.executeQueryUpdate(String.format("LOAD DATA LOCAL INFILE '%s' IGNORE INTO TABLE %s character set 'UTF8' fields TERMINATED by ','",getFilePath(tableName), tableName));
		}
		dao.executeQueryUpdate("SET FOREIGN_KEY_CHECKS = 1");		
	}

	@Override
	public String getFilePath(String tableName) {
		String importPath = System.getProperty("user.dir")+ "\\DataFiles\\";
		return String.format("%s%s.txt", importPath, tableName).replace("\\", "/");
	}
}
