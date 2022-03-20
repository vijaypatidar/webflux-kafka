package com.example.demo.controllers;

import com.example.demo.clients.GithubUserClient;
import com.example.demo.kafka.producer.KafkaTopicConfig;
import com.example.demo.models.GithubUser;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/github")
@Timed("GithubController")
public class GithubController {

    @Autowired
    private GithubUserClient githubUserClient;

    @Autowired
    private KafkaTemplate<String, GithubUser> kafkaTemplate;

    @Autowired
    private WebClient webClient;

    @GetMapping("/{username}")
    public Mono<GithubUser> getUser(@PathVariable String username) throws IOException {
        return githubUserClient.getGithubUser(username)
                .map(user->{
                    kafkaTemplate.send(KafkaTopicConfig.USER_INFO,user);
                    return user;
                });
    }

}
