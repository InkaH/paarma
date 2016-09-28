package ont.paarma.test.controller;

//https://richardbarabe.wordpress.com/2008/08/14/test-driving-a-simpleformcontroller-with-spring-mvc/
import static org.junit.Assert.assertEquals;
import ont.paarma.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserControllerTest{
	@Autowired
	UserFormController controller;
	
	@Test
	public void testCreateUser(){
		
		ModelMap modelMap = new ModelMap();
		String model = controller.initializeForm(modelMap);
		assertEquals(model, "newUser");
	}
}
