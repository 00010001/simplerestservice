package com.example.demo.service;

import com.example.demo.model.RequestCountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RequestCountServiceTest {

    private static final String TEST_LOGIN = "test";

    @InjectMocks
    RequestCountService requestCountService;

    @Mock
    RequestCountRepository requestCountRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenEntityExistsIncrementRequestCount() {
        RequestCountEntity requestCountEntity = new RequestCountEntity(TEST_LOGIN, 59);
        when(requestCountRepository.findByLoginIgnoreCase(TEST_LOGIN)).thenReturn(requestCountEntity);
        requestCountService.increment(TEST_LOGIN);
        assertThat(requestCountEntity.getRequestCount()).isEqualTo(60);
    }

    @Test
    void whenEntityDoesNotExistCreateOneAndSetRequestCount() {
        requestCountService.increment(TEST_LOGIN);
        ArgumentCaptor<RequestCountEntity> captor = ArgumentCaptor.forClass(RequestCountEntity.class);
        verify(requestCountRepository).save(captor.capture());
        assertThat(captor.getValue().getLogin()).isEqualTo(TEST_LOGIN);
        assertThat(captor.getValue().getRequestCount()).isEqualTo(1);
    }

}