package ont.paarma.test.ReservationTests;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ont.paarma.dao.ReservationDAO;
import ont.paarma.dao.UserDAO;
import ont.paarma.service.ReservationService;
import ont.paarma.service.UserService;

@Configuration
class ReservationServiceMockProvider {
	
	@Bean
	public UserService userService () {
		return  Mockito.mock(UserService.class);
	}
	
	@Bean
	public UserDAO userDAO(){
		return Mockito.mock(UserDAO.class);
	}
	
	@Bean 
	public ReservationService reservationService(){
		return Mockito.mock(ReservationService.class);
	}
	
	@Bean
	public ReservationDAO reservationDAO(){
		return Mockito.mock(ReservationDAO.class);
	}
}

