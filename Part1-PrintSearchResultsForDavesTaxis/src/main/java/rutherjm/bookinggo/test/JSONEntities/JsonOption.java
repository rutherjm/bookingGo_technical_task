package rutherjm.bookinggo.test.JSONEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonOption {
    @JsonProperty("car_type")
    public String carType;
    @JsonProperty("price")
    public int price;
    public String supplierID;

}