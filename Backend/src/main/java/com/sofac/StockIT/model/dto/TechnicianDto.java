package com.sofac.StockIT.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class TechnicianDto extends UsersDto{
    private String department;

    public boolean canViewProducts() { return true; }
    public boolean canAddProducts() { return true; }
    public boolean canEditProducts() { return false; }
    public boolean canDeleteProducts() { return false; }
}
