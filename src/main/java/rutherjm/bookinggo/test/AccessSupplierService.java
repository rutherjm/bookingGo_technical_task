package rutherjm.bookinggo.test;

import com.sun.net.httpserver.Authenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

import static rutherjm.bookinggo.test.ConstantUtils.*;
import static rutherjm.bookinggo.test.ConstantUtils.API_HOSTNAME;
import static rutherjm.bookinggo.test.JsonUtils.deserialize;

public class AccessSupplierService {
    /**
     * Takes in a query, crawls through all of the pre-set APIs, and returns an array of options for the cheapest.
     * @param q a query object.
     * @return cheapest options for each type. (null if none available)
     */
    public Option[] getOptionSet(Query q, int requiredCapacity)
    {
        TAXI_CAPACITY.put("STANDARD", 4);
        TAXI_CAPACITY.put("EXECUTIVE", 4);
        TAXI_CAPACITY.put("LUXURY", 4);
        TAXI_CAPACITY.put("PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("LUXURY_PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("MINIBUS", 16);

        //Fetch responses for each supplier based on the query parameter.
        ArrayList<ResponseEntity> responses = new ArrayList<>();
        ArrayList<SuccessfulResponse> deserializedResponses = new ArrayList<>();

        for (String id : TAXI_FIRM_IDS)
        {
            responses.add(getResponse(q, id));
        }

        //Collect the options from each 200-OK response.
        for (ResponseEntity response: responses)
        {
           if (response.getStatusCodeValue() == 200){
               //this is a 200-OK response - we can collect useful options.
               //deserialize response.
               deserializedResponses.add(deserialize(response.getBody().toString()));
           }
        }
        //DEBUGGING - print out all responses.
        for (SuccessfulResponse sr: deserializedResponses)
        {
            System.out.println("Options for "+sr.getSupplierID());
            for (Option option: sr.getOptions())
            {
                if (TAXI_CAPACITY.get(option.carType) >= requiredCapacity)
                {
                    System.out.println(option.carType + " - " + option.price);
                }
            }
        }
        //Filter the list down to the cheapest ones.

        //Return a list of the cheapest.
        return null;
    }
    @Bean
    public ResponseEntity getResponse(Query query, String supplierID) throws HttpClientErrorException
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);


        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(API_HOSTNAME)
                .path(supplierID)
                .queryParam("pickup", query.getPickUp().getX() + "," + query.getPickUp().getY())
            .queryParam("dropoff", query.getDropOff().getX() + "," + query.getDropOff().getY());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        return response;
    }
}
