package rutherjm.bookinggo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import rutherjm.bookinggo.test.JSONEntities.ErrorResponse;
import rutherjm.bookinggo.test.JSONEntities.SuccessfulResponse;

import java.io.IOException;

public class JsonUtils {
    public static SuccessfulResponse deserialize(String json)
    {
        ObjectMapper mapper = new ObjectMapper();
        SuccessfulResponse response;
        try{
            response = mapper.readValue(json, SuccessfulResponse.class);
        }
        catch (IOException e)
        {
            System.out.println(e);
            return null;
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
