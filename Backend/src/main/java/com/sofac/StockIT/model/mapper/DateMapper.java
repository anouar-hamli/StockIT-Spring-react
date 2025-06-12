package com.sofac.StockIT.model.mapper;

import com.sofac.StockIT.config.MapStructConfig;
import org.mapstruct.Mapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(config = MapStructConfig.class)
public interface DateMapper {
    default LocalDateTime map(LocalDateTime date) {
        return date == null ? LocalDateTime.now() : date;
    }

    default LocalDate map(LocalDate date) {
        return date == null ? LocalDate.now() : date;
    }
}