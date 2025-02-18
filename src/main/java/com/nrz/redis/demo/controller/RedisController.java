package com.nrz.redis.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrz.redis.demo.dto.ChatMessageDto;
import com.nrz.redis.demo.service.RedisPubSubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisPubSubService redisPubSubService;

    @PostMapping("/pubsub")
    ResponseEntity<Void> pubsub(@RequestBody ChatMessageDto message) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String msg = om.writeValueAsString(message);
        log.info("pub room ID: {}", message.getRoomId());
        log.info("pub message: {}", message.getMessage());
        log.info("pub email: {}", message.getSenderEmail());
        redisPubSubService.publish("chat", msg);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
