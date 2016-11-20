package ont.paarma.test.UserTests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
	public void testAddMethod_callsUserDaoAddUserOnce() {		
		User user = TestUtil.createTestUserNoId();
		userService.add(user);
		Mockito.verify(userDAOMock).addUser(user);
	}

	@Test
	public void testAddMethod_userDaoAddUserReturnsUserObject() {
		User userNoId = TestUtil.createTestUserNoId();
		User userWithId = TestUtil.createTestUserWithId();
		Mockito.when(userDAOMock.addUser(userNoId)).thenReturn(userWithId);
		User actual = userService.add(userNoId);
		Assert.assertEquals(userWithId, actual);    
	}
	
	@Test
	public void testAddMethodWithId_callsUserDaoUpdateUserOnce() {		
		User user = TestUtil.createTestUserWithId();
		userService.add(user);
		Mockito.verify(userDAOMock).updateUser(user);
	}

	@Test
	public void testAddMethodWithId_userDaoUpdateUserReturnsUserObject() {
		User userWithId = TestUtil.createTestUserWithId();
		Mockito.when(userDAOMock.updateUser(userWithId)).thenReturn(userWithId);
		User actual = userService.add(userWithId);
		Assert.assertEquals(userWithId, actual);    
	}
}
