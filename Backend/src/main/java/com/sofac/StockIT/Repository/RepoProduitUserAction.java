package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.ProduitUserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoProduitUserAction extends JpaRepository<ProduitUserAction, Long> {
}
