package com.nrz.redis.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrz.redis.demo.dto.ChatMessageDto;
import com.nrz.redis.demo.service.RedisPubSubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebsocketController {
    private final RedisPubSubService pubSubService;

    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable int roomId, ChatMessageDto message) throws JsonProcessingException {
        message.setRoomId(roomId);
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = objectMapper.writeValueAsString(message);

        log.info("pub room ID: {}", message.getRoomId());
        log.info("pub message: {}", message.getMessage());
        log.info("pub email: {}", message.getSenderEmail());
        pubSubService.publish("room"+roomId, msg);
    }
}
