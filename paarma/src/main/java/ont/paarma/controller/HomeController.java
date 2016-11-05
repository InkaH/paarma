package ont.paarma.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ont.paarma.model.User;
import ont.paarma.service.UserService;

/**
* Controller class.
* 
* @author Inka
*/

//@Controller annotation on class name declares this class as spring bean
@Controller
//@RequestMapping annotation declares that this class is default handler for all requests of type �/�
@RequestMapping("/")
public class HomeController {
	
//	private UserService service;
//	
//	@Autowired
//	public HomeController(UserService service){
//		this.service = service;
//	}

	//First method does not have any mapping declared, it will inherit 
	//the mapping from mapping declared on class level, acting as a default handler for GET requests
	@RequestMapping(method = RequestMethod.GET)
	// ModelMap is a Map implementation, which here is acting as replacement of 
	//[request.getAttribute()/request.setAttribute()], setting values as request attribute. 
	public String sayHello(ModelMap model) {
		model.addAttribute("greeting", "test");
		//This value will be suffixed and prefixed with suffix and prefix defined in view resolver to form the real view file name.
		return "index";
	}
	
//	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
//	public String setUpForm(Model model) { 
//		User user = new User();
//		model.addAttribute("user", user);
//		return "newUser";
//	}
//
//	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
//	public String add(@Valid @ModelAttribute("user") User addedUser, 
//			BindingResult result, RedirectAttributes attributes){
//		if(result.hasErrors()) { 
//			return "newUser";
//		}
//
//		User addedToDbUser = service.add(addedUser);
//		attributes.addAttribute("id", addedToDbUser.getId())
//		.addFlashAttribute("successMsg", "Käyttäjätili luotu.");
//		return createRedirectViewPath("/user/{id}");
//	}	
//
//	private String createRedirectViewPath(String requestMapping) {
//		StringBuilder redirectViewPath = new StringBuilder();
//		redirectViewPath.append("redirect:");
//		redirectViewPath.append(requestMapping);
//		return redirectViewPath.toString();
//	}
}