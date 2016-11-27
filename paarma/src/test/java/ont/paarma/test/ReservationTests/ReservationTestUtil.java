package ont.paarma.test.ReservationTests;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ont.paarma.model.Reservation;

public class ReservationTestUtil {

	public static Reservation createReservationNoId() {
		Calendar cal = new GregorianCalendar(2016, 11, 10);
		Date start = cal.getTime();
		return new Reservation(3, start, 2, "21");
	}
}
