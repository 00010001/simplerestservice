package com.example.demo.service;

import com.example.demo.model.RequestCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.UUID;

@Repository
public interface RequestCountRepository extends JpaRepository<RequestCountEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    RequestCountEntity findByLoginIgnoreCase(String login);

}