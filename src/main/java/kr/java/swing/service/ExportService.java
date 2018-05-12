package kr.java.swing.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.List;

import kr.java.swing.db.DBCon;

public class ExportService extends AbstractService{

	@Override
	public void service() {
		DataBaseDao.getInstance().executeQueryUpdate("USE " + DBCon.getInstance().getDbName());
		checkBackupDir();
		List<String> tables = getTables();
		for(String tblName : tables) {
			exportData("select * from "+ tblName, getFilePath(tblName));
		}		
	}

	@Override
	public String getFilePath(String tableName) {
		String importPath = System.getProperty("user.dir")+ "\\BackupFiles\\";
		return String.format("%s%s.txt", importPath, tableName).replace("\\", "/");
	}
	
	private void checkBackupDir() {
		File backupDir=new File(System.getProperty("user.dir")+ "\\BackupFiles\\");
		
		if(backupDir.exists()) {
			for(File file : backupDir.listFiles()) {
				file.delete();
				System.out.printf("%s Delete Success! %n", file.getName());
			}
		} else {
			backupDir.mkdir();
			System.out.printf("%s make dir Success! %n", System.getProperty("user.dir")+ "\\BackupFiles\\");
		}
	}
	
	private void exportData(String sql, String exportPath){
		StringBuilder sb = new StringBuilder();
		try (ResultSet rs = dao.executeQuery(sql)){
			int colCnt = rs.getMetaData().getColumnCount();// 컬럼의 개수
			while (rs.next()) {
				for (int i = 1; i <= colCnt; i++) { 
					sb.append(rs.getObject(i) + ",");
				}
				sb.replace(sb.length() - 1, sb.length(), ""); // 마지막 라인의 comma 제거
				sb.append("\r\n");
			}
			backupFileWrite(sb.toString(), exportPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.close();
		}
	}
	
	private void backupFileWrite(String str, String exportPath) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		try(OutputStreamWriter dos = new OutputStreamWriter(new FileOutputStream(exportPath),"UTF-8")){
			dos.write(str);
		} 	
	}

}
