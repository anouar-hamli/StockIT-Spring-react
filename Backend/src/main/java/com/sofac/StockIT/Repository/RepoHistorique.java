package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Historique;
import com.sofac.StockIT.model.entity.TypeAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoHistorique extends JpaRepository<Historique, Long> {
    @Query("SELECT h FROM Historique h WHERE h.produit.idproduit = :produitId")
    List<Historique> findByProduitId(@Param("produitId") Long produitId);
    List<Historique> findByProduitIdproduitOrderByDateActionDesc(Long idproduit);
    //List<Historique> findByTypeAction(TypeAction typeAction);
    //List<Historique> findByActionBy(String username);
}

