package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.Category;
import com.sofac.StockIT.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoProduit extends JpaRepository<Produit, Long> {
    // Find product by serial number
    Produit findByNumeroSerie(String numeroSerie);
    // Find products by category (type_materiel)
    List<Produit> findByTypeMateriel(Category typeMateriel);

    // Find products by category and not deleted
    List<Produit> findByTypeMaterielAndIsDeletedFalse(Category typeMateriel);

    // Find products by serial number containing (partial match)
    List<Produit> findByNumeroSerieContainingIgnoreCase(String partialNumeroSerie);

    // Find products by category and serial number containing
    List<Produit> findByTypeMaterielAndNumeroSerieContainingIgnoreCase(
            Category typeMateriel,
            String partialNumeroSerie
    );


}
