package ont.paarma.test.UserTests;

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
    
    @Before
    public void setUp() {
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.HSQL).addScript("db/sql/create.sql")
    		.addScript("db/sql/insert.sql").build();
    }
    
    @After
    public void tearDown() {
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
    	//first add user to db
    	User user = TestUtil.createDBUser();
    	User returnedAdd = userDao.addUser(user);
    	User returnedFind = userDao.findById(returnedAdd.getId());
    	Assert.assertNotNull(returnedFind);
    	Assert.assertEquals(returnedAdd.getId(), returnedFind.getId());
    	Assert.assertEquals(returnedAdd.getFirstName(), returnedFind.getFirstName());
    	Assert.assertEquals(returnedAdd.getLastName(), returnedFind.getLastName());
    }
    
    @Test
    public void testUpdateUser(){
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDAO userDao = new UserDAO();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	User user = TestUtil.createDBUpdateUser();
    	User returned = userDao.updateUser(user);
    	Assert.assertNotNull(returned);
    	Assert.assertEquals(user.getId(), returned.getId());
    	Assert.assertEquals(user.getFirstName(), returned.getFirstName());
    	Assert.assertEquals(user.getLastName(), returned.getLastName());
    }
}
