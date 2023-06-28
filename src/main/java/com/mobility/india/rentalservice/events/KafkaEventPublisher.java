package com.mobility.india.rentalservice.events;

import org.springframework.scheduling.annotation.Async;

import java.util.logging.Logger;

public class KafkaEventPublisher implements EventPublisher {

    @Override
    @Async
    public void publish(Event eventData) {
        //TODO Remove SOP and add Logger


        try {
            System.out.println("Publishing to Kafka");
        } catch (Exception e) {
            //Unable to publish.
            //Do not increment Kafka Ofset. Keep the record in Kafka topic named as "<tenantId_deadletter>
        }
    }
}
