package ont.paarma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserFormController{
	@RequestMapping(value = "/newUser.jsp", method = RequestMethod.GET)
	public ModelAndView initializeForm() { 
		ModelAndView model = new ModelAndView("newUser");
		return model;
	}
}
