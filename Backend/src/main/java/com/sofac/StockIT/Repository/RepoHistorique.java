package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Historique;
import com.sofac.StockIT.model.entity.TypeAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoHistorique extends JpaRepository<Historique, Long> {
//    List<Historique> findByProduitId(Long produitId);
//    List<Historique> findBySerialNumber(String serialNumber);
//    List<Historique> findByTypeAction(TypeAction typeAction);
//    List<Historique> findByActionBy(String username);
}
