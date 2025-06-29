package com.sofac.StockIT.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class AdminsDto extends UsersDto{
    private boolean isSuperAdmin;

    private boolean canAccessAdminPanel = true;
}
