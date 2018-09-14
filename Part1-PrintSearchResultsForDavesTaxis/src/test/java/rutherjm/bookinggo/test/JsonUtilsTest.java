package rutherjm.bookinggo.test;

import org.junit.Test;
import rutherjm.bookinggo.test.JSONEntities.SuccessfulResponse;

import static org.junit.Assert.assertEquals;
import static rutherjm.bookinggo.test.JsonUtils.deserialize;

public class JsonUtilsTest {
    @Test
    public void shouldCreateSuccessfulResponseObject()
    {
        String input = "{\"supplier_id\":\"DAVE\",\"pickup\":\"45.0,34.0\",\"dropoff\":\"23.0,22.0\",\"options\":[{\"car_type\":\"STANDARD\",\"price\":899155},{\"car_type\":\"LUXURY\",\"price\":129438},{\"car_type\":\"MINIBUS\",\"price\":118858}]}";

        SuccessfulResponse sr = deserialize(input);

        assertEquals("DAVE", sr.getSupplierID());
        assertEquals("45.0,34.0", sr.pickup);
        assertEquals("23.0,22.0", sr.dropoff);
        assertEquals("STANDARD", sr.getOptions()[0].carType);
        assertEquals(899155, sr.getOptions()[0].price);
        assertEquals("LUXURY", sr.getOptions()[1].carType);
        assertEquals(129438, sr.getOptions()[1].price);
    }
    @Test
    public void shouldReturnNullWhenInputNotRecognised()
    {
        String input = "{\"someIncorrectField\":\"someIncorrectValue\"}";

        SuccessfulResponse sr = deserialize(input);

        assertEquals(null, sr);
    }
}
