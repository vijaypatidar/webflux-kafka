package com.example.demo.clients;

import com.example.demo.models.GithubUser;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GithubUserClient extends AbstractClient{

    public Mono<GithubUser> getGithubUser( String username){
        return getMono("users/"+username,GithubUser.class);
    }

    @Override
    protected String baseUrl() {
        return "https://api.github.com/";
    }
}
