package ont.paarma.test.ReservationTests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ont.paarma.dao.ReservationDAO;
import ont.paarma.dao.UserDAO;
import ont.paarma.model.Reservation;
import ont.paarma.model.User;
import ont.paarma.test.UserTests.TestUtil;

public class ReservationDAOTest {
	
	private static EmbeddedDatabase db;
    ReservationDAO reservationDao;
    
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
    public void testAddReservation(){
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	ReservationDAO reservationDao = new ReservationDAO();
    	reservationDao.setNamedParameterJdbcTemplate(template);
    	Reservation reservation = TestUtil.createReservationNoId();
    	Reservation returned = reservationDao.addReservation(reservation);
    	Assert.assertNotNull(returned);
    	Assert.assertEquals(4, returned.getId());
    	Assert.assertEquals(reservation.getStartDate(), returned.getStartDate());
    	Assert.assertEquals(reservation.getTable(), returned.getTable());
    	Assert.assertEquals(reservation.getNumPeriods(), returned.getNumPeriods());
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
