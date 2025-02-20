package com.nrz.redis.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String providerId;

    String name;

    String image;

    String role;

    public static User of(OAuth2User loginUser) {

        return User.builder()
            .providerId(loginUser.getAttribute("sub"))
            .name(loginUser.getAttribute("name"))
            .image(loginUser.getAttribute("picture"))
            .role("ROLE_USER")
            .build();
    }

    //여러 곳 소셜로그인 시 필요
//    String provider;
}
