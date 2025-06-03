package com.sofac.StockIT.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "historique")
public class Historique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistorique")
    private long idHistorique;

    @ManyToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id_produit")
    private Produit produit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_action", nullable = false)
    private TypeAction typeAction;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "old_destination")
    private String oldDestination;

    @Column(name = "old_motif")
    private String oldMotif;

    @Column(name = "old_date_acquisition")
    private LocalDate oldDateAcquisition;

    @Column(name = "old_affectation")
    private String oldAffectation;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_statut_produit")
    private StatusProduit oldStatutProduit;

    @Column(name = "action_by", nullable = false)
    private String actionBy;

    @Column(name = "date_action")
    private LocalDateTime dateAction;
}
