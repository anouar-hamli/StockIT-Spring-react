package com.sofac.StockIT.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProduitUserAction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitUserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeAction", nullable = false, length = 50)
    private TypeAction typeAction;

    @Column(name = "dateAction", nullable = false)
    private LocalDateTime dateAction = LocalDateTime.now();
}
