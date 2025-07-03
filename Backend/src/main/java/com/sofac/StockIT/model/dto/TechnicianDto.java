package com.sofac.StockIT.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@JsonTypeName("technician")
public class TechnicianDto extends UsersDto{
    private String department;

    private boolean canAccessAdminPanel = false;
}
