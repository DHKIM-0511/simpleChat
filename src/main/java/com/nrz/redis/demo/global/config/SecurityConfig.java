package com.nrz.redis.demo.global.config;

//import com.nrz.oauth.demo.service.OAuth2UserService;
import com.nrz.redis.demo.global.oauth.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final Oauth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> {}) // 기본 CORS 설정 활성화
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN") //admin 가능 경로
                .requestMatchers("/", "login").permitAll() //login 전 가능 페이지
                .anyRequest().authenticated() // 그 이외는 인증 필요
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/") // 비로그인시 이동할 page 설정 (== login page)
                .defaultSuccessUrl("/chat") //login인 성공 후 이동 경로
                .userInfoEndpoint(userInfo -> userInfo // login 후 후처리
                    .userService(oAuth2UserService)
                )
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));


        return http.build();
    }
}