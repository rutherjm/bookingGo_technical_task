package rutherjm.bookinggo.test;

import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rutherjm.bookinggo.test.JSONEntities.ErrorResponse;
import rutherjm.bookinggo.test.JSONEntities.JsonOption;
import rutherjm.bookinggo.test.JSONEntities.SuccessfulResponse;

import java.util.ArrayList;

import static rutherjm.bookinggo.test.ConstantUtils.*;
import static rutherjm.bookinggo.test.JsonUtils.deserialize;
import static rutherjm.bookinggo.test.JsonUtils.deserializeError;

public class AccessSupplierService {

    /**
     * Takes in a query, crawls through all of the pre-set APIs, and returns an array of options.
     * @param q a query object.
     * @return options for each type.
     */
    public ArrayList<JsonOption> getOptionSet(Query q)
    {
        //Fetch responses for each supplier based on the query parameter.
        ArrayList<JsonOption> allJsonOptions = new ArrayList<>();

        //Go through each supplier
            //Fetch response from supplier
            try{
                ResponseEntity responseEntity =  getResponse(q, TIMEOUT_MS); //Get response
                convertResponseStringToOptionList(allJsonOptions, responseEntity.getBody().toString()); //Convert response to option list.
            }
            catch (HttpClientErrorException | HttpServerErrorException e)
            {
                //response wasn't 200-OK, lets deserialize it into an ErrorResponse object so we can print to console with a useful message.
                ErrorResponse response = deserializeError(e.getResponseBodyAsString());
                System.out.println(String.format(SUPPLIER_RESPONSE_NOT_RETRIEVED, response.path, response.status, response.error, response.message));
            }
            catch (ResourceAccessException e)
            {
                System.out.println(String.format(RESOURCE_ACCESS_EXCEPTION_ERROR, TIMEOUT_MS));
            }
        return allJsonOptions; //return the options.
    }

    /**
     * Takes in a response body and fetches any options within it.
     * @param list the list to append the results to
     * @param body response body.
     */
    private void convertResponseStringToOptionList(ArrayList<JsonOption> list, String body)
    {
        //Response is OK - read it.
        //Deserialize response.
            //try to deserialize what we're assuming is going to be a 200-OK response.
            SuccessfulResponse response = deserialize(body);

            //Add all options within the response into the allJsonOptions array
            for (JsonOption jsonOption : response.getOptions())
            {
                    jsonOption.supplierID = response.supplierID;
                    list.add(jsonOption);
            }
    }
    /**
     * Fetches a response from the supplier.
     * @param query the query to fetch a response for.
     * @return the response
     * @throws HttpClientErrorException
     * @throws HttpServerErrorException
     */
    @Bean
    public ResponseEntity getResponse(Query query, int timeout) throws HttpClientErrorException, HttpServerErrorException, ResourceAccessException
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
                .path(TAXI_FIRM_ID)
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
