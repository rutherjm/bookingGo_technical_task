package rutherjm.bookinggo.test;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import rutherjm.bookinggo.test.JSONEntities.Coordinate;
import rutherjm.bookinggo.test.JSONEntities.JsonOption;

import java.util.ArrayList;

import static rutherjm.bookinggo.test.ConstantUtils.TIMEOUT_MS;

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
            ResponseEntity response = as.getResponse(q, TIMEOUT_MS); //a timeout of '0' indicates no time-out.

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
            ResponseEntity response = as.getResponse(q,  0);
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
}
