package ont.paarma.test.controller;

public class TestUtil {
	
	public static String createString(int length) {
        StringBuilder builder = new StringBuilder();
 
        for (int index = 0; index < length; index++) {
            builder.append("x");
        }
 
        return builder.toString();
    }

}
