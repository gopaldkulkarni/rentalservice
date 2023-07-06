package common;

import model.RentalRequest;

public class RentalRequestValidator {

    public static boolean validate(RentalRequest rentalRequest) {
        System.out.print("Validating the request");
        if (rentalRequest.getUserId() == null || rentalRequest.getUserId().isEmpty()) {
            System.out.println("User ID is required");
            return false;
        }

        if (rentalRequest.getBikeId() == null || rentalRequest.getBikeId().isEmpty()) {
            System.out.println("Bike ID is required");
            return false;
        }


        // Additional validation rules can be added here

        return true;
    }
}

