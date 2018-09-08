package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);

        AccessSupplierService as = new AccessSupplierService();

        String response = (as.getResponse(new Query("dave", new Coordinate(3.410632, -2.157533),new Coordinate(3.410632, -2.157533))));
        SuccessfulResponse sr = JsonUtils.deserialize(response);

        System.out.println(sr.getOptions()[1].carType);


	}
}
