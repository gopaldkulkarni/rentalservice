package application;

import events.EventPublisher;
import events.KafkaEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import service.DefaultRentalService;
import service.RentalService;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public EventPublisher eventPublisher() {
        return new KafkaEventPublisher();
    }

}
