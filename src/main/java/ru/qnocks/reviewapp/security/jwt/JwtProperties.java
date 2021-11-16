package ru.qnocks.reviewapp.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    public String secret;

    public int expirationTime;
}
