package com.example.demo;


import com.example.demo.clients.GithubUserClient;
import com.example.demo.controllers.GithubController;
import com.example.demo.models.GithubUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GithubController.class)
@Import({GithubUserClient.class})
public class GithubControllerTest {
    @Autowired
    WebTestClient webClient;

    @MockBean
    GithubUserClient githubUserClient;

    @MockBean
    KafkaTemplate<String,String> kafkaTemplate;


    @Test
    public void testSendTopi() {
        GithubUser githubUserExpected = new GithubUser(1,"vijaypatidar","dafdsfgnsdgfjsgbs");

        Mockito.when(githubUserClient.getGithubUser("vijaypatidar")).thenReturn(Mono.just(githubUserExpected));

        Map<String,Object> expected = new HashMap<>();
        expected.put("id",1);
        expected.put("login","vijaypatidar");
        expected.put("node_id","dafdsfgnsdgfjsgbs");

        webClient.get()
                .uri("/vijaypatidar")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .isEqualTo(expected);

        Mockito.verify(githubUserClient,Mockito.times(1)).getGithubUser("vijaypatidar");

    }
    @Test
    public void testSendTopic404() {
        Mockito.when(githubUserClient.getGithubUser("vijaypatidar")).thenReturn(Mono.error(new Exception("User not found")));

        webClient.get()
                .uri("/vijaypatidar")
                .exchange()
                .expectStatus().is5xxServerError();

        Mockito.verify(githubUserClient,Mockito.times(1)).getGithubUser("vijaypatidar");

    }
}
