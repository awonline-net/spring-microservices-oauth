# Spring Boot Microservices OAuth2 with Authorization Code grant flow

![Alt text](overview.png?raw=true "OAuth")


To run the example:
- install the provided SSL certificates in your Java cacerts
- start Redis
- start all other services in any order

Optionally:
- generate your own SSL certificates (OAuth Server and Clients)
- generate your own keystore to use encrypted JWT's (OAuthServer and Resource Servers). There is also an example with JWS in place of JWE (see commits in tag [Stateful JWT](https://github.com/awonline-net/spring-microservices-oauth/releases/tag/oauth2_with_stateful_jwt))


## Single Sign On and Sign Out

Redis is used as authentication session store. The use of Redis allows log out from all Clients (Single Sign Out).
There is also a [tag](https://github.com/awonline-net/spring-microservices-oauth/releases/tag/sso_logout_single_client_app) provided which shows how to mix Single Sign On with Sign Out from specific Client (this example doesn't require Redis)

## Why HTTP sessions and JWT's?

JWT's are used for Client communication with Resource server over Spring's OAuthRestTemplate. As mentioned, Client's authentication sessions are stored in Redis.

Such solutions allows easy adaption of other OAuth2 grant flows that are more suitable for eg. single page or mobile apps (put a Gateway API in front of Resource Servers)
