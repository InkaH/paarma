package ont.paarma.test.UserTests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import ont.paarma.model.Reservation;
import ont.paarma.model.User;

public class TestUtil {
	
	public static String createString(int length) {
        StringBuilder builder = new StringBuilder();
 
        for (int index = 0; index < length; index++) {
            builder.append("x");
        }
 
        return builder.toString();
    }
	
	public static User createTestUserNoId(){
		return new User("firstName", "lastName");
	}

	public static User createTestUserWithId(){
		return new User(1, "firstName", "lastName");
	}
	
	public static User createDBUser(){
		return new User("testFirst", "testLast");
	}
	
	public static User createDBUpdateUser(){
		return new User(4, "testFirst2", "testLast2");
	}

	public static Reservation createReservationNoId() {
		Calendar cal = new GregorianCalendar(2016, 11, 25);
		Date date = cal.getTime();
		return new Reservation(4, date, 2, "21");
	}
}
