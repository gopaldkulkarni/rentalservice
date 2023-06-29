package service;

import events.Event;
import events.EventPublisher;
import events.UnpublishedEvent;
import events.UnpublishedEventException;
import model.Rental;
import model.RentalRequest;
import model.ReturnRequest;
import repository.RentalRepository;
import repository.UnpublishedEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultRentalService implements RentalService {

    private final RentalRepository rentalRepository;
    private final UnpublishedEventRepository unpublishedEventRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    public DefaultRentalService(RentalRepository rentalRepository,
                                UnpublishedEventRepository unpublishedEventRepository,
                                EventPublisher eventPublisher) {
        this.rentalRepository = rentalRepository;
        this.unpublishedEventRepository = unpublishedEventRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean isBikeAvailable(Long bikeId) {
        // Implementation to check bike availability
        // ...
        return true;
    }

    @Override
    public void startRental(String tenantId, RentalRequest rentalRequest) {
        Rental rental = new Rental();
        rental.setRentalId(UUID.randomUUID().toString());
        rental.setUserId(rentalRequest.getUserId());
        rental.setBikeId(rentalRequest.getBikeId());
        rental.setTenantId(tenantId);
        // Set other rental properties as needed

        rentalRepository.save(rental);

        Event event = new Event(("Rental" + rental.getRentalId() + " started!").getBytes());
        try {
            eventPublisher.publish(event);
        } catch (UnpublishedEventException e) {
            UnpublishedEvent unpublishedEvent = new UnpublishedEvent(event.getEventData());
            unpublishedEventRepository.saveUnpublishedEvent(unpublishedEvent);
        }
    }

    @Override
    public boolean processReturn(String rentalId, ReturnRequest returnRequest) {
        Optional<Rental> optionalRental = rentalRepository.findByRentalId(rentalId);

        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            if (rental.getEndTime() == null) {
                LocalDateTime endTime = LocalDateTime.now();
                rental.setEndTime(endTime);
                // Set other return details like return location, damages, etc.
                // Calculate rental cost if necessary
                rentalRepository.save(rental);

                Event event = new Event(("Rental" + rentalId + " Return Requested!").getBytes());
                try {
                    eventPublisher.publish(event);
                } catch (Exception e) {
                    UnpublishedEvent unpublishedEvent = new UnpublishedEvent(event.getEventData());
                    unpublishedEventRepository.saveUnpublishedEvent(unpublishedEvent);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Rental> getRentalById(String rentalId) {
        return rentalRepository.findByRentalId(rentalId);
    }

    @Override
    public List<Rental> getRentalsByUser(String userId) {
        return rentalRepository.findByUserId(userId);
    }

    @Override
    public Optional<Rental> getCurrentRentalByBike(String bikeId) {
        return rentalRepository.findFirstByBikeIdAndEndTimeIsNull(bikeId);
    }

    @Override
    public List<Rental> getRentalsByTenant(String tenantId) {
        return rentalRepository.findByTenantId(tenantId);
    }
    @Override
    public List<Rental> getCompletedRentalRecords() {
        //TODO handle null by using optional
        List<Rental> res = rentalRepository.findAllCompletedOrCancelledRentals();
        return res;
    }
    public void processUnpublishedEvents() {
        List<UnpublishedEvent> unpublishedEvents = unpublishedEventRepository.findAllUnpublishedEvents();
        for (UnpublishedEvent unpublishedEvent : unpublishedEvents) {
            try {
                Event event = new Event(unpublishedEvent.getEventData());
                eventPublisher.publish(event);
                unpublishedEventRepository.deleteUnpublishedEvent(unpublishedEvent);
            } catch (Exception e) {
                // Event publishing failed again, handle the error or retry later
            }
        }
    }
}
