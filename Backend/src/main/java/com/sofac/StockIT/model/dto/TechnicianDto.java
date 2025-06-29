package com.sofac.StockIT.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class TechnicianDto extends UsersDto{
    private String department;

    private boolean canAccessAdminPanel = false;
}
