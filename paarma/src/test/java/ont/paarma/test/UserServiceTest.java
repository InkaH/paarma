package ont.paarma.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import ont.paarma.config.AppConfig;
import ont.paarma.config.SpringRootConfig;
import ont.paarma.dao.UserDAO;
import ont.paarma.model.User;
import ont.paarma.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DAOMockProvider.class
})
@WebAppConfiguration
public class UserServiceTest {
	
	@Autowired 
	private UserDAO userDAOMock;
	
	@Autowired
	private UserService userService;
		
	@After
	public void tearDown(){
		Mockito.reset(userDAOMock);	
	}

	@Test
	public void testUserServiceAddMethod_callsUserDaoAddUserOnce() {		
		User user = TestUtil.createTestUserNoId();
		userService.add(user);
		Mockito.verify(userDAOMock).addUser(user);
	}

	@Test
	public void testUserServiceAddMethod_userDaoAddUserReturnsUserObject() {
		User userNoId = TestUtil.createTestUserNoId();
		User userWithId = TestUtil.createTestUserWithId();
		Mockito.when(userDAOMock.addUser(userNoId)).thenReturn(userWithId);
		User actual = userService.add(userNoId);
		Assert.assertEquals(userWithId, actual);    
	}
}
