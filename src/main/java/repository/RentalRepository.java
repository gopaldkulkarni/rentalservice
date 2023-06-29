package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByTenantId(String tenantId);

    List<Rental> findByUserId(String userId);

    Optional<Rental> findFirstByBikeIdAndEndTimeIsNull(String bikeId);

    Optional<Rental> findByRentalId(String rentalId);
    @Query("SELECT r FROM Rental r WHERE r.status = 'COMPLETED' OR r.status = 'CANCELLED'")
    List<Rental> findAllCompletedOrCancelledRentals();
}
