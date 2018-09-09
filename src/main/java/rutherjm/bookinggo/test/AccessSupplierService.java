package rutherjm.bookinggo.test;

import org.apache.http.conn.ConnectTimeoutException;
import org.omg.CORBA.TIMEOUT;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rutherjm.bookinggo.test.JSONEntities.*;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static rutherjm.bookinggo.test.ConstantUtils.*;
import static rutherjm.bookinggo.test.JsonUtils.deserialize;
import static rutherjm.bookinggo.test.JsonUtils.deserializeError;

public class AccessSupplierService {

    /**
     * Takes in a query, crawls through all of the pre-set APIs, and returns an array of options for the cheapest.
     * @param q a query object.
     * @return cheapest options for each type.
     */
    public ArrayList<ArrayOption> getOptionSet(Query q, int requiredCapacity)
    {
        //Fetch responses for each supplier based on the query parameter.
        ArrayList<JsonOption> allJsonOptions = new ArrayList<>();
        ArrayList<ArrayOption> cheapestOptions;

        //Go through each supplier
        for (String id : TAXI_FIRM_IDS)
        {
            //Fetch response from supplier
            try{
                ResponseEntity responseEntity =  getResponse(q, id, TIMEOUT_MS);
                convertResponseStringToOptionList(allJsonOptions, responseEntity.getBody().toString(), requiredCapacity);

            }
            catch (HttpClientErrorException | HttpServerErrorException e)
            {
                //response wasn't 200-OK, lets deserialize it into an ErrorResponse object so we can print to console with a useful message.
                ErrorResponse response = deserializeError(e.getResponseBodyAsString());
                System.out.println(String.format(SUPPLIER_RESPONSE_NOT_RETRIEVED, response.path, response.status, response.error, response.message));
            }
            catch (ResourceAccessException e)
            {
                System.out.println("Resource access exception. Perhaps the request missed the timeout period of " + TIMEOUT_MS + "ms");
            }
        }
        cheapestOptions = filterCheapestOptions(allJsonOptions);
        return cheapestOptions;
    }

    /**
     * Takes in a response body and fetches any options within it.
     * @param body response body.
     */
    private void convertResponseStringToOptionList(ArrayList<JsonOption> list, String body, int requiredCapacity)
    {
        //Response is OK - read it.
        //Deserialize response.
            //try to deserialize what we're assuming is going to be a 200-OK response.
            SuccessfulResponse response = deserialize(body);

            //Add all options within the response into the allJsonOptions array
            for (JsonOption jsonOption : response.getOptions())
            {
                //Filter list down to the taxis with the correct capacity.
                if (doesTaxiMeetCapacity(jsonOption.carType, requiredCapacity))
                {
                    //No car type present in the cheapest list - so add it in.
                    jsonOption.supplierID = response.supplierID;
                    list.add(jsonOption);
                }
            }
    }
    /**
     * Takes a list of all options, and returns a list with the cheapest options for that type.
     * @param all all the options.
     * @return the filtered options.
     */
    public ArrayList<ArrayOption> filterCheapestOptions(ArrayList<JsonOption> all)
    {
        ArrayList<ArrayOption> cheapestOptions = new ArrayList<>();

        //Filter the allJsonOptions ArrayList to the cheapest only.
        for (int i = 0; i < TAXI_TYPES.size(); i++)
        {
            //For each taxi type
            boolean isFound = false; //Represents whether a cheaper jsonOption has been found.

            ArrayOption cheapest = new ArrayOption("", MAX_INT_VALUE, ""); //An element with the highest possible price to compare against.
            for (JsonOption jsonOption : all) //Go through each jsonOption
            {
                //For each jsonOption
                if (jsonOption.carType.equals(TAXI_TYPES.get(i))) //Does this jsonOption carType equal the one we're currently looking for?
                {
                    if (jsonOption.price < cheapest.price) //If this is a cheaper jsonOption than the one we've already found
                    {
                        isFound = true; //mark as we've found a cheaper jsonOption.
                        cheapest = new ArrayOption(jsonOption.carType, jsonOption.price, jsonOption.supplierID); //set this jsonOption as the cheapest.
                    }
                }
            }
            if (isFound)
            {
                //we found a valid jsonOption to add.
                cheapestOptions.add(cheapest); //add the cheapest jsonOption to the array.
            }
        }
        return cheapestOptions;
    }
    /**
     * Checks if a car type meets the required capacity.
     * @param carType the car type to check against.
     * @param requiredCapacity the capacity required.
     * @return true if taxi meets capacity.
     */
    public boolean doesTaxiMeetCapacity(String carType, int requiredCapacity)
    {
        Map<String, Integer> TAXI_CAPACITY = new HashMap<>();
        TAXI_CAPACITY.put("STANDARD", 4);
        TAXI_CAPACITY.put("EXECUTIVE", 4);
        TAXI_CAPACITY.put("LUXURY", 4);
        TAXI_CAPACITY.put("PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("LUXURY_PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("MINIBUS", 16);

        if (TAXI_CAPACITY.get(carType) >= requiredCapacity) return true; //taxi meets capacity.
        return false; //taxi doesn't meet capacity.
    }

    /**
     * Fetches a response from the supplier.
     * @param query the query to fetch a response for.
     * @param supplierID the supplier we're contacting
     * @return the response
     * @throws HttpClientErrorException
     * @throws HttpServerErrorException
     */
    @Bean
    public ResponseEntity getResponse(Query query, String supplierID, int timeout) throws HttpClientErrorException, HttpServerErrorException, ResourceAccessException
    {

        //set timeouts
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        clientHttpRequestFactory.setReadTimeout(timeout);

        //Create a new RestTemplate with the timeout set.
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        //Configure headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        //Create the URL to use with the correct query.
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(API_HOSTNAME)
                .path(supplierID)
                .queryParam("pickup", query.getPickUp().getX() + "," + query.getPickUp().getY())
            .queryParam("dropoff", query.getDropOff().getX() + "," + query.getDropOff().getY());


        HttpEntity<?> entity = new HttpEntity<>(headers);

        //Fetch the response.

            ResponseEntity<String> response = restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class);


            return response;

    }
}
