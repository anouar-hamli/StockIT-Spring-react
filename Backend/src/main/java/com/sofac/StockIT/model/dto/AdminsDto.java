package com.sofac.StockIT.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("admin")
public class AdminsDto extends UsersDto{
    private boolean isSuperAdmin;

    private boolean canAccessAdminPanel = true;
}
