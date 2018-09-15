package rutherjm.bookinggo.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantUtils {
    public static final String API_HOSTNAME = "https://techtest.rideways.com/";
    public static final List<String> TAXI_FIRM_IDS = Arrays.asList("dave", "eric", "jeff");
    public static final List<String> TAXI_TYPES = Arrays.asList("STANDARD", "EXECUTIVE", "LUXURY", "PEOPLE_CARRIER", "LUXURY_PEOPLE_CARRIER", "MINIBUS");

    public static final int MAX_INT_VALUE = 2147483647;
    public static final int TIMEOUT_MS = 2000;
    //ERRORS
    public static final String SUPPLIER_RESPONSE_NOT_RETRIEVED = "Supplier %s returned %s %s. Cause: %s ";
    public static final String RESOURCE_ACCESS_EXCEPTION_ERROR = "Resource access exception. Perhaps the request missed the timeout period of %sms";

    //Taxi capacities.
    public static final Map<String, Integer> TAXI_CAPACITY = new HashMap<String, Integer>()
    {{
        put("STANDARD", 4);
        put("EXECUTIVE", 4);
        put("LUXURY", 4);
        put("PEOPLE_CARRIER", 6);
        put("LUXURY_PEOPLE_CARRIER", 6);
        put("MINIBUS", 16);
    }};

}
