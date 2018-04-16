package kr.java.swing.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import kr.java.dbcon.DBCon;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseServiceTest {
	private static final Logger log = LogManager.getLogger();
	private static Connection connection;
	private static AbstractService importService;
	private static AbstractService initService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		connection = DBCon.getInstance().getConnection("conf.properties");
		importService = new ImportService();
		initService = new InitService();
		
		initService.service();
		importService.service();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DBCon.getInstance().connectionClose();
	}
	
	@Test
	public void testADbExists() throws SQLException{
		log.debug("testADbExists()");
		String sql = "SELECT EXISTS (SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'coffee') AS flag";// 데이터베이스 존재 확인
		int exist = getQueryResult(sql);
		Assert.assertEquals(1, exist);
	}

	@Test
	public void testBTableExist() throws SQLException {
		log.debug("testBTableExist()");
		String[] tableNames = {"product", "sale"};
		String dbName = "coffee";
		String sql = "SELECT EXISTS (SELECT 1 FROM Information_schema.tables WHERE table_name = '%s' AND table_schema = '%s' ) AS flag";// 데이터베이스내 테이블 확인
		for(String tblName: tableNames) {
			int exists = getQueryResult(String.format(sql,tblName, dbName));
			Assert.assertEquals(1,exists);
			log.trace(String.format("\t%s table %s", tblName, exists==1?"exit":"not exist"));
		}
	}
	
	@Test
	public void testCLoadData() throws SQLException {
		log.debug("testCLoadData()");
		
		String[] tableNames = {"product", "sale"};
		int[] cnts = {8,4};
		
		String sql = "select count(*) cnt from %s.%s";// 데이터베이스 존재 확인
		for(int i=0; i<tableNames.length; i++) {
			int exists = getQueryResult(String.format(sql,"coffee", tableNames[i]));
			Assert.assertEquals(cnts[i], exists);
			log.trace("   count(*) - " + exists);
		}
	}
	
	private int getQueryResult(String sql) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			result = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		return result;
	}
}
