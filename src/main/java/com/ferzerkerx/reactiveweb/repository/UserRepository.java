package com.ferzerkerx.reactiveweb.repository;


import com.ferzerkerx.reactiveweb.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

public interface UserRepository {
    @Nonnull
    Mono<User> getUserById(String id);

    @Nonnull
    Flux<User> getUsers();
}
