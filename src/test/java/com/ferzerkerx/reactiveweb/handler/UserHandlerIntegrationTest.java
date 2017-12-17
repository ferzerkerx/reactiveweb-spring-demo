package com.ferzerkerx.reactiveweb.handler;

import com.ferzerkerx.reactiveweb.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserHandlerIntegrationTest {

    @Autowired
    @Nonnull
    private WebTestClient webTestClient;

    @Test
    public void testFindUserReactively() {
        FluxExchangeResult<User> result = webTestClient.get().uri("/api/user").accept(MediaType.APPLICATION_JSON)
                .exchange().returnResult(User.class);
        assertThat(result.getStatus().value(), is(200));
        List<User> users = result.getResponseBody().collectList().block();

        assertNotNull(users);
        assertThat(users.size(), is(2));
        assertThat(users.get(0).getUserName(), is("User1"));
    }

    @Test
    public void testFindUserImperatively() {
        User user = webTestClient.get().uri("/api/user/1")
                .accept(MediaType.APPLICATION_JSON).exchange().returnResult(User.class).getResponseBody().blockFirst();
        assertNotNull(user);
        assertThat(user.getId(), is(1L));
        assertThat(user.getUserName(), is("User1"));
    }

    @Test
    public void testUserNotFound() {
        webTestClient.get().uri("/api/user/10").accept(MediaType.APPLICATION_JSON).exchange().expectStatus()
                .isNotFound();
    }

}