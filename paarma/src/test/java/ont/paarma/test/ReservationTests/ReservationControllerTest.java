package ont.paarma.test.ReservationTests;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ont.paarma.config.AppConfig;
import ont.paarma.controller.ReservationController;
import ont.paarma.model.Reservation;
import ont.paarma.model.User;
import ont.paarma.service.ReservationService;
import ont.paarma.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		AppConfig.class,
        ReservationServiceMockProvider.class
})
@WebAppConfiguration
public class ReservationControllerTest{

	private MockMvc mockMvc;
		
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
    private ReservationService reservationServiceMock;
	
	@Before
	public void setUp() {
		
		mockMvc = MockMvcBuilders
				.webAppContextSetup(this.webApplicationContext).build();
	}
	
	@After
	public void tearDown(){
		//reset mock between tests
		Mockito.reset(reservationServiceMock);
	}

	@Test
	public void testInitForm() throws Exception{

		mockMvc.perform(get("/newReservation"))
		.andExpect(status().isOk())
		.andExpect(view().name("newReservation"))
		.andExpect(forwardedUrl("/WEB-INF/views/newReservation.jsp"))
		.andExpect(request().sessionAttribute("reservation", hasProperty("id", is(0))))
		.andExpect(request().sessionAttribute("reservation", hasProperty("startDate", nullValue())))
		.andExpect(request().sessionAttribute("reservation", hasProperty("table", nullValue())))
		.andExpect(request().sessionAttribute("reservation", hasProperty("numPeriods", is(0))));  
	}
	
	@Test
	public void testCreateReservationWithInvalidFields() throws Exception{
		
		Reservation invalidReservation = ReservationTestUtil.createReservationNoId();
		User user = new User();
		user.setId(4);
		invalidReservation.setNumPeriods(0);

		mockMvc.perform(post("/newReservation")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("reservation", invalidReservation)
				.sessionAttr("user", user)
				)
		.andExpect(status().isOk())
		.andExpect(view().name("newReservation"))
		.andExpect(forwardedUrl("/WEB-INF/views/newReservation.jsp"))
		.andExpect(model().attributeHasFieldErrors("reservation", "numPeriods"));
		Mockito.verifyZeroInteractions(reservationServiceMock);
	}

	@Test
	public void testCreateReservationWithValidFields() throws Exception{
		Reservation reservation = ReservationTestUtil.createReservationNoId();
		Reservation resWithId = ReservationTestUtil.createReservationNoId();
		resWithId.setId(4);
		User user = new User();
		user.setId(4);
		Mockito.when(reservationServiceMock.add(isA(Reservation.class))).thenReturn(resWithId);

		mockMvc.perform(post("/newReservation")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("reservation", reservation)
				.sessionAttr("user", user)
				)
		.andExpect(view().name("reservationView"))
		.andExpect(forwardedUrl("/WEB-INF/views/reservationView.jsp"))
		.andExpect(model().attribute("msg", is("Varaus tehty.")))
		.andExpect(request().sessionAttribute("reservation", hasProperty("id", is(reservation.getId())))) 
		.andExpect(request().sessionAttribute("reservation", hasProperty("table", is(reservation.getTable())))) 
		.andExpect(request().sessionAttribute("reservation", hasProperty("numPeriods", is(reservation.getNumPeriods()))))
		.andExpect(request().sessionAttribute("reservation", hasProperty("startDate", is(reservation.getStartDate()))));

        ArgumentCaptor<Reservation> formObjectArgument = ArgumentCaptor.forClass(Reservation.class);
        Mockito.verify(reservationServiceMock, times(1)).add(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(reservationServiceMock);
 
        Reservation formObject = formObjectArgument.getValue();
        assertThat(formObject.getId(), is(0));
        assertThat(formObject.getTable(), is(resWithId.getTable()));
        assertThat(formObject.getNumPeriods(), is(resWithId.getNumPeriods()));
        assertThat(formObject.getStartDate(), is(resWithId.getStartDate()));
	}	
	
	@Test
	public void testInitEditForm() throws Exception{
		Reservation resWithId = ReservationTestUtil.createReservationNoId();
		resWithId.setId(4);
		
		mockMvc.perform(get("/editReservation")
		.sessionAttr("reservation", resWithId))
		.andExpect(status().isOk())
		.andExpect(view().name("editReservation"))
		.andExpect(forwardedUrl("/WEB-INF/views/editReservation.jsp"))
		.andExpect(request().sessionAttribute("reservation", hasProperty("id", is(resWithId.getId()))))
		.andExpect(request().sessionAttribute("reservation", hasProperty("table", is(resWithId.getTable())))) 
		.andExpect(request().sessionAttribute("reservation", hasProperty("numPeriods", is(resWithId.getNumPeriods())))); 
	}
	
	@Test
	public void testEditUserWithValidFields() throws Exception{
		Reservation resWithId = ReservationTestUtil.createReservationNoId();
		resWithId.setId(4);
		Mockito.when(reservationServiceMock.add(isA(Reservation.class))).thenReturn(resWithId);

		mockMvc.perform(post("/editReservation")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("reservation", resWithId)
				)
		.andExpect(forwardedUrl("/WEB-INF/views/reservationView.jsp"))
		.andExpect(model().attribute("msg", is("Varaus päivitetty.")));

        ArgumentCaptor<Reservation> formObjectArgument = ArgumentCaptor.forClass(Reservation.class);
        Mockito.verify(reservationServiceMock, times(1)).edit(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(reservationServiceMock);
 
        Reservation formObject = formObjectArgument.getValue();
        assertThat(formObject.getId(), is(resWithId.getId()));
        assertThat(formObject.getTable(), is(resWithId.getTable()));
        assertThat(formObject.getNumPeriods(), is(resWithId.getNumPeriods()));
        assertEquals(formObject.getStartDate(), resWithId.getStartDate());
	}	
	
	@Test
	public void testFindAll() throws Exception{
		User user = new User();
		user.setId(1);
		mockMvc.perform(get("allReservations")
		.sessionAttr("user", user))
		.andExpect(status().isOk())
		.andExpect(view().name("allReservations"))
		.andExpect(forwardedUrl("/WEB-INF/views/allReservations.jsp"))
		.andExpect(request().sessionAttribute("reservations", hasProperty("id", is(1))));
	}    
}
