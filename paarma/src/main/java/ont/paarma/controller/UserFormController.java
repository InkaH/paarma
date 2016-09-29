package ont.paarma.controller;

import ont.paarma.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserFormController{
	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String setUpForm(Model model) { 
		User user = new User();
		model.addAttribute("user", user);
		return "newUser";
	}
}
