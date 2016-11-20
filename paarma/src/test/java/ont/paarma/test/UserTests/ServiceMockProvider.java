package ont.paarma.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ont.paarma.dao.UserDAO;
import ont.paarma.service.UserService;

@Configuration
class ServiceMockProvider {
	
	@Bean
	public UserService userService () {
		return  Mockito.mock(UserService.class);
	}
	
	@Bean
	public UserDAO userDAO(){
		return Mockito.mock(UserDAO.class);
	}
}

