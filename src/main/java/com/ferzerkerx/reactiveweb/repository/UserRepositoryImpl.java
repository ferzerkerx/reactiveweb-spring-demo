package com.ferzerkerx.reactiveweb.repository;


import com.ferzerkerx.reactiveweb.model.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Nonnull
    private static final List<User> USERS = createUsers();

    @Nonnull
    @Override
    public Mono<User> getUserById(String id) {
        return Mono.justOrEmpty(
                USERS.stream()
                        .filter(user -> user.getId().equals(Long.valueOf(id)))
                        .findFirst().orElse(null)
        );
    }

    @Nonnull
    @Override
    public Flux<User> getUsers() {
        return Flux.fromIterable(USERS);
    }

    @Nonnull
    private static List<User> createUsers() {
        return Arrays.asList(
                new User(1L, "User1"),
                new User(2L, "User2")
        );
    }
}
