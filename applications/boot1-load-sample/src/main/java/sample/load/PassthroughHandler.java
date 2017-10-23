package sample.load;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PassthroughHandler {

    private final RestTemplate restTemplate;

    private final String targetHost;

    public PassthroughHandler(
            RestTemplate restTemplate,
            @Value("${loadtarget.host}") String targetHost
    ) {
        this.restTemplate = restTemplate;
        this.targetHost = targetHost;
    }


    public MessageAck handlePassthrough(Message message) {
        ResponseEntity<MessageAck> responseEntity = this.restTemplate.postForEntity(targetHost + "/messages", message, MessageAck.class);
        return responseEntity.getBody();
    }
}
