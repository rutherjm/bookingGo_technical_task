package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Option{
    @JsonProperty("car_type")
    public String carType;
    @JsonProperty("price")
    public int price;
    public String supplierID;

}