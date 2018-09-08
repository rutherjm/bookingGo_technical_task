package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

import static rutherjm.bookinggo.test.ConstantUtils.*;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);

		Scanner input = new Scanner(System.in);

		Double pickuplat, pickuplong, dropofflat, dropofflong;
		int minimumCapacity;

        AccessSupplierService as = new AccessSupplierService();

        //Get parameters for query
        System.out.println("Pick up latitude: ");
        pickuplat = input.nextDouble();
        System.out.println("Pick up longitude: ");
        pickuplong = input.nextDouble();
        System.out.println("drop off latitude: ");
        dropofflat = input.nextDouble();
        System.out.println("Drop off longitude: ");
        dropofflong = input.nextDouble();
        System.out.println("Enter minimum capacity:");
        minimumCapacity = input.nextInt();


        Query q = new Query(new Coordinate(pickuplat, pickuplong),new Coordinate(dropofflat, dropofflong));
        //sendQuery(q, minimumCapacity);

        as.getOptionSet(q, minimumCapacity);

	}
/**
	public static void sendQuery(Query q, int minCapacity)
    {
        TAXI_CAPACITY.put("STANDARD", 4);
        TAXI_CAPACITY.put("EXECUTIVE", 4);
        TAXI_CAPACITY.put("LUXURY", 4);
        TAXI_CAPACITY.put("PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("LUXURY_PEOPLE_CARRIER", 6);
        TAXI_CAPACITY.put("MINIBUS", 16);

        ResponseEntity response = new AccessSupplierService().getResponse(q);
        if (response.getStatusCode() == HttpStatus.OK){
            SuccessfulResponse sr = JsonUtils.deserialize(response.getBody().toString());

            if (sr.getOptions().length > 0)
            {
                Arrays.sort(sr.getOptions(), new SortByPrice());
                for (Option option: sr.getOptions())
                {
                    if (TAXI_CAPACITY.get(option.carType) >= minCapacity)
                    {
                        System.out.println(option.carType + " - " + option.price);
                    }
                }
            }
            else{
                System.out.println("None available. ");
            }
        }
        else if ((response.getStatusCodeValue() == 400) || (response.getStatusCodeValue() == 500)){
            ErrorResponse er = JsonUtils.deserializeError(response.getBody().toString());
            System.out.println(er.error);
        }

    }
 */
}
