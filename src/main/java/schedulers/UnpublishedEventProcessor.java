package schedulers;

import events.Event;
import events.EventPublisher;
import events.UnpublishedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import repository.UnpublishedEventRepository;
import service.RentalService;

import java.util.List;

@Component
public class UnpublishedEventProcessor {
    @Autowired
    EventPublisher eventPublisher;

    @Autowired
    UnpublishedEventRepository unpublishedEventRepository;
    public UnpublishedEventProcessor(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Scheduled(fixedDelay = 60000) // Process every minute (adjust as needed)
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
