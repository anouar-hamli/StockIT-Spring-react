package com.sofac.StockIT.model;

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
    private long produit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_materiel", nullable = false)
    private Category type_materiel;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numero_serie;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "motif",nullable = false)
    private String motif;

    @Column(name = "date_acquisition", nullable = false)
    private String date_acquisition;

    @Column(name = "affectation", nullable = false)
    private String affectation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_produit", nullable = false)
    private StatusProduit statut_produit;

    @Column(name = "is_deleted",nullable = false)
    private Boolean is_deleted=false;

    @OneToMany(mappedBy = "produit",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Historique> historiques = new ArrayList<>();

    public void addHistorique(Historique historique) {
        this.historiques.add(historique);
        historique.setProduit(this);
    }
    public void sauvegarderHistorique(TypeAction typeAction,String actionBy){
        Historique historique = new Historique();
        historique.setProduit(this);
        historique.setTypeAction(typeAction);
        historique.setSerialNumber(this.numero_serie);
        historique.setOldDestination(this.destination);
        historique.setOldMotif(this.motif);
        historique.setOldDateAcquisition(LocalDate.parse(this.date_acquisition));
        historique.setOldAffectation(this.affectation);
        historique.setOldStatutProduit(this.statut_produit);
        historique.setActionBy(actionBy);
        historique.setDateAction(LocalDateTime.now());

        this.addHistorique(historique);
    }
    public void softDeleted() {
        this.is_deleted=true;
        this.sauvegarderHistorique(TypeAction.DELETE,"system");
    }
}
