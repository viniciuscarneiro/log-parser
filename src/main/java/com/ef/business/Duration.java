package com.ef.business;

import java.time.LocalDateTime;
import java.util.Map;

public interface Duration {
    Map<String, LocalDateTime> getDateRange(LocalDateTime date);
}
