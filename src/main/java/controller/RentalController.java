package controller;

import common.RentalRequestValidator;
import model.Rental;
import model.RentalRequest;
import model.ReturnRequest;
import service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/{rentalId}/start")
    public ResponseEntity<String> beginRent(@RequestHeader("X-TenantID") String tenantId,
                                            @RequestBody RentalRequest rentalRequest) {
        System.out.print("Begin start!!!!!!!");
        if (!RentalRequestValidator.validate(rentalRequest)) {
            // Invalid request
            return ResponseEntity.badRequest().build();
        }

        if (!rentalService.isBikeAvailable(Long.parseLong(rentalRequest.getBikeId()))) {
            return ResponseEntity.badRequest().build();
        }

        rentalService.startRental(tenantId, rentalRequest);

        return ResponseEntity.ok("Rental acquired successfully");
    }

    @PostMapping("/{rentalId}/return")
    public ResponseEntity<String> processReturn(@PathVariable("rentalId") String rentalId,
                                                @RequestBody ReturnRequest returnRequest) {
        boolean isProcessed = rentalService.processReturn(rentalId, returnRequest);
        if (isProcessed) {
            return ResponseEntity.ok("Rental returned successfully");
        } else {
            return ResponseEntity.badRequest().body("Rental is already returned");
        }
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<Rental> getRentalByID(@PathVariable("rentalId") String rentalId) {
        Optional<Rental> optionalRental = rentalService.getRentalById(rentalId);

        return optionalRental.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rental>> getRentalsByUser(@PathVariable("userId") String userId) {
        List<Rental> rentals = rentalService.getRentalsByUser(userId);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/bike/{bikeId}/current")
    public ResponseEntity<Rental> getCurrentRentalByBike(@PathVariable("bikeId") String bikeId) {
        Optional<Rental> optionalRental = rentalService.getCurrentRentalByBike(bikeId);

        return optionalRental.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Rental>> getRentalsByTenant(@RequestHeader("X-TenantID") String tenantId) {
        List<Rental> rentals = rentalService.getRentalsByTenant(tenantId);
        return ResponseEntity.ok(rentals);
    }
}
