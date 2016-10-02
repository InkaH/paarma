package ont.paarma.test.controller;

import ont.paarma.config.AppConfig;
import ont.paarma.controller.UserFormController;
import ont.paarma.model.User;
import ont.paarma.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, AppConfig.class})
@WebAppConfiguration
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
        
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void testInitForm() throws Exception{

		this.mockMvc.perform(get("/newUser"))
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
		
		this.mockMvc.perform(post("/newUser")
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
}
