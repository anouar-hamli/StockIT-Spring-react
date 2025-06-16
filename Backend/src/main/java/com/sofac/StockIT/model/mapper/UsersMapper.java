package com.sofac.StockIT.model.mapper;

import com.sofac.StockIT.config.MapStructConfig;
import com.sofac.StockIT.model.dto.AdminsDto;
import com.sofac.StockIT.model.dto.ProduitUserActionDto;
import com.sofac.StockIT.model.dto.TechnicianDto;
import com.sofac.StockIT.model.dto.UsersDto;
import com.sofac.StockIT.model.entity.Admins;
import com.sofac.StockIT.model.entity.ProduitUserAction;
import com.sofac.StockIT.model.entity.Technician;
import com.sofac.StockIT.model.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class, uses = {DateMapper.class})
public interface UsersMapper {
    @SubclassMapping(source = Admins.class, target = AdminsDto.class)
    @SubclassMapping(source = Technician.class, target = TechnicianDto.class)
    UsersDto toDto(Users entity);

    @SubclassMapping(source = AdminsDto.class, target = Admins.class)
    @SubclassMapping(source = TechnicianDto.class, target = Technician.class)
    Users toEntity(UsersDto dto);

    @Mapping(target = "motDePasse", ignore = true)
    AdminsDto toAdminsDto(Admins entity);   
}
