package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Category;
import com.sofac.StockIT.model.entity.Produit;
import com.sofac.StockIT.model.entity.StatusProduit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoProduit extends JpaRepository<Produit, Long> , JpaSpecificationExecutor<Produit> {
    List<Produit> findByIsDeletedFalse();
    List<Produit> findByTypeMateriel(Category category);
    List<Produit> findByStatutProduit(StatusProduit status);
    Optional<Produit> findByNumeroSerieAndIsDeletedFalse(String numeroSerie);

}
