package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    @JsonProperty("timestamp")
    String timestamp;
    @JsonProperty("status")
    String status;
    @JsonProperty("error")
    String error;
    @JsonProperty("message")
    String message;
    @JsonProperty("path")
    String path;
}
