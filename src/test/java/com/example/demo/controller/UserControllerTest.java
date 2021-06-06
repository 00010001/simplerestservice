package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenLoginIsNullThenShouldThrowException() {
        assertThatThrownBy(() -> userController.getUser(null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Login name cannot be null");
    }

    @Test
    void whenLoginIsEmptyThenShouldThrowException() {
        assertThatThrownBy(() -> userController.getUser("")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Login name cannot be empty");
    }

    @Test
    void loginPassedToServiceShouldBeTrimmedAndLowercase() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        userController.getUser(" octoCAT ");
        verify(userService).getUser(captor.capture());
        assertThat(captor.getValue()).isEqualTo("octocat");
    }
}