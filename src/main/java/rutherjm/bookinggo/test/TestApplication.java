package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);

        AccessSupplierService as = new AccessSupplierService();

        ResponseEntity response = (as.getResponse(new Query("dave", new Coordinate(3.410632, -2.157533),new Coordinate(3.410632, -2.157533))));

        if (response.getStatusCode() == HttpStatus.OK){
            SuccessfulResponse sr = JsonUtils.deserialize(response.getBody().toString());
            System.out.println(sr.getOptions()[1].carType);
        }
        else if ((response.getStatusCodeValue() == 400) || (response.getStatusCodeValue() == 500)){
            ErrorResponse er = JsonUtils.deserializeError(response.getBody().toString());
            System.out.println(er.error);
        }




	}
}
