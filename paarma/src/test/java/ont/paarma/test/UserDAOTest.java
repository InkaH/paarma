//package ont.paarma.test;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import ont.paarma.config.AppConfig;
//import ont.paarma.dao.UserDAO;
//import ont.paarma.model.User;
//import ont.paarma.service.UserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestContext.class, AppConfig.class})
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//    TransactionalTestExecutionListener.class,
//    DirtiesContextTestExecutionListener.class})
//@WebAppConfiguration
//public class UserDAOTest {
//	
//	@Autowired
//	private UserDAO userDAOMock;
//
//	@Before
//	public void setUp() {
//		//reset mock between tests
//		Mockito.reset(userDAOMock);
//	}
//
//	  @Test
//	  public void testUserDAOAddUserMethod() {
//	    UserService userService = new UserService(userDAOMock);
//	    userService.add(user);
//	    Mockito.verify(userDAOMock).addUser(user);
//	}
//
//	  @Test
//	  public void testUserServiceAddUserMethod_userDaoAddUserReturnsUserObject() {
//		UserService userService = new UserService(userDAOMock);
////		User user = createTestUser();
////	    Mockito.when(userDAOMock.addUser(user)).thenReturn();
////	    User actual = myService.findById(1L);
////	    Assert.assertEquals("My first name", actual.getFirstName());
////	    Assert.assertEquals("My surname", actual.getLastName());
//	}
//}
