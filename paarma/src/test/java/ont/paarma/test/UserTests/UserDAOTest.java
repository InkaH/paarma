package ont.paarma.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ont.paarma.dao.UserDAO;
import ont.paarma.model.User;

public class UserDAOTest {
	
	private static EmbeddedDatabase db;
    UserDAO userDao;
    
    @BeforeClass
    public static void setUp() {
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.HSQL).addScript("db/sql/create-db.sql")
    		.addScript("db/sql/insert-data.sql").build();
    }
    
    @AfterClass
    public static void tearDown() {
        db.shutdown();
    }

    @Test
    public void testAddUser(){
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDAO userDao = new UserDAO();
    	userDao.setNamedParameterJdbcTemplate(template);
    	User user = TestUtil.createDBUser();
    	User returned = userDao.addUser(user);
    	Assert.assertNotNull(returned);
    	Assert.assertEquals(4, returned.getId());
    	Assert.assertEquals(user.getFirstName(), returned.getFirstName());
    	Assert.assertEquals(user.getLastName(), returned.getLastName());
    }
    
    @Test
    public void testFindById() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDAO userDao = new UserDAO();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	User user = userDao.findById(4);
    	Assert.assertNotNull(user);
    	Assert.assertEquals(4, user.getId());
    	Assert.assertEquals("testFirst", user.getFirstName());
    	Assert.assertEquals("testLast", user.getLastName());
    }
}
