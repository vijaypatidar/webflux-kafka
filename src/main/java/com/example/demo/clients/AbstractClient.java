package com.example.demo.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public abstract class AbstractClient {

    @Autowired
    protected WebClient webClient;


    protected <T> Mono<T> getMono(String url, Class<T> clazz) {
        return webClient.get()
                .uri(getAbsoluteUrl(url))
                .headers(getHeadersConsumer())
                .retrieve()
                .bodyToMono(clazz);
    }

    private Consumer<HttpHeaders> getHeadersConsumer() {
        return httpHeaders -> {

        };
    }


    protected <T> Flux<T> getFlux(String url, Class<T> clazz) {
        return webClient.get()
                .uri(getAbsoluteUrl(url))
                .headers(getHeadersConsumer())
                .retrieve()
                .bodyToFlux(clazz);
    }

    protected <T> Flux<T> postFlux(String url, Class<T> clazz, Object body) {
        return webClient.post()
                .uri(getAbsoluteUrl(url))
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(clazz);
    }


    private String getAbsoluteUrl(String relativeUrl) {
        return baseUrl() + relativeUrl;
    }

    protected abstract String baseUrl();
}
