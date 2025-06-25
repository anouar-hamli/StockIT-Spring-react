package com.sofac.StockIT.service;

import com.sofac.StockIT.Repository.RepoHistorique;
import com.sofac.StockIT.Repository.RepoProduit;
import com.sofac.StockIT.model.dto.HistoriqueDto;
import com.sofac.StockIT.model.dto.ProduitDto;
import com.sofac.StockIT.model.entity.*;
import com.sofac.StockIT.model.mapper.HistoriqueMapper;
import com.sofac.StockIT.model.mapper.ProduitMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.persistence.criteria.Predicate;


import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final RepoProduit produitRepository;
    private final ProduitMapper produitMapper;
    private final RepoHistorique historiqueRepository;
    private final HistoriqueMapper historiqueMapper;


    @Transactional(readOnly = true)
    public List<ProduitDto> getAllProduits() {
        return produitRepository.findByIsDeletedFalse()
                .stream()
                .map(produit -> produitMapper.toDto(produit))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProduitDto getProduitById(Long id) {
        return produitRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .map(produitMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    private void createHistorique(Produit produit, TypeAction action, String actionBy) {
        Historique historique = new Historique();

        historique.setProduit(produit);
        historique.setTypeAction(action);
        historique.setNumeroSerie(produit.getNumeroSerie());

        historique.setOldDestination(produit.getDestination());
        historique.setOldMotif(produit.getMotif());
        historique.setOldDateAcquisition(produit.getDateAcquisition());
        historique.setOldAffectation(produit.getAffectation());
        historique.setOldStatutProduit(produit.getStatutProduit());
        historique.setDateAction(LocalDateTime.now());

        historiqueRepository.save(historique);
    }
    @Transactional
    public ProduitDto createProduit(ProduitDto produitDto) {
        Produit produit = produitMapper.toEntity(produitDto);
        produit = produitRepository.save(produit);

        // Create history entry
        createHistorique(produit, TypeAction.CREATE, "system");

        return produitMapper.toDto(produit);
    }



    @Transactional
    public ProduitDto updateProduit(Long id, ProduitDto produitDto) {
        Produit existing = produitRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create history entry before update
        createHistorique(existing, TypeAction.UPDATE, "system");

        existing.setTypeMateriel(produitDto.getTypeMateriel());
        existing.setReference(produitDto.getReference());
        existing.setNumeroSerie(produitDto.getNumeroSerie());
        existing.setDestination(produitDto.getDestination());
        existing.setMotif(produitDto.getMotif());
        existing.setDateAcquisition(produitDto.getDateAcquisition());
        existing.setAffectation(produitDto.getAffectation());
        existing.setStatutProduit(produitDto.getStatutProduit());

        Produit updated = produitRepository.save(existing);
        return produitMapper.toDto(updated);
    }

    @Transactional
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create history entry before deletion
        createHistorique(produit, TypeAction.DELETE, "system");

        produit.softDeleted();
        produitRepository.save(produit);
    }

        @Transactional
        public List<ProduitDto> searchProduits(String numeroSerie, StatusProduit status, Category category) {
            Specification<Produit> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("isDeleted"), false));

                if (numeroSerie != null && !numeroSerie.isEmpty()) {
                    predicates.add(cb.like(root.get("numeroSerie"), "%" + numeroSerie + "%"));
                }
                if (status != null) {
                    predicates.add(cb.equal(root.get("statutProduit"), status));
                }
                if (category != null) {
                    predicates.add(cb.equal(root.get("typeMateriel"), category));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            };

            return produitRepository.findAll(spec)
                    .stream()
                    .map(produitMapper::toDto)
                    .collect(Collectors.toList());
        }

    @Transactional(readOnly = true)
    public ProduitDto getProduitByNumeroSerie(String numeroSerie) {
        return produitRepository.findByNumeroSerieAndIsDeletedFalse(numeroSerie)
                .map(produitMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    @Transactional(readOnly = true)
    public List<ProduitDto> findByTypeMateriel(Category category) {
        return produitRepository.findByTypeMateriel(category)
                .stream()
                .map(produitMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<ProduitDto> findByStatutProduit(StatusProduit status){
        return produitRepository.findByStatutProduit(status)
                .stream()
                .map(produitMapper::toDto)
                .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public List<HistoriqueDto> getProduitHistory(Long produitId) {
        return historiqueRepository.findByProduitId(produitId)
                .stream()
                .map(historiqueMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Map<String, Long> getProduitStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", getTotalProduits());
        stats.put("actif", getActifProduits());
        stats.put("inactif", getInactifProduits());
        stats.put("enReparation", getEnReparationProduits());
        stats.put("horsService", getHorsServiceProduits());
        return stats;
    }

    @Transactional(readOnly = true)
    public long getTotalProduits() {
        return produitRepository.countNonDeletedProducts();
    }
    @Transactional(readOnly = true)
    public long getActifProduits() {
        return produitRepository.countByStatusAndNotDeleted(StatusProduit.ACTIF);


    }

    @Transactional(readOnly = true)
    public long getInactifProduits() {
        return produitRepository.countByStatusAndNotDeleted(StatusProduit.INACTIF);
    }

    @Transactional(readOnly = true)
    public long getEnReparationProduits() {
        return produitRepository.countByStatusAndNotDeleted(StatusProduit.EN_REPARATION);
    }

    @Transactional(readOnly = true)
    public long getHorsServiceProduits() {
        return produitRepository.countByStatusAndNotDeleted(StatusProduit.HORS_SERVICE);
    }
}
