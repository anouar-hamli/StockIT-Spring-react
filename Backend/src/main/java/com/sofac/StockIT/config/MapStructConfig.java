package com.sofac.StockIT.config;

import com.sofac.StockIT.model.mapper.DateMapper;
import com.sofac.StockIT.model.mapper.EnumMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
       // uses = {DateMapper.class, EnumMapper.class}
)
public interface MapStructConfig {


}
