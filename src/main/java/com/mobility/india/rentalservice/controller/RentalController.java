package com.mobility.india.rentalservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobility.india.rentalservice.model.*;
import com.mobility.india.rentalservice.repository.RentalRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    RentalRepository rentalRepository;
    @PostMapping
    public ResponseEntity<String> initiateRental(@RequestHeader("X-TenantID") String tenantId,
                                                 @RequestBody RentalRequest rentalRequest) {
        // Create a new Rental object
        Rental rental = new Rental();
        rental.setRentalId(UUID.randomUUID().toString());
        rental.setUserId(rentalRequest.getUserId());
        rental.setBikeId(rentalRequest.getBikeId());
        rental.setTenantId(tenantId); // Set the tenant identifier
        
        // Set other rental properties as needed

        // Save the rental to the database
        rentalRepository.save(rental);

        // Return appropriate response
        return ResponseEntity.ok("Rental initiated successfully");
    }

    @PostMapping("/{rentalId}/return")
    public ResponseEntity<String> processReturn(@PathVariable("rentalId") String rentalId,
                                                @RequestBody ReturnRequest returnRequest) {
        Optional<Rental> optionalRental = rentalRepository.findByRentalId(rentalId);

        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            if (rental.getEndTime() == null) {
                LocalDateTime endTime = LocalDateTime.now();
                rental.setEndTime(endTime);
                // Set other return details like return location, damages, etc.
                // Calculate rental cost if necessary
                // Update the rental in the database
                rentalRepository.save(rental);

                return ResponseEntity.ok("Rental returned successfully");
            } else {
                return ResponseEntity.badRequest().body("Rental is already returned");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<Rental> getRentalByID(@PathVariable("rentalId") String rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findByRentalId(rentalId);

        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            return ResponseEntity.ok(rental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rental>> getRentalsByUser(@PathVariable("userId") String userId) {
        List<Rental> rentals = rentalRepository.findByUserId(userId);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/bike/{bikeId}/current")
    public ResponseEntity<Rental> getCurrentRentalByBike(@PathVariable("bikeId") String bikeId) {
        Optional<Rental> optionalRental = rentalRepository.findFirstByBikeIdAndEndTimeIsNull(bikeId);

        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            return ResponseEntity.ok(rental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Rental>> getRentalsByTenant(@RequestHeader("X-TenantID") String tenantId) {
        // Retrieve rentals by tenant ID
        List<Rental> rentals = rentalRepository.findByTenantId(tenantId);

        // Return appropriate response
        return ResponseEntity.ok(rentals);
    }
}
