package kr.java.swing.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImportServiceTest {
	private static final Logger log = LogManager.getLogger();
	private static AbstractService importService;
	private static AbstractService initService;
	private static DataBaseDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		initService = new InitService();
		importService = new ImportService();
		
		initService.service();
		importService.service();
		dao = DataBaseDao.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao.executeQueryUpdate("drop database coffee");
	}


	@Test
	public void testLoadData() throws SQLException  {
		log.debug("testCLoadData()");
		
		String[] tableNames = {"product", "sale"};
		int[] cnts = {8,4};
		
		String sql = "select count(*) cnt from %s.%s";// 데이터베이스 존재 확인
		for(int i=0; i<tableNames.length; i++) {
			try(ResultSet rs= dao.executeQuery(String.format(sql,"coffee", tableNames[i]))){
				rs.next();
				Assert.assertEquals(cnts[i], rs.getInt(1));
				log.trace("   count(*) - " + rs.getInt(1));
			}
		}
	}


}
