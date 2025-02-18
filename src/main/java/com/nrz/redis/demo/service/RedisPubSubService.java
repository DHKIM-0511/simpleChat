package com.nrz.redis.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrz.redis.demo.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPubSubService implements MessageListener {
    private final StringRedisTemplate stringRedisTemplate;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public void publish(String channel, String msg){
        stringRedisTemplate.convertAndSend(channel, msg);
    }

    @Override
    public void onMessage(Message msg, byte[] pattern){
        String payload = new String(msg.getBody());
        ObjectMapper om = new ObjectMapper();
        try{
            ChatMessageDto dto = om.readValue(payload, ChatMessageDto.class);
            log.info("sub room ID: {}", dto.getRoomId());
            log.info("sub message: {}", dto.getMessage());
            log.info("sub email: {}", dto.getSenderEmail());
            simpMessageSendingOperations.convertAndSend("/topic/"+dto.getRoomId(), dto);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
