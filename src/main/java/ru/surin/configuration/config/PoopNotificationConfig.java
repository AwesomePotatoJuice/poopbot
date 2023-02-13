package ru.surin.configuration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "poopnotification")
public class PoopNotificationConfig {
    private String chatFile;
}
