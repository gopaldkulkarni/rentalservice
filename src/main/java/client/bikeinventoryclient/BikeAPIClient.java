package client.bikeinventoryclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BikeAPIClient {
    private final String URL = "http://localhost:8081/bikes/{id}";
    @Autowired
    private RestTemplate restTemplate;
    public boolean isBikeFree(Long id) {
        System.out.print("Retriving the bike info ["+URL+"]"+id);
        ResponseEntity<Bike> res = restTemplate.getForEntity(URL,Bike.class, id);
        if (res.getStatusCode() != HttpStatus.OK) {
            return false;
        }
        Bike b = res.getBody();
        if (b.description == "Available")
            return true;

        return false;
    }
}
