package com.example.demo.services.impls;

import com.example.demo.models.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserServiceImpl implements UserService {
    private static final Map<String,User> userMap = new HashMap<>();
    @NotNull
    @Override
    public Mono<User> createUser(@NotNull User user) {
        userMap.put(user.getUsername(),user);
        return Mono.just(user);
    }

    @NotNull
    @Override
    public Mono<User> updateUser(@NotNull User user) {
        return createUser(user);
    }

    @NotNull
    @Override
    public Mono<User> getUser(@NotNull String username) {
        return Mono.create(sink->{
            User user = userMap.get(username);
            if (user!=null) sink.success(user);
            else
                sink.error(new UserNotFoundException(username));
        });
    }
}
