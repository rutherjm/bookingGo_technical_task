package rutherjm.bookinggo.test;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import rutherjm.bookinggo.test.JSONEntities.Coordinate;
import rutherjm.bookinggo.test.JSONEntities.JsonOption;

import java.util.ArrayList;

public class AccessSupplierServiceTest {
    /**
     * Should check that a response object is returned that contains something (OK to include client/server errors at this stage. )
     */
    @Test
    public void checkAResponseIsReturnedFromAPI()
    {
        boolean passed;
        AccessSupplierService as = new AccessSupplierService();
        Query q = new Query(new Coordinate(32, 54),new Coordinate(76, 12));

        try
        {
            ResponseEntity response = as.getResponse(q, "DAVE",0); //a timeout of '0' indicates no time-out.

            passed = response.hasBody(); //we will pass if the reason has a body.
        }
        catch(HttpClientErrorException | HttpServerErrorException e)
        {
            passed = true; //we're just asserting that something is returned here - these errors mean it was still successful at this stage.
        }
        catch(ResourceAccessException e)
        {
            passed = false;
        }
        assert(passed);
    }

    /**
     * Checks that a ClientError is thrown when the supplier ID is invalid.
     */
    @Test
    public void invalidSupplierShouldBeClientError()
    {
        AccessSupplierService as = new AccessSupplierService();
        Query q = new Query(new Coordinate(32, 54),new Coordinate(76, 12));

        try
        {
            ResponseEntity response = as.getResponse(q, "notASupplierID", 0);
        }
        catch(HttpClientErrorException e)
        {
            assert(true);
        }
        catch(ResourceAccessException e)
        {
            assert(false);
        }
    }

    /**
     * Checks that a standard taxi is valid when the user has specified a minimum size of four.
     */
    @Test
    public void minRequirementOfFourForAStandardTaxi()
    {
        AccessSupplierService as = new AccessSupplierService();
        assert(as.doesTaxiMeetCapacity("STANDARD", 4));
    }
    /**
     * Checks that a standard taxi is invalid when the user has specified a minimum size of five.
     */
    @Test
    public void minRequirementOfFiveForAStandardTaxi()
    {
        AccessSupplierService as = new AccessSupplierService();
        assert(!as.doesTaxiMeetCapacity("STANDARD", 5));
    }

    /**
     * Checks that the cheapest filter method returns only the cheapest options available within the given arraylist.
     */
    @Test
    public void cheapestFilterShouldReturnCorrectSet()
    {
        boolean found = false;
        ArrayList<JsonOption> list = new ArrayList<>();
        list.add(new JsonOption());
        list.add(new JsonOption());
        list.add(new JsonOption());
        list.add(new JsonOption());

        list.get(0).carType = "STANDARD";
        list.get(0).price = 100;
        list.get(1).carType = "STANDARD";
        list.get(1).price = 250;
        list.get(2).carType = "LUXURY";
        list.get(2).price = 704;
        list.get(3).carType = "LUXURY";
        list.get(3).price = 605;

        AccessSupplierService as = new AccessSupplierService();
        ArrayList<ArrayOption> filtered = as.filterCheapestOptions(list);

        for(ArrayOption option: filtered)
        {
            if((option.carType == "LUXURY") && (option.price == 605)) found = true;
        }
        assert(found);
    }
}
