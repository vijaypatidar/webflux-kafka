package com.example.demo.kafka.consumer;


import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log
public class GithubUsernameTopic {
    @KafkaListener(topics = "USER_INFO", groupId = "g1")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(
                "Received Message: " + message
                        + "from partition: " + partition);
    }
}
