package com.sofac.StockIT.service;

import com.sofac.StockIT.Repository.RepoProduitUserAction;
import com.sofac.StockIT.Repository.RepoUsers;
import com.sofac.StockIT.Repository.RepoProduit;
import com.sofac.StockIT.model.dto.ProduitUserActionDto;
import com.sofac.StockIT.model.entity.Produit;
import com.sofac.StockIT.model.entity.ProduitUserAction;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.model.entity.Users;
import com.sofac.StockIT.model.mapper.ProduitUserActionMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitUserActionService {

    private final RepoProduitUserAction actionRepository;
    private final ProduitUserActionMapper actionMapper;
    private final RepoUsers userRepository;
    private final RepoProduit produitRepository;


    //Logs a product action with manual user and product IDs

    @Transactional
    public void logUserAction(Long userId, Long produitId, TypeAction actionType) {
        Users user = getUserById(userId);
        Produit produit = getProduitById(produitId);
        saveAction(createAction(user, produit, actionType));
    }



     // Logs a product action using the currently authenticated user

    @Transactional
    public void logProduitAction(Produit produit, TypeAction actionType) {
        Users user = getCurrentUser();
        saveAction(createAction(user, produit, actionType));
    }


     // Logs bulk actions for multiple products

    @Transactional
    public void logBulkActions(List<Long> produitIds, TypeAction actionType) {
        Users user = getCurrentUser();
        produitIds.forEach(produitId -> {
            Produit produit = getProduitById(produitId);
            saveAction(createAction(user, produit, actionType));
        });
    }

    // Query methods
    public Page<ProduitUserActionDto> getAllActions(Pageable pageable) {
        return actionRepository.findAll(pageable).map(actionMapper::toDto);
    }

    public List<ProduitUserActionDto> findByProductSerialNumber(String serialNumber) {
        return actionRepository.findByProductSerialNumber(serialNumber).stream()
                .map(actionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProduitUserActionDto> findByUserNameAndSerialNumber(String userName, String serialNumber) {
        return actionRepository.findByUserNameAndSerialNumber(userName, serialNumber).stream()
                .map(actionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProduitUserActionDto> findByUserNameAndSerialNumberAndActionType(
            String userName, String serialNumber, TypeAction actionType) {
        return actionRepository.findByUserNameAndSerialNumberAndActionType(
                        userName, serialNumber, actionType).stream()
                .map(actionMapper::toDto)
                .collect(Collectors.toList());
    }

    // Helper methods
    private ProduitUserAction createAction(Users user, Produit produit, TypeAction actionType) {
        return ProduitUserAction.builder()
                .user(user)
                .produit(produit)
                .typeAction(actionType)
                .description(generateDescription(user, produit, actionType))
                .dateAction(LocalDateTime.now())
                .build();
    }

    private String generateDescription(Users user, Produit produit, TypeAction actionType) {
        return switch (actionType) {
            case CREATE -> String.format("%s created product %s (%s)",
                    user.getNom(), produit.getNumeroSerie(), produit.getTypeMateriel());
            case UPDATE -> String.format("%s updated product %s (%s)",
                    user.getNom(), produit.getNumeroSerie(), produit.getTypeMateriel());
            case DELETE -> String.format("%s deleted product %s (%s)",
                    user.getNom(), produit.getNumeroSerie(), produit.getTypeMateriel());
        };
    }

    private Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Users)) {
            throw new IllegalStateException("No authenticated user available");
        }
        return (Users) auth.getPrincipal();
    }

    private Users getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }

    private Produit getProduitById(Long produitId) {
        return produitRepository.findById(produitId)
                .orElseThrow(() -> new EntityNotFoundException("Produit not found: " + produitId));
    }

    private void saveAction(ProduitUserAction action) {
        actionRepository.save(action);
    }
}