package ont.paarma.test.UserTests;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

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
import org.springframework.web.bind.annotation.SessionAttributes;
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
		.andExpect(request().sessionAttribute("user", hasProperty("id", is(0))))
		.andExpect(request().sessionAttribute("user", hasProperty("firstName", nullValue())))
		.andExpect(request().sessionAttribute("user", hasProperty("lastName", nullValue())));  
	}
	
	@Test
	public void testCreateUserWithInvalidFields() throws Exception{
		String firstName = TestUtil.createString(51);
		String lastName = TestUtil.createString(1);
		User invalidUser = new User(0, firstName, lastName);

		mockMvc.perform(post("/newUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("user", invalidUser)
				)
		.andExpect(status().isOk())
		.andExpect(view().name("newUser"))
		.andExpect(forwardedUrl("/WEB-INF/views/newUser.jsp"))
		.andExpect(model().attributeHasFieldErrors("user", "firstName"))
		.andExpect(model().attributeHasFieldErrors("user", "lastName"))
		.andExpect(request().sessionAttribute("user", hasProperty("id", is(invalidUser.getId())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("firstName", is(invalidUser.getFirstName())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("lastName", is(invalidUser.getLastName()))));
		Mockito.verifyZeroInteractions(userServiceMock);
	}

	@Test
	public void testCreateUserWithValidFields() throws Exception{
		User addedUser = TestUtil.createTestUserNoId();
		User returnedUser = TestUtil.createTestUserWithId();
		Mockito.when(userServiceMock.add(isA(User.class))).thenReturn(returnedUser);

		mockMvc.perform(post("/newUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("user", addedUser)
				)
		.andExpect(view().name("dashboard"))
		.andExpect(forwardedUrl("/WEB-INF/views/dashboard.jsp"))
		.andExpect(model().attribute("msg", is("Käyttäjätili luotu.")))
		.andExpect(request().sessionAttribute("user", hasProperty("id", is(addedUser.getId())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("firstName", is(addedUser.getFirstName())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("lastName", is(addedUser.getLastName()))));

        ArgumentCaptor<User> formObjectArgument = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userServiceMock, times(1)).add(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(userServiceMock);
 
        User formObject = formObjectArgument.getValue();
        assertThat(formObject.getId(), is(returnedUser.getId()));
        assertThat(formObject.getFirstName(), is(returnedUser.getFirstName()));
        assertThat(formObject.getLastName(), is(returnedUser.getLastName()));
	}	
	
	@Test
	public void testInitEditForm() throws Exception{
		User editUser = TestUtil.createTestUserWithId();
		
		mockMvc.perform(get("/editUser")
		.sessionAttr("user", editUser))
		.andExpect(status().isOk())
		.andExpect(view().name("editUser"))
		.andExpect(forwardedUrl("/WEB-INF/views/editUser.jsp"))
		.andExpect(request().sessionAttribute("user", hasProperty("id", is(editUser.getId()))))
		.andExpect(request().sessionAttribute("user", hasProperty("firstName", is(editUser.getFirstName())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("lastName", is(editUser.getLastName())))); 
	}
	
	@Test
	public void testEditUserWithInvalidFields() throws Exception{
		String firstName = TestUtil.createString(51);
		String lastName = TestUtil.createString(1);
		User invalidUser = new User(1, firstName, lastName);

		mockMvc.perform(post("/editUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("user", invalidUser)
				)
		.andExpect(status().isOk())
		.andExpect(view().name("editUser"))
		.andExpect(forwardedUrl("/WEB-INF/views/editUser.jsp"))
		.andExpect(model().attributeHasFieldErrors("user", "firstName"))
		.andExpect(model().attributeHasFieldErrors("user", "lastName"))
		.andExpect(request().sessionAttribute("user", hasProperty("id", is(invalidUser.getId())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("firstName", is(invalidUser.getFirstName())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("lastName", is(invalidUser.getLastName()))));
		Mockito.verifyZeroInteractions(userServiceMock);
	}
	
	@Test
	public void testEditUserWithValidFields() throws Exception{
		User editUser = TestUtil.createTestUserWithId();
		Mockito.when(userServiceMock.add(isA(User.class))).thenReturn(editUser);

		mockMvc.perform(post("/editUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("user", editUser)
				)
		.andExpect(forwardedUrl("/WEB-INF/views/dashboard.jsp"))
		.andExpect(model().attribute("msg", is("Tili päivitetty.")))
		.andExpect(request().sessionAttribute("user", hasProperty("id", is(editUser.getId())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("firstName", is(editUser.getFirstName())))) 
		.andExpect(request().sessionAttribute("user", hasProperty("lastName", is(editUser.getLastName()))));
		
        ArgumentCaptor<User> formObjectArgument = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userServiceMock, times(1)).add(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(userServiceMock);
 
        User formObject = formObjectArgument.getValue();
        assertThat(formObject.getId(), is(1));
        assertThat(formObject.getFirstName(), is("firstName"));
        assertThat(formObject.getLastName(), is("lastName"));
	}	
	
	

	 
	    
}
