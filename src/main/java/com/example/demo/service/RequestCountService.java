package com.example.demo.service;

import com.example.demo.model.RequestCountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RequestCountService {

    private final Logger logger = LoggerFactory.getLogger(RequestCountService.class);
    private final RequestCountRepository requestCountRepository;

    @Autowired
    public RequestCountService(RequestCountRepository requestCountRepository) {
        this.requestCountRepository = requestCountRepository;
    }

    @Transactional
    public void increment(String login) {
        var requestCountEntity = requestCountRepository.findByLoginIgnoreCase(login);
        if (requestCountEntity != null) {
            requestCountEntity.setRequestCount(requestCountEntity.getRequestCount() + 1);
            logger.info("Increasing request count for login [{}], current count [{}]", login, requestCountEntity.getRequestCount());
        } else {
            requestCountEntity = new RequestCountEntity(login, 1);
            logger.info("Creating new RequestCountEntity for login [{}], current count [1]", login);
        }
        requestCountRepository.save(requestCountEntity);
    }
}
