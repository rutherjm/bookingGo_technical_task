package rutherjm.bookinggo.test;

import com.sun.net.httpserver.Authenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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
     * @return cheapest options for each type.
     */
    public ArrayList<ArrayOption> getOptionSet(Query q, int requiredCapacity)
    {
        TAXI_CAPACITY.put("STANDARD", 4);
        TAXI_CAPACITY.put("EXECUTIVE", 4);
        TAXI_CAPACITY.put("LUXURY", 4);
        TAXI_CAPACITY.put("PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("LUXURY_PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("MINIBUS", 16);

        //Fetch responses for each supplier based on the query parameter.
        ArrayList<Option> allOptions = new ArrayList<>();
        ArrayList<ArrayOption> cheapestOptions = new ArrayList<>();
        for (String id : TAXI_FIRM_IDS)
        {
            //Get the response
            ResponseEntity responseEntity =  getResponse(q, id);
            if (responseEntity.getStatusCodeValue() == 200) //is the response containing the options we're expecting?
            {
                //Deserialize response
                SuccessfulResponse response = deserialize(responseEntity.getBody().toString());
                //Add all options into the allOptions array
                for (Option option: response.getOptions())
                {
                    //Filter list down to the taxis with the correct capacity.
                    if (TAXI_CAPACITY.get(option.carType) >= requiredCapacity)
                    {
                        //No car type present in the cheapest list - so add it in.
                        option.supplierID = id;
                        allOptions.add(option);
                    }
                }
            }
        }


        //Filter list to the cheapest only
        for (int i = 0; i < TAXI_TYPES.size(); i++)
        {
            boolean isFound = false;
            ArrayOption cheapest = new ArrayOption("", MAX_INT_VALUE, "");
            for (Option option: allOptions)
            {
                if (option.carType.equals(TAXI_TYPES.get(i)))
                {
                    if (option.price < cheapest.price)
                    {
                        isFound = true;
                        cheapest = new ArrayOption(option.carType, option.price, option.supplierID);
                    }
                }
            }
            if (isFound)
            {
                cheapestOptions.add(cheapest);
            }
        }
        return cheapestOptions;
    }
    @Bean
    public ResponseEntity getResponse(Query query, String supplierID) throws HttpClientErrorException, HttpServerErrorException
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
