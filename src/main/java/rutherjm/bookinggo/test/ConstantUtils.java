package rutherjm.bookinggo.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantUtils {
    public static String API_HOSTNAME = "https://techtest.rideways.com/";
    public static Map<String, Integer> TAXI_CAPACITY = new HashMap<>();
    public static List<String> TAXI_FIRM_IDS = Arrays.asList("dave", "eric", "jeff");
    public static List<String> TAXI_TYPES = Arrays.asList("STANDARD", "EXECUTIVE", "LUXURY", "PEOPLE_CARRIER", "LUXURY_PEOPLE_CARRIER", "MINIBUS");

    public static int MAX_INT_VALUE = 2147483647;

}
