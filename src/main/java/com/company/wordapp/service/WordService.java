package com.company.wordapp.service;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class WordService extends BasicService {

    private static final String SPACE = " ";
    
    public void processWords(Exchange exchange) {
        LocalDateTime dateTime = LocalDateTime.now().withSecond(0).withNano(0);
        String sentence = (String) exchange.getMessage().getBody();

        if (getWordMap().containsKey(dateTime)) {
            sentence = getWordMap().get(dateTime) + SPACE + exchange.getMessage().getBody();
        }
        getWordMap().put(dateTime, sentence);
    }
}
