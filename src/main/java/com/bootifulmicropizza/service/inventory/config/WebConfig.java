package com.bootifulmicropizza.service.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .anonymous();
    }
}

/* TODO

https://developer.okta.com/blog/2018/04/02/client-creds-with-spring-boot

The above URL details how to create a client and a resource server where no actual user interaction is
required. i.e service-to-service

In the example above, the resource server is a backend service like inventory-service. The client is the 
frontend-for-backend service, website-api-gateway. The gateway is given a specific scope which the resource server
allows access to certain endpoints.  e.g. a scope could be INVENTORY_READ to get products but the client must be
authorised to access the resource server.

https://www.baeldung.com/spring-security-method-security

*/