package rutherjm.bookinggo.test.JSONEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessfulResponse {
    @JsonProperty("supplier_id")
    public String supplierID;
    @JsonProperty("pickup")
    public String pickup; //change these to coordinates later
    @JsonProperty("dropoff")
    public String dropoff; //change these to coordinates later.
    @JsonProperty("options")
    public JsonOption[] jsonOption;

    public String getSupplierID()
    {
        return supplierID;
    }
    public JsonOption[] getOptions()
    {
        return jsonOption;
    }
}
