package ont.paarma.test;

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
}
