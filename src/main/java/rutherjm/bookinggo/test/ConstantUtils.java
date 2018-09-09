package rutherjm.bookinggo.test;

import java.util.Arrays;
import java.util.List;

public class ConstantUtils {
    public static final String API_HOSTNAME = "https://techtest.rideways.com/";
    public static final List<String> TAXI_FIRM_IDS = Arrays.asList("dave", "eric", "jeff");
    public static final List<String> TAXI_TYPES = Arrays.asList("STANDARD", "EXECUTIVE", "LUXURY", "PEOPLE_CARRIER", "LUXURY_PEOPLE_CARRIER", "MINIBUS");

    public static final int MAX_INT_VALUE = 2147483647;
    public static final int TIMEOUT_MS = 2000000;
    //ERRORS
    public static final String SUPPLIER_RESPONSE_NOT_RETRIEVED = "Supplier %s returned %s %s. Cause: %s ";

}
