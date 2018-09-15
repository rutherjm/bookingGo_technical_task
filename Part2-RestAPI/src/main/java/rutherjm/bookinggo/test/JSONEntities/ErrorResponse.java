package rutherjm.bookinggo.test.JSONEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    @JsonProperty("timestamp")
    public String timestamp;
    @JsonProperty("status")
    public String status;
    @JsonProperty("error")
    public String error;
    @JsonProperty("message")
    public String message;
    @JsonProperty("path")
    public String path;
}
