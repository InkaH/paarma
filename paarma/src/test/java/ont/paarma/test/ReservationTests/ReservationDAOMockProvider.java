package ont.paarma.test.ReservationTests;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ont.paarma.dao.ReservationDAO;
import ont.paarma.service.ReservationService;

@Configuration
class ReservationDAOMockProvider {
	
	@Bean
	public ReservationService reservationService(){
		return new ReservationService();
	}
	
	@Bean
	public ReservationDAO reservationDAO(){
		return Mockito.mock(ReservationDAO.class);
	}
}
