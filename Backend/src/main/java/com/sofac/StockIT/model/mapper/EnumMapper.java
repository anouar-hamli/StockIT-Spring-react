package com.sofac.StockIT.model.mapper;

import com.sofac.StockIT.config.MapStructConfig;
import com.sofac.StockIT.model.entity.Category;
import com.sofac.StockIT.model.entity.StatusProduit;
import com.sofac.StockIT.model.entity.TypeAction;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface EnumMapper {
    TypeAction map(TypeAction typeAction);
    StatusProduit map(StatusProduit statusProduit);
    Category map(Category category);

}
