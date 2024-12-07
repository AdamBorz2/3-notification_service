package com.itm.space.notificationservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "spring.kafka.topic")
@Getter
@Setter
public class KafkaProperties {
    private String notificationEvents;
}
