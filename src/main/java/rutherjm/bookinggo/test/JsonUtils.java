package rutherjm.bookinggo.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    public static SuccessfulResponse deserialize(String json)
    {
        ObjectMapper mapper = new ObjectMapper();
        SuccessfulResponse response = new SuccessfulResponse();
        try{
            response = mapper.readValue(json, SuccessfulResponse.class);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return response;
    }
    public static ErrorResponse deserializeError(String json)
    {
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse response = new ErrorResponse();
        try{
            response = mapper.readValue(json, ErrorResponse.class);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return response;
    }
}
