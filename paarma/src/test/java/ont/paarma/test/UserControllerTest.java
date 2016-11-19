package ont.paarma.test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import ont.paarma.model.User;
import ont.paarma.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		AppConfig.class,
        ServiceMockProvider.class
})
@WebAppConfiguration
public class UserControllerTest{

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
    private UserService userServiceMock;
	
	@Before
	public void setUp() {
		
		mockMvc = MockMvcBuilders
				.webAppContextSetup(this.webApplicationContext).build();
	}
	
	@After
	public void tearDown(){
		//reset mock between tests
		Mockito.reset(userServiceMock);
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

	@SuppressWarnings("deprecation")
	@Test
	public void testCreateUserWithValidFields() throws Exception{
		User addedUser = new User("etunimi", "sukunimi");
		User returnedUser = new User(1, "etunimi", "sukunimi");
		Mockito.when(userServiceMock.add(isA(User.class))).thenReturn(returnedUser);

		mockMvc.perform(post("/newUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", addedUser.getFirstName())
				.param("lastName", addedUser.getLastName())
				.sessionAttr("user", new User())
				)
		.andExpect(status().isMovedTemporarily())
		.andExpect(view().name("redirect:/user/{id}"))
		.andExpect(redirectedUrl("/user/1"))
		.andExpect(flash().attribute("successMsg", is("Käyttäjätili luotu.")))
		.andExpect(model().attribute("id", is("1")));

        ArgumentCaptor<User> formObjectArgument = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userServiceMock, times(1)).add(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(userServiceMock);
 
        User formObject = formObjectArgument.getValue();
        assertThat(formObject.getId(), is(0));
        assertThat(formObject.getFirstName(), is("etunimi"));
        assertThat(formObject.getLastName(), is("sukunimi"));
	}	
	

	 
	    
}
