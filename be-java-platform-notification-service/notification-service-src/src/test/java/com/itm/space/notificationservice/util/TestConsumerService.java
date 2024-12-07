package com.itm.space.notificationservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Service
@RequiredArgsConstructor
public class TestConsumerService {

    private final ConsumerFactory<String, byte[]> consumerFactory;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @SneakyThrows
    public void consumeAndValidate(String topic, byte[] event, String... ignoredFields) {
        try (Consumer<String, byte[]> consumer = consumerFactory.createConsumer(groupId, null)) {
            consumer.subscribe(Collections.singleton(topic));
            consumer.poll(Duration.ofSeconds(10));
            consumer.seekToBeginning(consumer.assignment());

            ConsumerRecords<String, byte[]> events = consumer.poll(Duration.ofSeconds(10));

            assertThat(events)
                    .hasSizeGreaterThan(0)
                    .extracting(ConsumerRecord::value)
                    .anySatisfy(actualEvent -> {
                Map<String, Object> expectedMap = objectMapper.readValue(event, Map.class);
                Map<String, Object> actualMap = objectMapper.readValue((byte[]) actualEvent, Map.class);

                if (ignoredFields != null) {
                    for (String field : ignoredFields) {
                        expectedMap.remove(field);
                        actualMap.remove(field);
                    }
                }

                assertThat(actualMap).isEqualTo(expectedMap);
            });
        }
    }
}