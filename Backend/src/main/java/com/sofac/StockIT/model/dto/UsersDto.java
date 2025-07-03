package com.sofac.StockIT.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminsDto.class, name = "admin"),
        @JsonSubTypes.Type(value = TechnicianDto.class, name = "technician")
})
public class UsersDto {
    private Long idUser;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private LocalDateTime dateAction;
}
