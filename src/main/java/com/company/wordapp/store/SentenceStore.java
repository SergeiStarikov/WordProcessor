package com.company.wordapp.store;

import lombok.Getter;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class SentenceStore {
    private Map<LocalDateTime, String> words = new HashMap<>();
}
