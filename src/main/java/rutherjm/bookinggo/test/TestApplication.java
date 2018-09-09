package rutherjm.bookinggo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rutherjm.bookinggo.test.JSONEntities.Coordinate;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);

		Scanner input = new Scanner(System.in);

		Double pickuplat, pickuplong, dropofflat, dropofflong;
		int minimumCapacity;


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

        //Create a new query object.
        Query q = new Query(new Coordinate(pickuplat, pickuplong),new Coordinate(dropofflat, dropofflong));


        AccessSupplierService as = new AccessSupplierService();
        ArrayList<ArrayOption> options = as.getOptionSet(q, minimumCapacity);

        for (ArrayOption option: options)
        {
            System.out.println(option.carType + " - " + option.supplierID + " - "  + option.price);
        }
	}
}
