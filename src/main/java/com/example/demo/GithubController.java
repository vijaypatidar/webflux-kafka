package com.example.demo;

import com.example.demo.clients.GithubUserClient;
import com.example.demo.kafka.producer.KafkaTopicConfig;
import com.example.demo.models.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class GithubController {

    @Autowired
    private GithubUserClient githubUserClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private WebClient webClient;

    @GetMapping("/{username}")
    public Mono<GithubUser> getUser(@PathVariable String username) throws IOException {
        kafkaTemplate.send(KafkaTopicConfig.USER_INFO,username);
        return githubUserClient.getGithubUser(username);
    }
}
