package com.sofac.StockIT.service;

import com.sofac.StockIT.Repository.RepoProduit;
import com.sofac.StockIT.model.dto.ProduitDto;
import com.sofac.StockIT.model.entity.Category;
import com.sofac.StockIT.model.entity.Produit;
import com.sofac.StockIT.model.entity.StatusProduit;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.model.mapper.ProduitMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final RepoProduit produitRepository;
    private final ProduitMapper produitMapper;

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

    @Transactional
    public ProduitDto updateProduit(Long id, ProduitDto produitDto) {
        Produit existing = produitRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Product not found"));


        existing.setTypeMateriel(produitDto.getTypeMateriel());
        existing.setReference(produitDto.getReference());
        existing.setNumeroSerie(produitDto.getNumeroSerie());
        existing.setDestination(produitDto.getDestination());
        existing.setMotif(produitDto.getMotif());
        existing.setDateAcquisition(produitDto.getDateAcquisition());
        existing.setAffectation(produitDto.getAffectation());
        existing.setStatutProduit(produitDto.getStatutProduit());

        existing.sauvegarderHistorique(TypeAction.UPDATE, "system");
        Produit updated = produitRepository.save(existing);
        return produitMapper.toDto(updated);
    }

    @Transactional
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Product not found"));
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
}
