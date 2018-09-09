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


		while(true)
        {
            //Get parameters for query
            System.out.println("Pick up latitude: ");
            pickuplat = getDoubleValue(input);
            System.out.println("Pick up longitude: ");
            pickuplong = getDoubleValue(input);
            System.out.println("drop off latitude: ");
            dropofflat = getDoubleValue(input);
            System.out.println("Drop off longitude: ");
            dropofflong = getDoubleValue(input);
            System.out.println("Enter minimum capacity:");
            minimumCapacity = getIntValue(input);

            //Create a new query object.
            Query q = new Query(new Coordinate(pickuplat, pickuplong),new Coordinate(dropofflat, dropofflong));

            //Create a new AccessSupplierService for this static context.
            AccessSupplierService as = new AccessSupplierService();
            ArrayList<ArrayOption> options = as.getOptionSet(q, minimumCapacity);

            //Show the results on console.
            for (ArrayOption option: options)
            {
                System.out.println(option.carType + " - " + option.supplierID + " - "  + option.price);
            }
        }

	}

    /**
     * Keeps looping until the Scanner input is of Double value.
     * @param in the scanner being used on console.
     * @return the new double value.
     */
	private static double getDoubleValue(Scanner in)
    {
        String input;
        while(true) //keep looping until we convert a number to double successfully.
        {
            input = in.next();
            try
            {
                return Double.parseDouble(input);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Value not numerical! Try again.");
            }
        }
    }
    /**
     * Keeps looping until the Scanner input is of Integer value.
     * @param in the scanner being used on console.
     * @return the new integer value.
     */
    private static int getIntValue(Scanner in)
    {
        String input;
        while(true) //keep looping until we convert a number to double successfully.
        {
            input = in.next();
            try
            {
                return Integer.parseInt(input);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Value not numerical! Try again.");
            }
        }
    }
}
