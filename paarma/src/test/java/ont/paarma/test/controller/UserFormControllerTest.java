package ont.paarma.test.controller;

import ont.paarma.config.AppConfig;
import ont.paarma.model.User;
import ont.paarma.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.isA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, AppConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@WebAppConfiguration
@DatabaseSetup("UserData.xml")
public class UserFormControllerTest{

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserService userServiceMock;

	@Before
	public void setUp() {

		//reset mock between tests
		Mockito.reset(userServiceMock);

		mockMvc = MockMvcBuilders
				.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void testInitForm() throws Exception{

		mockMvc.perform(get("/newUser"))
		.andExpect(status().isOk())
		.andExpect(view().name("newUser"))
		.andExpect(forwardedUrl("/WEB-INF/views/newUser.jsp"))
		.andExpect(model().attribute(
				"user", hasProperty("id", is(0)))) 
		.andExpect(model().attribute(
				"user", hasProperty("firstName", nullValue()))) 
		.andExpect(model().attribute(
				"user", hasProperty("lastName", nullValue()))); 
	}

	@Test
	public void testCreateUserWithInvalidFields() throws Exception{
		String firstName = TestUtil.createString(51);
		String lastName = TestUtil.createString(1);

		mockMvc.perform(post("/newUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", firstName)
				.param("lastName", lastName)
				.sessionAttr("user", new User())
				)
		.andExpect(status().isOk())
		.andExpect(view().name("newUser"))
		.andExpect(forwardedUrl("/WEB-INF/views/newUser.jsp"))
		.andExpect(model().attributeHasFieldErrors("user", "firstName"))
		.andExpect(model().attributeHasFieldErrors("user", "lastName"))
		.andExpect(model().attribute("user", hasProperty("id", is(0))))
		.andExpect(model().attribute("user", hasProperty("firstName", is(firstName))))
		.andExpect(model().attribute("user", hasProperty("lastName", is(lastName))));
		Mockito.verifyZeroInteractions(userServiceMock);
	}

	@Test
	public void testCreateUserWithValidFields() throws Exception{
		int id = 1;
		String firstName = "firstName";
		String lastName = "lastName";
		User addedUser = new User(id, "firstName", "lastName");

		when(userServiceMock.add(isA(User.class))).thenReturn(addedUser);

		mockMvc.perform(post("/newUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", firstName)
				.param("lastName", lastName)
				.sessionAttr("user", new User())
				)
		.andExpect(status().isMovedTemporarily())
		.andExpect(view().name("redirect:/user/{id}"))
		.andExpect(redirectedUrl("/user/1"))
		.andExpect(flash().attribute("successMsg", is("Käyttäjätili luotu.")))
		.andExpect(model().attribute("id", is("1")));	


		ArgumentCaptor<User> formObjectArgument = ArgumentCaptor.forClass(User.class);
		verify(userServiceMock, times(1)).add(formObjectArgument.capture());
		Mockito.verifyNoMoreInteractions(userServiceMock);

		User formObject = formObjectArgument.getValue();

		assertThat(formObject.getId(), is(0));
		assertThat(formObject.getFirstName(), is("firstName"));
		assertThat(formObject.getLastName(), is("lastName"));
	}	
	@Test
	@ExpectedDatabase(value="UserData-addUser.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateUserWithValidFieldsWithDBMock() throws Exception{
		String firstName = "c";
		String lastName = "c";
		
		mockMvc.perform(post("/newUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", firstName)
				.param("lastName", lastName)
				.sessionAttr("user", new User())
				)
		.andExpect(status().isMovedTemporarily())
		.andExpect(view().name("redirect:/user/{id}"))
		.andExpect(redirectedUrl("/user/1"))
		.andExpect(flash().attribute("successMsg", is("Käyttäjätili luotu.")))
		.andExpect(model().attribute("id", is("3")));
	}
}
