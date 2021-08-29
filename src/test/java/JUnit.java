package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import main.java.RentalAgreement;
import org.junit.jupiter.api.Test;

public class JUnit {

    RentalAgreement rentalAgreement;

    @Test
    void test1()  {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalAgreement = new RentalAgreement("JAKR", 5, 101, "9/3/15");
        });
        assertEquals("Discount percent can't be greater than 100", exception.getMessage());
    }

    @Test
    void test2() {
        rentalAgreement = new RentalAgreement("LADW", 3, 10, "7/2/20");
        assertEquals("Rental Agreement: \n" +
                "Tool code: LADW\n" +
                "Tool type: Ladder\n" +
                "Tool brand: Werner\n" +
                "Rental days: 3\n" +
                "Checkout date: 07/02/20\n" +
                "Due date: 07/05/20\n" +
                "Daily rental charge: $1.99\n" +
                "Charge days: 2\n" +
                "Pre-discount charge: $3.98\n" +
                "Discount percent: 10%\n" +
                "Discount amount: $0.4\n" +
                "Final charge: $3.58", rentalAgreement.toString());
    }

    @Test
    void test3() {
        rentalAgreement = new RentalAgreement("CHNS", 5, 25, "7/2/15");
        assertEquals("Rental Agreement: \n" +
                "Tool code: CHNS\n" +
                "Tool type: Chainsaw\n" +
                "Tool brand: Stihl\n" +
                "Rental days: 5\n" +
                "Checkout date: 07/02/15\n" +
                "Due date: 07/07/15\n" +
                "Daily rental charge: $1.49\n" +
                "Charge days: 3\n" +
                "Pre-discount charge: $4.47\n" +
                "Discount percent: 25%\n" +
                "Discount amount: $1.12\n" +
                "Final charge: $3.35", rentalAgreement.toString());
    }

    @Test
    void test4() {
        rentalAgreement = new RentalAgreement("JAKD", 6, 0, "9/3/15");
        assertEquals("Rental Agreement: \n" +
                "Tool code: JAKD\n" +
                "Tool type: Jackhammer\n" +
                "Tool brand: DeWalt\n" +
                "Rental days: 6\n" +
                "Checkout date: 09/03/15\n" +
                "Due date: 09/09/15\n" +
                "Daily rental charge: $2.99\n" +
                "Charge days: 3\n" +
                "Pre-discount charge: $8.97\n" +
                "Discount percent: 0%\n" +
                "Discount amount: $0.0\n" +
                "Final charge: $8.97", rentalAgreement.toString());
    }

    @Test
    void test5() {
        rentalAgreement = new RentalAgreement("JAKR", 9, 0, "7/2/15");
        assertEquals("Rental Agreement: \n" +
                "Tool code: JAKR\n" +
                "Tool type: Jackhammer\n" +
                "Tool brand: Ridgid\n" +
                "Rental days: 9\n" +
                "Checkout date: 07/02/15\n" +
                "Due date: 07/11/15\n" +
                "Daily rental charge: $2.99\n" +
                "Charge days: 5\n" +
                "Pre-discount charge: $14.95\n" +
                "Discount percent: 0%\n" +
                "Discount amount: $0.0\n" +
                "Final charge: $14.95", rentalAgreement.toString());
    }

    @Test
    void test6() {
        rentalAgreement = new RentalAgreement("JAKR", 4, 50, "7/2/20");
        assertEquals("Rental Agreement: \n" +
                "Tool code: JAKR\n" +
                "Tool type: Jackhammer\n" +
                "Tool brand: Ridgid\n" +
                "Rental days: 4\n" +
                "Checkout date: 07/02/20\n" +
                "Due date: 07/06/20\n" +
                "Daily rental charge: $2.99\n" +
                "Charge days: 1\n" +
                "Pre-discount charge: $2.99\n" +
                "Discount percent: 50%\n" +
                "Discount amount: $1.5\n" +
                "Final charge: $1.49", rentalAgreement.toString());
    }

    //Exception testing for rental days < 1
    @Test
    void test7()  {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalAgreement = new RentalAgreement("JAKR", 0, 2, "1/3/20");
        });
        assertEquals("Rental days can't be less than 1", exception.getMessage());
    }

    //Exception testing for negative discount
    @Test
    void test8()  {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalAgreement = new RentalAgreement("LADW", 4, -1, "9/3/15");
        });
        assertEquals("Discount percent can't be less than 0", exception.getMessage());
    }

    //Testing 4th of July on a weekday for LADW
    @Test
    void test9() {
        rentalAgreement = new RentalAgreement("LADW", 5, 13, "6/30/16");
        assertEquals("Rental Agreement: \n" +
                "Tool code: LADW\n" +
                "Tool type: Ladder\n" +
                "Tool brand: Werner\n" +
                "Rental days: 5\n" +
                "Checkout date: 06/30/16\n" +
                "Due date: 07/05/16\n" +
                "Daily rental charge: $1.99\n" +
                "Charge days: 4\n" +
                "Pre-discount charge: $7.96\n" +
                "Discount percent: 13%\n" +
                "Discount amount: $1.03\n" +
                "Final charge: $6.93", rentalAgreement.toString());
    }

    //Testing 4th of July on a weekday for JAKD
    @Test
    void test10() {
        rentalAgreement = new RentalAgreement("JAKD", 4, 3, "6/30/16");
        assertEquals("Rental Agreement: \n" +
                "Tool code: JAKD\n" +
                "Tool type: Jackhammer\n" +
                "Tool brand: DeWalt\n" +
                "Rental days: 4\n" +
                "Checkout date: 06/30/16\n" +
                "Due date: 07/04/16\n" +
                "Daily rental charge: $2.99\n" +
                "Charge days: 1\n" +
                "Pre-discount charge: $2.99\n" +
                "Discount percent: 3%\n" +
                "Discount amount: $0.09\n" +
                "Final charge: $2.9", rentalAgreement.toString());
    }

    //Exception testing for toolcode not found
    @Test
    void test11()  {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalAgreement = new RentalAgreement("BIKE", 4, 2, "9/3/15");
        });
        assertEquals("Tool type was not found!", exception.getMessage());
    }
}
