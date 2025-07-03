package com.sofac.StockIT.model.dto;

import com.sofac.StockIT.model.entity.TypeAction;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProduitUserActionDto {
    private Long id;
    private ProduitDto produit;
    private UsersDto user;
    private TypeAction typeAction;
    private LocalDateTime dateAction;
    private String description;

}
