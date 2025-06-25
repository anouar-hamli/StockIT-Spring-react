package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.ProduitUserAction;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoProduitUserAction extends JpaRepository<ProduitUserAction, Long>  {

    @Query("""
        SELECT a FROM ProduitUserAction a
        JOIN FETCH a.produit
        JOIN FETCH a.user
        WHERE a.produit.numeroSerie = :serialNumber
    """)
    List<ProduitUserAction> findByProductSerialNumber(@Param("serialNumber") String serialNumber);

    @Query("""
        SELECT a FROM ProduitUserAction a
        JOIN FETCH a.produit
        JOIN FETCH a.user
        WHERE a.user.nom = :userName AND a.produit.numeroSerie = :serialNumber
    """)
    List<ProduitUserAction> findByUserNameAndSerialNumber(
            @Param("userName") String userName,
            @Param("serialNumber") String serialNumber);

    @Query("""
        SELECT a FROM ProduitUserAction a
        JOIN FETCH a.produit
        JOIN FETCH a.user
        WHERE a.user.nom = :userName
          AND a.produit.numeroSerie = :serialNumber
          AND a.typeAction = :actionType
    """)
    List<ProduitUserAction> findByUserNameAndSerialNumberAndActionType(
            @Param("userName") String userName,
            @Param("serialNumber") String serialNumber,
            @Param("actionType") TypeAction actionType);
}


