package com.sofac.StockIT.model.dto;

import com.sofac.StockIT.model.entity.Category;
import com.sofac.StockIT.model.entity.StatusProduit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDto {
    private Long idproduit;
    private Category typeMateriel;
    private String reference;
    private String numeroSerie;
    private String destination;
    private String motif;
    private LocalDate dateAcquisition;
    private String affectation;
    private StatusProduit statutProduit;
    private Boolean isDeleted;
    private List<HistoriqueDto> historiques;
    private String fullReference;

//    public String getFullReference() {
//        return typeMateriel + "-" + reference;
//    }

}
