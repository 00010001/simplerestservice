package com.example.demo.service;

import com.example.demo.model.GithubUser;
import com.example.demo.model.User;
import com.example.demo.model.exception.NoFollowersException;
import com.google.common.io.Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


class UserServiceTest {

    private static final String TEST_LOGIN = "octocat";

    @InjectMocks
    UserService userService;

    @Mock
    RequestCountRepository requestCountRepository;
    @Mock
    GithubClient githubClient;
    @Mock
    RequestCountService requestCountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetUser() throws IOException {

        when(githubClient.getUser(TEST_LOGIN)).thenReturn(getJsonFromResource("octocat.json"));
        User user = userService.getUser(TEST_LOGIN);

        assertThat(user.getId()).isEqualTo(583231);
        assertThat(user.getLogin()).isEqualTo(TEST_LOGIN);
        assertThat(user.getName()).isEqualTo("The Octocat");
        assertThat(user.getType()).isEqualTo("User");
        assertThat(user.getAvatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/583231?v=4");
        assertThat(LocalDateTime.of(2011, 1, 25, 18, 44, 36)
                .isEqual(user.getCreatedAt())).isTrue();
        assertThat(BigDecimal.valueOf(0.0158898310)).isEqualByComparingTo(user.getCalculations());
    }

    @Test
    void whenGithubUserHasNoFollowersThenShouldThrowNoFollowersException() {
        GithubUser githubUser = new GithubUser();
        githubUser.setLogin(TEST_LOGIN);
        githubUser.setFollowers(0);
        assertThatThrownBy(() -> userService.validateFollowers(githubUser)).isInstanceOf(NoFollowersException.class)
                .hasMessageContaining("No followers (can't do calculations -> divide by zero) for github user: octocat");
    }

    @Test
    void shouldFetchUserFromGithub() throws IOException {

        when(githubClient.getUser(TEST_LOGIN)).thenReturn(getJsonFromResource("octocat.json"));
        GithubUser githubUser = userService.fetchGithubUser(TEST_LOGIN);
        assertThat(githubUser.getLogin()).isEqualTo(TEST_LOGIN);
        assertThat(githubUser.getAvatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/583231?v=4");
        assertThat(githubUser.getName()).isEqualTo("The Octocat");
        assertThat(githubUser.getType()).isEqualTo("User");
        assertThat(githubUser.getPublicRepos()).isEqualTo(8);
        assertThat(LocalDateTime.of(2011, 1, 25, 18, 44, 36)
                .isEqual(githubUser.getCreatedAt())).isTrue();
        assertThat(githubUser.getFollowers()).isEqualTo(3776);
        assertThat(githubUser.getId()).isEqualTo(583231);

    }

    @Test
    void shouldCalculate() {
        assertThat(BigDecimal.valueOf(0.0158898310)).isEqualByComparingTo(userService.calculate(3776, 8));
        assertThat(BigDecimal.valueOf(0.0297909417)).isEqualByComparingTo(userService.calculate(11480, 55));
    }

    private String getJsonFromResource(String resourceName) throws IOException {
        URL url = Resources.getResource(resourceName);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }

}