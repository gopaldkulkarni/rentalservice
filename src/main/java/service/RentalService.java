package service;

import model.Rental;
import model.RentalRequest;
import model.ReturnRequest;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    boolean isBikeAvailable(Long bikeId);

    void startRental(String tenantId, RentalRequest rentalRequest);

    boolean processReturn(String rentalId, ReturnRequest returnRequest);

    Optional<Rental> getRentalById(String rentalId);

    List<Rental> getRentalsByUser(String userId);

    Optional<Rental> getCurrentRentalByBike(String bikeId);

    List<Rental> getRentalsByTenant(String tenantId);
    public List<Rental> getCompletedRentalRecords();
}
