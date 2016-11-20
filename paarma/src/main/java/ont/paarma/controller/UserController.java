package ont.paarma.controller;

import ont.paarma.model.User;
import ont.paarma.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("user")
public class UserController{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@ModelAttribute("user")
	public User getUser () {
		return new User();
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String setUpForm(@ModelAttribute("user") User user) { 
		return "newUser";
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("user") User user, 
			BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) { 
			model.addAttribute("msg", "Virhe.");
			return "newUser";
		}

		User addedToDbUser = service.add(user);
		user.setId(addedToDbUser.getId());
		model.addAttribute("msg", "Käyttäjätili luotu.");
		return "dashboard";
	}	

	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public String editUser(HttpServletRequest reques, @ModelAttribute("user") User user) { 
		return "editUser";
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute("user") User user, 
			BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", "Virhe.");
			return "editUser";
		}
//		service.add(user);
		model.addAttribute("msg", "Tili päivitetty.");
		return "dashboard";
	}	
}
