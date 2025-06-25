package com.sofac.StockIT.service;

import com.sofac.StockIT.Repository.RepoHistorique;
import com.sofac.StockIT.Repository.RepoProduit;
import com.sofac.StockIT.model.dto.HistoriqueDto;
import com.sofac.StockIT.model.entity.Historique;
import com.sofac.StockIT.model.entity.Produit;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.model.mapper.HistoriqueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class HistoriqueService {
    private final RepoHistorique repoHistorique;
    private final HistoriqueMapper historiqueMapper;

    @Transactional
    public void createHistorique(Produit produit, TypeAction typeAction, String actionBy) {
        HistoriqueDto historiqueDto = new HistoriqueDto();
        historiqueDto.setProduitId(produit.getIdproduit());
        historiqueDto.setTypeAction(typeAction);
        historiqueDto.setNumeroSerie(produit.getNumeroSerie());
        historiqueDto.setDateAction(LocalDateTime.now());

        repoHistorique.save(historiqueMapper.toEntity(historiqueDto));
    }


}
