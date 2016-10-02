package ont.paarma.test.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ont.paarma.service.UserService;

@Configuration
public class TestContext {
	
	@Bean
    public UserService UserService() {
        return Mockito.mock(UserService.class);
    }
}
