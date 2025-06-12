package com.sofac.StockIT.model.mapper;

import com.sofac.StockIT.config.MapStructConfig;
import com.sofac.StockIT.model.dto.HistoriqueDto;
import com.sofac.StockIT.model.entity.Historique;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class, uses = {EnumMapper.class, DateMapper.class})
public interface HistoriqueMapper {

    @Mapping(target = "produit", ignore = true)
    @Mapping(source = "produitId", target = "produit.idproduit")
    Historique toEntity(HistoriqueDto dto);

    @Mapping(source = "produit.idproduit", target = "produitId")
    HistoriqueDto toDto(Historique entity);

}
