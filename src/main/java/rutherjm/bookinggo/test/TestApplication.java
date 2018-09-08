package rutherjm.bookinggo.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
        ArrayList<ArrayOption> options = as.getOptionSet(q, minimumCapacity);


	}
}
