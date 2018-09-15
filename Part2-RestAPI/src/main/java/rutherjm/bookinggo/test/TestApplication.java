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
		System.out.println("Launched Web REST API. ");

    }
}
