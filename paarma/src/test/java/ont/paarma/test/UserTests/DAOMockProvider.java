package ont.paarma.test.UserTests;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ont.paarma.dao.UserDAO;
import ont.paarma.service.UserService;

@Configuration
class DAOMockProvider {
	
	@Bean
	public UserService userService(){
		return new UserService();
	}
	
	@Bean
	public UserDAO userDAO(){
		return Mockito.mock(UserDAO.class);
	}
}

