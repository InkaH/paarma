package ont.paarma.test.controller;

import ont.paarma.config.AppConfig;
import ont.paarma.controller.UserFormController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class UserFormControllerTest{

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	UserFormController controller;

	@Before
	public void setUp() {

		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void testCreateUser() throws Exception{

		this.mockMvc.perform(get("/newUser"))
		.andExpect(status().isOk())
		.andExpect(view().name("newUser"))
		.andExpect(forwardedUrl("/WEB-INF/views/newUser.jsp"))
		.andExpect(model().attribute(
			"user", hasProperty("id", is(0)))) //Hamcrest
		.andExpect(model().attribute(
			"user", hasProperty("firstName", nullValue()))) 
		.andExpect(model().attribute(
			"user", hasProperty("lastName", nullValue()))) 
		.andExpect(model().attribute(
			"user", hasProperty("streetAddress", nullValue()))) 
		.andExpect(model().attribute(
			"user", hasProperty("zipCode", nullValue()))) 
		.andExpect(model().attribute(
			"user", hasProperty("city", nullValue()))) 
		.andExpect(model().attribute(
			"user", hasProperty("phoneNumber", nullValue()))); 
	}
}
