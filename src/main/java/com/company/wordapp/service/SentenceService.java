package com.company.wordapp.service;

import com.company.wordapp.config.KafkaCamelRouteBuilder;
import com.company.wordapp.store.SentenceStore;
import lombok.AllArgsConstructor;
import org.apache.camel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SentenceService extends BasicService {
    
    public static final String DIRECT_START = "direct:start";

    @Autowired
    CamelContext camelContext;

    @Autowired
    KafkaCamelRouteBuilder kafkaCamelRouteBuilder;

    @Autowired
    SentenceStore sentenceStore;

    @EndpointInject(uri = DIRECT_START)
    ProducerTemplate kafkaProducer;

    @PostConstruct
    public void setup() {
        try {
            camelContext.addRoutes(kafkaCamelRouteBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Scheduled(cron = "${cronjob.exp.sentenceService}")
    public void sendSentences() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        String current = getWordMap().get(now);
        
        getWordMap().entrySet()
            .stream()
            .filter(item -> item.getKey().compareTo(now) < 0)
            .forEach(item -> kafkaProducer.sendBody(item.getValue()));
        
        getWordMap().clear();
        
        if (current != null) {
            getWordMap().put(now, current);
        }
    }
    
    
    
}
