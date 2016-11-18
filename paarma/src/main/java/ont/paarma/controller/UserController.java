package ont.paarma.controller;

import ont.paarma.model.User;
import ont.paarma.service.UserService;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String setUpForm(Model model) { 
		User user = new User();
		model.addAttribute("user", user);
		return "newUser";
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("user") User addedUser, 
			BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()) { 
			return "newUser";
		}

		User addedToDbUser = service.add(addedUser);

			System.out.println(addedToDbUser.getId());

		attributes.addAttribute("id", addedToDbUser.getId())
		.addFlashAttribute("successMsg", "Käyttäjätili luotu.");
		return createRedirectViewPath("/user/{id}");
	}	

	private String createRedirectViewPath(String requestMapping) {
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(requestMapping);
		return redirectViewPath.toString();
	}
}
