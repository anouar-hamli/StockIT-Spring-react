package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoProduit extends JpaRepository<Produit, Long> {
}
