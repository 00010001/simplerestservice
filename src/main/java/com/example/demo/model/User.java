package com.example.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class User implements Serializable {

    private int id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private BigDecimal calculations;

    public User id(final int id) {
        this.id = id;
        return this;
    }

    public User login(final String login) {
        this.login = login;
        return this;
    }

    public User name(final String name) {
        this.name = name;
        return this;
    }

    public User type(final String type) {
        this.type = type;
        return this;
    }

    public User avatarUrl(final String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public User createdAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public User calculations(final BigDecimal calculations) {
        this.calculations = calculations;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getCalculations() {
        return calculations;
    }

    public void setCalculations(BigDecimal calculations) {
        this.calculations = calculations;
    }
}
