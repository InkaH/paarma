package ont.paarma.test.ReservationTests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ont.paarma.dao.ReservationDAO;
import ont.paarma.model.Reservation;
import ont.paarma.service.ReservationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ReservationDAOMockProvider.class
})
@WebAppConfiguration
public class ReservationServiceTest {
	
	@Autowired 
	private ReservationDAO reservationDAOMock;
	
	@Autowired
	private ReservationService reservationService;
		
	@After
	public void tearDown(){
		Mockito.reset(reservationDAOMock);	
	}

	@Test
	public void testAddMethod_callsReservationDaoAddReservationOnce() {		
		Reservation reservation = ReservationTestUtil.createReservationNoId();
		reservationService.add(reservation);
		Mockito.verify(reservationDAOMock).addReservation(reservation);
	}

	@Test
	public void testAddMethod_userDaoAddUserReturnsUserObject() {
		Reservation resNoId = ReservationTestUtil.createReservationNoId();
		Reservation resWithId = ReservationTestUtil.createReservationNoId();
		resWithId.setId(3);
		Mockito.when(reservationDAOMock.addReservation(resNoId)).thenReturn(resWithId);
		Reservation actual = reservationService.add(resNoId);
		Assert.assertEquals(resWithId, actual);    
	}
	
	@Test
	public void testEditMethodWithId_userDaoUpdateUserReturnsUserObject() {
		Reservation resWithId = ReservationTestUtil.createReservationNoId();
		resWithId.setId(4);
		Mockito.when(reservationDAOMock.updateReservation(resWithId)).thenReturn(resWithId);
		Reservation actual = reservationService.edit(resWithId);
		Assert.assertEquals(resWithId, actual);    
	}
}
