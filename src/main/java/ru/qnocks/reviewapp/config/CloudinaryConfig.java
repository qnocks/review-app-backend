package ru.qnocks.reviewapp.config;

import com.cloudinary.Cloudinary;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.cloudinary")
public class CloudinaryConfig {

    private String cloudName;

    private String apiKey;

    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> props = new HashMap<>();
        props.put("cloud_name", cloudName);
        props.put("api_key", apiKey);
        props.put("api_secret", apiSecret);

        return new Cloudinary(props);
    }
}
