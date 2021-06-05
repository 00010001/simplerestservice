package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class RequestCountEntity {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "REQUEST_COUNT")
    private int requestCount;

    public RequestCountEntity() {
    }

    public RequestCountEntity(String login, int requestCount) {
        this.login = login;
        this.requestCount = requestCount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }
}
