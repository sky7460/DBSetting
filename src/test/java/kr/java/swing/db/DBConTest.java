package kr.java.swing.db;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBConTest {
	static final Logger log = LogManager.getLogger();
	
	@Test
	public void testAConnection() {
		log.debug("testConnection()");
		Connection con = DBCon.getInstance().getConnection("conf.properties");
		Assert.assertNotNull(con);
		log.trace("\tcon - " + con);
	}
	
	@Test
	public void testBClose() {
		log.debug("testClose()");
		DBCon.getInstance().connectionClose();
		Assert.assertNull(null, DBCon.getInstance().getConnection());
		log.trace("\tDBCon.getInstance().getConnection() - " + DBCon.getInstance().getConnection());
	}
	
	@Test
	public void testCDbName() {
		log.debug("testCDbName()");
		String dbName = DBCon.getInstance().getDbName();
		Assert.assertEquals("coffee", dbName);
		log.trace("\tdbName - " + dbName);
	}
	
/*	@Test
	public void testDNullDbName() {
		log.debug("testDNullDbName()");
		DBCon.getInstance().getConnection("conf_no_dbname.properties");
		String dbName = DBCon.getInstance().getDbName();
		Assert.assertNotEquals("coffee", dbName);
		log.trace("\tdbName - " + dbName);
	}*/
}
