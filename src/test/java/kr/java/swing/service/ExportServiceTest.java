package kr.java.swing.service;

import java.io.File;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExportServiceTest {
	private static final Logger log = LogManager.getLogger();
	private static AbstractService importService;
	private static AbstractService initService;
	private static AbstractService exportService;
	private static DataBaseDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		initService = new InitService();
		importService = new ImportService();
		exportService = new ExportService();
		
		initService.service();
		importService.service();
		exportService.service();
		dao = DataBaseDao.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao.executeQueryUpdate("drop database coffee");
	}


	@Test
	public void testExistBackupDir() throws SQLException {
		log.debug("testExistBackupDir()");
		
		File backupDir=new File(System.getProperty("user.dir")+ "\\BackupFiles\\");
		Assert.assertEquals(true, backupDir.exists());
		log.trace(String.format("\tBackupFiles %s", backupDir.exists()?"exist":"not exist"));
	}

	@Test
	public void testExistBackupFiles() throws SQLException {
		log.debug("testExistBackupFiles()");
		
		File backupDir=new File(System.getProperty("user.dir")+ "\\BackupFiles\\");
		
		for(File file : backupDir.listFiles()) {
			Assert.assertEquals(true, file.exists());
			log.trace(String.format("\tfile %s", file.exists()?file.getName():"not exist"));
		}
		
	}
}
