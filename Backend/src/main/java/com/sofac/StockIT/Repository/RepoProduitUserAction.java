package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.ProduitUserAction;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoProduitUserAction extends JpaRepository<ProduitUserAction, Long> {
//    List<ProduitUserAction> findByProduitId(Long produitId);
//    List<ProduitUserAction> findByUser(Users user);
//    List<ProduitUserAction> findByTypeAction(TypeAction typeAction);
//    List<ProduitUserAction> findByProduit_Numero_serie(String numeroSerie);
}
