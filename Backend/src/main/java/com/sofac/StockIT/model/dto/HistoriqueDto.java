package com.sofac.StockIT.model.dto;

import com.sofac.StockIT.model.entity.StatusProduit;
import com.sofac.StockIT.model.entity.TypeAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueDto {
    private Long idHistorique;
    private Long produitId;
    private TypeAction typeAction;
    private String numeroSerie;
    private String oldDestination;
    private String oldMotif;
    private LocalDate oldDateAcquisition;
    private String oldAffectation;
    private StatusProduit oldStatutProduit;
    private LocalDateTime dateAction;

}
