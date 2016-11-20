package ont.paarma.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ont.paarma.dao.ReservationDAO;
import ont.paarma.model.Reservation;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationDAO reservationDAO;

	public Reservation add(Reservation reservation) {
		return reservationDAO.addReservation(reservation);
	}

	public Reservation findById(int id){
		return reservationDAO.findById(id);
	}
	
	public Reservation edit(Reservation reservation) {
		return reservationDAO.updateReservation(reservation);
	}
	
	public List<Reservation> findAll(int id){
		List<Reservation> reservationList= new ArrayList<Reservation>();
		reservationList = reservationDAO.findAll(id);
		return reservationList;
	}
	
	public void delete(int id){
		reservationDAO.deleteReservation(id);
	}
}
