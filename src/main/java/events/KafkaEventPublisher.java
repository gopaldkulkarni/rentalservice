package events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * Uses a backup file store. On fail to publish, the record will be put into filestore for reprocessing.
 */
public class KafkaEventPublisher implements EventPublisher {



    @Override
    @Async
    public void publish(Event eventData) throws UnpublishedEventException {
        //TODO Remove SOP and add Logger

        try {
            System.out.println("Publishing to Kafka");
        } catch (Exception e) {
            //Unable to publish. Store in a file for retrying.
            throw new UnpublishedEventException("Unable to publish the event");
        }
    }
}
