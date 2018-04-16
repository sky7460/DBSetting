package kr.java.swing.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class InitServiceTest {
	private static final Logger log = LogManager.getLogger();
	private static AbstractService initService;
	private static DataBaseDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		initService = new InitService();
		initService.service();
		dao = DataBaseDao.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao.execSQL("drop database coffee");
	}


	@Test
	public void testDatabaseExists() throws SQLException{
		log.debug("testDatabaseExists()");
		String sql = "SELECT EXISTS (SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'coffee') AS flag";// 데이터베이스 존재 확인
		try(ResultSet rs= dao.execQueryRes(sql)){
			rs.next();
			Assert.assertEquals(1, rs.getInt(1));
			log.trace(String.format("\t%s database %s", "coffee", rs.getInt(1)==1?"exist":"not exist"));
		}
	}

	@Test
	public void testTableExist() throws SQLException {
		log.debug("testTableExist()");
		String[] tableNames = {"product", "sale"};
		String dbName = "coffee";
		String sql = "SELECT EXISTS (SELECT 1 FROM Information_schema.tables WHERE table_name = '%s' AND table_schema = '%s' ) AS flag";// 데이터베이스내 테이블 확인
		for(String tblName: tableNames) {
			try(ResultSet rs= dao.execQueryRes(String.format(sql,tblName, dbName))){
				rs.next();
				Assert.assertEquals(1, rs.getInt(1));
				log.trace(String.format("\t%s table %s", tblName, rs.getInt(1)==1?"exist":"not exist"));
			}
		}
	}

}
