package com.bootifulmicropizza.service.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Configuration for the OAuth2 resource server.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "inventory-service";

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/health", "/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**").permitAll()

            .antMatchers(HttpMethod.GET, "/catalogues/**").access("#oauth2.clientHasRole('INVENTORY_READ') or hasRole('ROLE_CUSTOMER')")
            .antMatchers(HttpMethod.GET, "/iantest/**").hasRole("CUSTOMER")
            .antMatchers(HttpMethod.POST, "/catalogues/**").hasAuthority("INVENTORY_WRITE")
            .antMatchers(HttpMethod.PUT, "/catalogues/**").hasAuthority("INVENTORY_WRITE")
            .antMatchers(HttpMethod.DELETE, "/catalogues/**").hasAuthority("INVENTORY_WRITE")

            .antMatchers(HttpMethod.GET, "/products/**").access("#oauth2.clientHasRole('INVENTORY_READ') or hasRole('ROLE_CUSTOMER')")
            .antMatchers(HttpMethod.POST, "/products/**").hasAuthority("INVENTORY_WRITE")
            .antMatchers(HttpMethod.PUT, "/products/**").hasAuthority("INVENTORY_WRITE")
            .antMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("INVENTORY_WRITE")

            .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
        super.configure(resources);
    }
}
