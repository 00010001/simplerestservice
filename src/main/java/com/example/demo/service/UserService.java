package com.example.demo.service;

import com.example.demo.model.GithubUser;
import com.example.demo.model.User;
import com.example.demo.model.exception.NoFollowersException;
import com.example.demo.model.exception.NotReadableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class UserService {

    private final ObjectMapper objectMapper;
    private final GithubClient githubClient;
    private final RequestCountService requestCountService;

    @Autowired
    public UserService(GithubClient githubClient, RequestCountService requestCountService) {
        this.githubClient = githubClient;
        this.requestCountService = requestCountService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public User getUser(String login) {
        requestCountService.increment(login);
        var githubUser = fetchGithubUser(login);
        validateFollowers(githubUser);
        return new User()
                .id(githubUser.getId())
                .login(githubUser.getLogin())
                .name(githubUser.getName())
                .type(githubUser.getType())
                .avatarUrl(githubUser.getAvatarUrl())
                .createdAt(githubUser.getCreatedAt())
                .calculations(calculate(githubUser.getFollowers(), githubUser.getPublicRepos()));
    }

    GithubUser fetchGithubUser(String login) {
        try {
            return objectMapper.readValue(githubClient.getUser(login), GithubUser.class);
        } catch (JsonProcessingException e) {
            throw new NotReadableException("JSON parse error: " + e.getOriginalMessage(), e);
        }
    }

    void validateFollowers(GithubUser githubUser) {
        if (githubUser.getFollowers() == 0) {
            throw new NoFollowersException("No followers (can't do calculations -> divide by zero) for github user: "
                    + githubUser.getLogin());
        }
    }

    BigDecimal calculate(int followers, int publicRepos) {
        return BigDecimal.valueOf(6).divide(BigDecimal.valueOf(followers), 10, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(2).add(BigDecimal.valueOf(publicRepos)));
    }

}
