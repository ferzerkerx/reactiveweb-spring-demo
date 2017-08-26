package com.ferzerkerx.reactiveweb.handler;


import com.ferzerkerx.reactiveweb.model.User;
import com.ferzerkerx.reactiveweb.repository.UserRepository;
import com.ferzerkerx.reactiveweb.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Objects;

@Service
public class UserHandler {

    @Nonnull
    private final UserRepository userRepository;

    @Autowired
    public UserHandler(@Nonnull UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Nonnull
    public Mono<ServerResponse> handleGetUsers(@Nonnull ServerRequest ignored) {
        return ServerResponse.ok().body(userRepository.getUsers(), User.class);
    }

    @Nonnull
    public Mono<ServerResponse> handleGetUserById(@Nonnull ServerRequest request) {
        return userRepository.getUserById(request.pathVariable("id"))
                .filter(Objects::nonNull)
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
