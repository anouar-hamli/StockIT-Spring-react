package com.sofac.StockIT.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private long idproduit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_materiel", nullable = false)
    private Category typeMateriel;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "motif",nullable = false)
    private String motif;

    @Column(name = "date_acquisition", nullable = false)
    private LocalDate dateAcquisition;

    @Column(name = "affectation", nullable = false)
    private String affectation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_produit", nullable = false)
    private StatusProduit statutProduit;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;


    @OneToMany(mappedBy = "produit",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Historique> historiques = new ArrayList<>();

    public void addHistorique(Historique historique) {
        this.historiques.add(historique);
        historique.setProduit(this);
    }
    public void sauvegarderHistorique(TypeAction typeAction){
        Historique historique = new Historique();
        historique.setProduit(this);
        historique.setTypeAction(typeAction);
        historique.setNumeroSerie(this.numeroSerie);
        historique.setOldDestination(this.destination);
        historique.setOldMotif(this.motif);
        historique.setOldDateAcquisition(this.dateAcquisition);
        historique.setOldAffectation(this.affectation);
        historique.setOldStatutProduit(this.statutProduit);
        historique.setDateAction(LocalDateTime.now());

        this.addHistorique(historique);
    }
    public void softDeleted() {
        this.isDeleted=true;
        this.sauvegarderHistorique(TypeAction.DELETE);
    }
}
