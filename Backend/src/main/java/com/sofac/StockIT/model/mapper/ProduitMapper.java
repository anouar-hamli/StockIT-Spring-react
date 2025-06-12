package com.sofac.StockIT.model.mapper;

import com.sofac.StockIT.config.MapStructConfig;
import com.sofac.StockIT.model.dto.ProduitDto;
import com.sofac.StockIT.model.entity.Produit;
import org.mapstruct.*;

import java.util.List;


@Mapper(config = MapStructConfig.class,
        uses = {HistoriqueMapper.class, ProduitUserActionMapper.class, EnumMapper.class, DateMapper.class})
public interface ProduitMapper {
    @Mapping(target = "historiques", ignore = true)
    @Mapping(target = "isDeleted", defaultValue = "false")
    Produit toEntity(ProduitDto dto);

    @Mapping(target = "fullReference", expression = "java(entity.getTypeMateriel().name() + \"-\" + entity.getReference())")
    ProduitDto toDto(Produit entity);

}
