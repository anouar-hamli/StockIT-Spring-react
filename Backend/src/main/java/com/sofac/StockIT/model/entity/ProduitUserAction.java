package com.sofac.StockIT.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Builder
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
    @PrePersist
    public void prePersist() {
        if (this.dateAction == null) {
            this.dateAction = LocalDateTime.now();
        }
    }

    @Column(name = "description", length = 255)
    private String description;

}
