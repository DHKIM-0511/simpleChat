package com.nrz.redis.demo.global.oauth;

import com.nrz.redis.demo.entity.User;
import com.nrz.redis.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder; 일반 로그인시 패스워드 인코더

    //oauth provider에게 받은 유저 정보를 받은 후 처리
    //함수 종료시 @AuthenticationPrincipal 사용 가능
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("registration id= {}", userRequest.getClientRegistration().getRegistrationId());
        log.info("userId= {}", userRequest.getClientRegistration().getClientId());
        log.info("user name= {}", userRequest.getClientRegistration().getClientName());
        log.info("getAttributes= {}", super.loadUser(userRequest).getAttributes());
        OAuth2User loginUser = super.loadUser(userRequest);

        String id = loginUser.getAttributes().get("sub").toString();
        User user = userRepository.findByProviderId(id) //provider id를 기준으로 찾았을때 없다면 저장 있으면 get
            .orElseGet(() ->
                userRepository.save(User.of(loginUser))
            );

        return new Oauth2UserDetails(user, loginUser.getAttributes());
    }
}
