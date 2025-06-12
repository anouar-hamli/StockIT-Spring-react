package com.sofac.StockIT.model.mapper;

import com.sofac.StockIT.config.MapStructConfig;
import com.sofac.StockIT.model.dto.ProduitUserActionDto;
import com.sofac.StockIT.model.entity.ProduitUserAction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class,
        uses = {ProduitMapper.class, UsersMapper.class, EnumMapper.class, DateMapper.class})
public interface ProduitUserActionMapper {
    ProduitUserActionDto toDto(ProduitUserAction entity);

    @Mapping(target = "produit.historiques", ignore = true)
    @Mapping(target = "user.motDePasse", ignore = true)
    ProduitUserAction toEntity(ProduitUserActionDto dto);
}
