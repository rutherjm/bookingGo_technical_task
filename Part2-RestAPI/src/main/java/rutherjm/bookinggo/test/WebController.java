package rutherjm.bookinggo.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rutherjm.bookinggo.test.JSONEntities.Coordinate;

import java.util.ArrayList;

@RestController
public class WebController {
    @RequestMapping(value = "/get_status")
    public String get_status()
    {
        return "Application alive. ";
    }
    @RequestMapping(value = "/getCheapestTaxis", params = {"pickuplat", "pickuplong", "dropofflat", "dropofflong", "capacity"})
    public ResponseEntity getCheapestTaxis(@RequestParam("pickuplat") double pickuplat,
                                           @RequestParam("pickuplong") double pickuplong,
                                           @RequestParam("dropofflat") double dropofflat,
                                           @RequestParam("dropofflong") double dropofflong,
                                           @RequestParam("capacity") int capacity)
    {
        //Create the header to show our response will be of JSON format.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Create a new Query object with the request parameters.
        Query q = new Query(new Coordinate(pickuplat, pickuplong), new Coordinate(dropofflat, dropofflong));

        //Create a new AccessSupplierService to retrieve the available options.
        AccessSupplierService as = new AccessSupplierService();
        ArrayList<ArrayOption> list= as.getOptionSet(q, capacity);

        //Create a new object mapper to turn the ArrayList into JSON format so we can send it.
        ObjectMapper getJson = new ObjectMapper();
        try
        {
            //Convert the ArrayList to JSON.
            String jsonstring = getJson.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(list);
            //Respond with the JSON.
            return(new ResponseEntity<>(jsonstring, headers, HttpStatus.OK));
        }
        catch(JsonProcessingException e)
        {
            //Error occurred - inform the user.
            return(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
