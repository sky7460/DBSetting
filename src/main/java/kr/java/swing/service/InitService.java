package kr.java.swing.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;

public class InitService extends AbstractService{

	@Override
	public void service() {
		createTable("create_sql.txt");	// 데이터베이스 생성 및 테이블 생성 유저 생성
		createTriggerProcedure("create_procedure.txt");	// 프로시저 생성
/*		createTriggerProcedure("create_trigger.txt");	// 트리거 생성         */
	}

	private void createTable(String file) {
		try (InputStream is = ClassLoader.getSystemResourceAsStream("sql/"+file);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
			StringBuilder statement = new StringBuilder();
			for (String line; (line = br.readLine()) != null;) {
				if (!line.isEmpty() && !line.startsWith("--")) {
					statement.append(line.trim());
				}
				if (line.endsWith(";")) {
					dao.execSQL(statement.toString());
					LogManager.getLogger().debug("createTable() - " + statement.toString());
					statement.setLength(0);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createTriggerProcedure(String file) {
		try (InputStream is = ClassLoader.getSystemResourceAsStream("sql/"+file);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
			StringBuilder statement = new StringBuilder();
			for (String line; (line = br.readLine()) != null;) {
				if (!line.isEmpty() && !line.startsWith("--")) {
					statement.append(line);
				}
				if (line.endsWith("END;")) {
					dao.execSQL(statement.toString());
					LogManager.getLogger().debug("createTriggerProcedure() - " + statement.toString());
					statement.setLength(0);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getFilePath(String tableName) {
		throw new UnsupportedOperationException(); 
	}
	
}
