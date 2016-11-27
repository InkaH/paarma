package ont.paarma.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ont.paarma.model.Reservation;
import ont.paarma.model.User;
import ont.paarma.service.ReservationService;

@Controller
@SessionAttributes({"user", "reservation"})
public class ReservationController{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private ReservationService reservationService;

//	@ModelAttribute("user")
//	public User getUser () {
//		return new User();
//	}
	
	@ModelAttribute("reservation")
	public Reservation getReservation () {
		return new Reservation();
	}

	 @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        sdf.setLenient(true);
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	    }
	 
	@RequestMapping(value = "/newReservation", method = RequestMethod.GET)
	public ModelAndView setUpForm(@ModelAttribute("reservation") Reservation reservation) { 
		//clear sessionAttribute between new reservations
//		HttpSession session = request.getSession();
//        session.setAttribute("user", new User());
		return new ModelAndView("newReservation", "reservation", new Reservation());
	}

	@RequestMapping(value = "/newReservation", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("reservation") Reservation reservation, 
			BindingResult bindingResult, Model model, @ModelAttribute("user") User user){
		if(bindingResult.hasErrors()) { 
			model.addAttribute("msg", "Virhe.");
			return "newReservation";
		}

		reservation.setUserId(user.getId());
		reservation = reservationService.add(reservation);
		model.addAttribute("msg", "Varaus tehty.");
		return "reservationView";
	}	

	@RequestMapping(value = "/editReservation", method = RequestMethod.GET)
	public String editReservation(HttpServletRequest reques, @ModelAttribute("reservation") Reservation reservation) { 
		return "editReservation";
	}

	@RequestMapping(value = "/editReservation", method = RequestMethod.POST)
	public String editReservation(@Valid @ModelAttribute("reservation") Reservation reservation, 
			BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", "Virhe.");
			return "editReservation";
		}
		reservation = reservationService.edit(reservation);
		model.addAttribute("msg", "Varaus päivitetty.");
		return "reservationView";
	}	
	
	@RequestMapping(value = "/allReservations", method = RequestMethod.GET)
	public ModelAndView findAllReservations(@ModelAttribute("reservation") Reservation reservation,
			@ModelAttribute("user") User user){
		List<Reservation> allReservations = reservationService.findAll(user.getId());
		System.out.println(Arrays.toString(allReservations.toArray()));
		return new ModelAndView("/allReservations", "reservations", allReservations);
	}	
	
	@RequestMapping(value = "/deleteReservation", method = RequestMethod.GET)
	public ModelAndView deleteReservation(@ModelAttribute("reservation") Reservation reservation,
			@ModelAttribute("user") User user, RedirectAttributes redir){
		reservationService.delete(user.getId());
		redir.addFlashAttribute("msg", "Varaus poistettu.");
		return new ModelAndView("redirect:/allReservations");
	}	
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String viewDashboard(@ModelAttribute("reservation") Reservation reservation,
			@ModelAttribute("user") User user){
		return "dashboard";
	}	
}
