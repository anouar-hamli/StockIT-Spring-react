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
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class,
        uses = DateMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UsersMapper {

    // Entity → DTO
    @SubclassMapping(source = Admins.class, target = AdminsDto.class)
    @SubclassMapping(source = Technician.class, target = TechnicianDto.class)
    UsersDto toDto(Users entity);

    @Mapping(target = "motDePasse", ignore = true)
    AdminsDto toAdminsDto(Admins entity);

    @Mapping(target = "motDePasse", ignore = true)
    TechnicianDto toTechnicianDto(Technician entity);

    // DTO → Entity
    @SubclassMapping(source = AdminsDto.class, target = Admins.class)
    @SubclassMapping(source = TechnicianDto.class, target = Technician.class)
    Users toEntity(UsersDto dto);

    // Required mapping methods
    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "dateAction", ignore = true)
    Admins toAdminsEntity(AdminsDto dto);

    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "dateAction", ignore = true)
    Technician toTechnicianEntity(TechnicianDto dto);

    // Update methods
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "dateAction", ignore = true)
    @Mapping(target = "motDePasse", ignore = true)
    void updateFromDto(UsersDto dto, @MappingTarget Users entity);
}
