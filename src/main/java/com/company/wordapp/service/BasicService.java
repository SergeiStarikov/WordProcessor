package com.company.wordapp.service;

import com.company.wordapp.store.SentenceStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public abstract class BasicService {
    
    @Autowired
    SentenceStore sentenceStore;

    protected Map<LocalDateTime, String> getWordMap() {
        return sentenceStore.getWords();
    }
}
