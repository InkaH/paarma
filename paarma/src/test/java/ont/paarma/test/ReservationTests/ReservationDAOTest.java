package ont.paarma.test.ReservationTests;

import java.util.ArrayList;
import java.util.List;

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
    	Reservation reservation = ReservationTestUtil.createReservationNoId();
    	Reservation returned = reservationDao.addReservation(reservation);
    	Assert.assertNotNull(returned);
    	Assert.assertEquals(4, returned.getId());
    	Assert.assertEquals(reservation.getStartDate(), returned.getStartDate());
    	Assert.assertEquals(reservation.getTable(), returned.getTable());
    	Assert.assertEquals(reservation.getNumPeriods(), returned.getNumPeriods());
    	Assert.assertEquals(reservation.getEndDate(), returned.getEndDate());
    }
    
    @Test
    public void testFindById() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	ReservationDAO reservationDao = new ReservationDAO();
    	reservationDao.setNamedParameterJdbcTemplate(template);
    	//first add reservation to db
    	Reservation reservation = ReservationTestUtil.createReservationNoId();
    	Reservation returned = reservationDao.addReservation(reservation);
    	Reservation returnedFind = reservationDao.findById(returned.getId());
    	Assert.assertNotNull(returnedFind);
    	Assert.assertEquals(returned.getId(), returnedFind.getId());
    }
    
    @Test
    public void testFindAll() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	ReservationDAO reservationDao = new ReservationDAO();
    	reservationDao.setNamedParameterJdbcTemplate(template);
    	List<Reservation> reservations = new ArrayList<Reservation>();
    	//we know userId 1 has 2 reservations in our test db
    	reservations = reservationDao.findAll(1);
    	Assert.assertNotNull(reservations);
    	Assert.assertTrue((reservations.size() == 2));
    	Assert.assertEquals(reservations.get(0).getId(), 1);
    	Assert.assertEquals(reservations.get(1).getId(), 2);
    }
    
    @Test
    public void testFindAllWithNoReservationsInDB_returnsEmptyList() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	ReservationDAO reservationDao = new ReservationDAO();
    	reservationDao.setNamedParameterJdbcTemplate(template);
    	List<Reservation> reservations = new ArrayList<Reservation>();
    	//we know userId 3 has no reservations in our test db
    	reservations = reservationDao.findAll(3);
    	Assert.assertNotNull(reservations);
    	Assert.assertTrue((reservations.size() == 0));
    }
    
    @Test
    public void testUpdateReservation(){
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	ReservationDAO reservationDao = new ReservationDAO();
    	reservationDao.setNamedParameterJdbcTemplate(template);
    	Reservation reservation = ReservationTestUtil.createReservationNoId();
    	Reservation returned = reservationDao.updateReservation(reservation);
    	Assert.assertNotNull(returned);
    	Assert.assertEquals(reservation.getId(), returned.getId());
    	Assert.assertEquals(reservation.getStartDate(), returned.getStartDate());
    	Assert.assertEquals(reservation.getTable(), returned.getTable());
    	Assert.assertEquals(reservation.getNumPeriods(), returned.getNumPeriods());
    	Assert.assertEquals(reservation.getEndDate(), returned.getEndDate());
    }
    
    @Test
    public void testDeleteReservation(){
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	ReservationDAO reservationDao = new ReservationDAO();
    	reservationDao.setNamedParameterJdbcTemplate(template);
    	reservationDao.deleteReservation(3);
    	Reservation returnedFind = reservationDao.findById(3);
    	Assert.assertNull(returnedFind);
    }
}
