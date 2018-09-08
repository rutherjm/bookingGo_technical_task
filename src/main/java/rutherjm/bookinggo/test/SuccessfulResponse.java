package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)

public class SuccessfulResponse {
    @JsonProperty("supplier_id")
    private String supplierID;
    @JsonProperty("pickup")
    private String pickup; //change these to coordinates later
    @JsonProperty("dropoff")
    private String dropoff; //change these to coordinates later.
    @JsonProperty("options")
    public Option[] option;

    public String getSupplierID()
    {
        return supplierID;
    }
    public Option[] getOptions()
    {
        return option;
    }
}
