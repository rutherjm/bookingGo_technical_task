package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);

		Scanner input = new Scanner(System.in);

		String supplierid;
		Double pickuplat, pickuplong, dropofflat, dropofflong;

        AccessSupplierService as = new AccessSupplierService();

        //Get parameters for query
        supplierid = "dave";
        System.out.println("Pick up latitude: ");
        pickuplat = input.nextDouble();
        System.out.println("Pick up longitude: ");
        pickuplong = input.nextDouble();
        System.out.println("drop off latitude: ");
        dropofflat = input.nextDouble();
        System.out.println("Drop off longitude: ");
        dropofflong = input.nextDouble();
        

        ResponseEntity response = (as.getResponse(new Query(supplierid, new Coordinate(pickuplat, pickuplong),new Coordinate(dropofflat, dropofflong    ))));

        if (response.getStatusCode() == HttpStatus.OK){
            SuccessfulResponse sr = JsonUtils.deserialize(response.getBody().toString());
            Arrays.sort(sr.getOptions(), new SortByPrice());
            for (Option option: sr.getOptions())
            {
                System.out.println(option.carType + " - " + option.price);
            }
        }
        else if ((response.getStatusCodeValue() == 400) || (response.getStatusCodeValue() == 500)){
            ErrorResponse er = JsonUtils.deserializeError(response.getBody().toString());
            System.out.println(er.error);
        }




	}
}
