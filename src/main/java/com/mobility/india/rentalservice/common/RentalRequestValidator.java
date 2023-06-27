package com.mobility.india.rentalservice.common;

import com.mobility.india.rentalservice.model.RentalRequest;

public class RentalRequestValidator {

    public static boolean validate(RentalRequest rentalRequest) {
        if (rentalRequest.getUserId() == null || rentalRequest.getUserId().isEmpty()) {
            System.out.println("User ID is required");
            return false;
        }

        if (rentalRequest.getBikeId() == null || rentalRequest.getBikeId().isEmpty()) {
            System.out.println("Bike ID is required");
            return false;
        }

        if (rentalRequest.getDuration() <= 0) {
            System.out.println("Duration should be greater than zero");
            return false;
        }

        // Additional validation rules can be added here

        return true;
    }
}

