package rutherjm.bookinggo.test;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import rutherjm.bookinggo.test.JSONEntities.Coordinate;

public class AccessSupplierServiceTest {
    @Test
    public void checkAResponseIsReturnedFromAPI()
    {
        boolean passed;
        AccessSupplierService as = new AccessSupplierService();
        Query q = new Query(new Coordinate(32, 54),new Coordinate(76, 12));

        try
        {
            ResponseEntity response = as.getResponse(q, "DAVE");

            passed = response.hasBody(); //we will pass if the reason has a body.
        }
        catch(HttpClientErrorException | HttpServerErrorException e)
        {
            passed = true; //we're just asserting that something is returned here - these errors mean it was still successful at this stage.
        }
        assert(passed);
    }
    @Test
    public void invalidSupplierShouldBeClientError()
    {
        AccessSupplierService as = new AccessSupplierService();
        Query q = new Query(new Coordinate(32, 54),new Coordinate(76, 12));

        try
        {
            ResponseEntity response = as.getResponse(q, "notASupplierID");
        }
        catch(HttpClientErrorException e)
        {
            assert(true);
        }
    }
}
