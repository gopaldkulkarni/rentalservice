package schedulers;


import events.Event;
import events.EventPublisher;
import events.UnpublishedEventException;
import model.Rental;
import service.RentalService;
import util.JSonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This scheduler archives the completed Rental data. It simply publishes to Kafka. From there the events should be added to Cassandra
 */
@Component
public class ArchiveRentalsScheduler {
    @Autowired
    RentalService rentalService;

    @Autowired
    EventPublisher eventPublisher;

    @Value("${scheduler.cron.expression.archive}")
    private String cronExpression;

    @Scheduled(cron = "${scheduler.cron.expression.archive}")
    public void scheduledTask() {
        // Your task logic here
        System.out.print("The configured expression is "+ cronExpression);
        System.out.println("Running Archival shedule to Archive Rental data: " + LocalDateTime.now());
        // Perform the required actions for the scheduled task
        List<Rental> completedRentals = rentalService.getCompletedRentalRecords();
        for(Rental r: completedRentals) {
            try {
                eventPublisher.publish(new Event(JSonUtil.toJson(r).getBytes()));
            } catch (UnpublishedEventException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
