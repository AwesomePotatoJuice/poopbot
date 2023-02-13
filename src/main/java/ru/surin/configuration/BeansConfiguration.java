package ru.surin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.surin.configuration.config.PoopNotificationConfig;

import java.io.FileNotFoundException;
import java.util.Set;

import static ru.surin.util.FileHelper.readLongsFromFile;

@Configuration
public class BeansConfiguration {

    @Autowired
    PoopNotificationConfig poopNotificationConfig;

    @Bean
    Set<Long> chats() {
        try {
            return readLongsFromFile(poopNotificationConfig.getChatFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
