# simpleChat

## 프로젝트 개요
simpleChat은 Redis Pub/Sub을 활용한 채팅 서버입니다. OAuth2 및 Redis Session을 통한 로그인과 MySQL JPA 기반 회원 관리를 제공합니다.

## 기술 스택
- **Backend**: Spring Boot, JPA (MySQL), Spring Security
- **Authentication**: OAuth2, Redis Session
- **Messaging**: Redis Pub/Sub, STOMP
- **Frontend**: Thymeleaf

## 특징
- Spring Security, OAuth2 및 Redis Session을 활용한 로그인 인증
- MySQL + JPA 기반 회원 관리
- Redis Pub/Sub + Stomp를 이용한 실시간 채팅 구현
- 서버의 수평 확장을 고려한 아키텍처 설계
