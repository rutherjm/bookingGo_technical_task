package rutherjm.bookinggo.test;

import java.util.HashMap;
import java.util.Map;

public class ConstantUtils {
    public static String API_HOSTNAME = "https://techtest.rideways.com/";
    public static Map<String, Integer> TAXI_CAPACITY = new HashMap<>();

    public ConstantUtils()
    {
        //initiate taxi capacity map
        TAXI_CAPACITY.put("STANDARD", 4);
        TAXI_CAPACITY.put("EXECUTIVE", 4);
        TAXI_CAPACITY.put("LUXURY", 4);
        TAXI_CAPACITY.put("PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("LUXURY_PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("MINIBUS", 16);

    }


}
