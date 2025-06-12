package com.sofac.StockIT.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class AdminsDto extends UsersDto{
    private boolean isSuperAdmin;

    public boolean canViewProducts() { return true; }
    public boolean canAddProducts() { return true; }
    public boolean canEditProducts() { return true; }
    public boolean canDeleteProducts() { return true; }
}
