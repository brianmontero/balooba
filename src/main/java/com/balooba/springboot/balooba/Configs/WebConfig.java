package com.balooba.springboot.balooba.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${client.domain.url}")
    private String CLIENT_DOMAIN_URL;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(CLIENT_DOMAIN_URL)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
