package com.nrz.redis.demo.controller;

import com.nrz.redis.demo.global.oauth.Oauth2UserDetails;
import com.nrz.redis.demo.service.RedisPubSubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisPubSubService redisPubSubService;

    @GetMapping("/chat")
    String chat(@AuthenticationPrincipal Oauth2UserDetails oauth2UserDetails, Model model) {
        model.addAttribute("user", oauth2UserDetails.getUser());
        return "chat";
    }
}
