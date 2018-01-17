package com.ef.enums;

import java.time.LocalDateTime;
import java.util.List;

public interface Search {

    List<String> executeSearch(LocalDateTime date, Integer threshold);

}
