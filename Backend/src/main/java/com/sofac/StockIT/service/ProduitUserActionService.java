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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitUserActionService {

    private final RepoProduitUserAction actionRepository;
    private final ProduitUserActionMapper actionMapper;
    private final RepoUsers userRepository;
    private final RepoProduit produitRepository;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a", Locale.ENGLISH);

    public void logUserAction(Long userId, Long produitId, TypeAction actionType) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new EntityNotFoundException("Produit not found with ID: " + produitId));

        String userName = user.getNom();
        String category = produit.getTypeMateriel().name();
        String serialNumber = produit.getNumeroSerie();
        String status = produit.getStatutProduit().name();

        String description = switch (actionType) {
            case CREATE -> String.format("%s added a %s with serial number %s to %s",
                    userName, category, serialNumber, status);
            case UPDATE -> String.format("%s updated the %s with serial number %s to status %s",
                    userName, category, serialNumber, status);
            case DELETE -> String.format("%s deleted the %s with serial number %s from the system",
                    userName, category, serialNumber);
        };

        ProduitUserAction action = new ProduitUserAction();
        action.setUser(user);
        action.setProduit(produit);
        action.setTypeAction(actionType);
        action.setDescription(description);
        action.setDateAction(LocalDateTime.now());

        actionRepository.save(action);
    }
    public List<ProduitUserActionDto> getAllActionsForTable() {
        return actionRepository.findAll().stream()
                .map(actionMapper::toDto)
                .collect(Collectors.toList());
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
        return actionRepository.findByUserNameAndSerialNumberAndActionType(userName, serialNumber, actionType).stream()
                .map(actionMapper::toDto)
                .collect(Collectors.toList());
    }



}
