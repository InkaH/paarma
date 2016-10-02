package ont.paarma.controller;

import ont.paarma.model.User;
import ont.paarma.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserFormController{
	
	private final UserService service;
	
	@Autowired
	public UserFormController(UserService service){
		this.service = service;
	}
	
	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String setUpForm(Model model) { 
		User user = new User();
		model.addAttribute("user", user);
		return "newUser";
	}
	
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("user") User user, 
			BindingResult result, RedirectAttributes attributes){
			if(result.hasErrors()) { 
				return "newUser";
			}
			
			User addedUser = service.add(user);
			attributes.addAttribute("id", addedUser.getId());
			return ("main");
	}	
}
