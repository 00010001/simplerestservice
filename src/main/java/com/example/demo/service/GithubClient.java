package com.example.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "github", url = "https://api.github.com/")
public interface GithubClient {

    @GetMapping(value = "/users/{login}", produces = "application/json")
    String getUser(@PathVariable("login") String login);

}
