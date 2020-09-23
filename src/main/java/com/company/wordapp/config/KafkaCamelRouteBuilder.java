package com.company.wordapp.config;

import com.company.wordapp.service.WordService;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class KafkaCamelRouteBuilder extends RouteBuilder {

    private static final String KAFKA_SENTENCE_TOPIC = "kafka:sentence?brokers=localhost:9092";
    private static final String KAFKA_WORD_TOPIC = "kafka:word?brokers=localhost:9092";
    private static final String DIRECT_START = "direct:start";
    
    private final WordService wordService;

    @Override
    public void configure() {
        from(DIRECT_START)
            .to(KAFKA_SENTENCE_TOPIC);
        
        from(KAFKA_WORD_TOPIC)
            .process(wordService::processWords);
    }
}
