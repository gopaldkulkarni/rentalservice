package com.mobility.india.rentalservice.pricing;

import com.mobility.india.rentalservice.model.Rental;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    @Value("${rental.pricing.model}")
    private String pricingModel;

    public BigDecimal calculatePrice(Rental rental) {
        if ("prepaid".equalsIgnoreCase(pricingModel)) {
            return BigDecimal.valueOf(1000);//Dummy data
        } else if ("postpaid".equalsIgnoreCase(pricingModel)) {
            // Postpaid pricing logic
            // Calculate the price based on the rental duration and other factors
            return BigDecimal.valueOf(2000);//Dummy data
        } else {
            // Handle invalid or unsupported pricing model
            throw new UnsupportedOperationException("Invalid pricing model: " + pricingModel);
        }
    }

}
