package com.sofac.StockIT.listeners;

import com.sofac.StockIT.Repository.RepoProduitUserAction;
import com.sofac.StockIT.config.SpringContext;
import com.sofac.StockIT.model.entity.Produit;
import com.sofac.StockIT.model.entity.ProduitUserAction;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.model.entity.Users;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Component
public class ProduitListener {
    @PostPersist
    public void afterCreate(Produit produit) {
        logAction(produit, TypeAction.CREATE);
    }

    @PostUpdate
    public void afterUpdate(Produit produit) {
        logAction(produit, TypeAction.UPDATE);
    }

    @PostRemove
    public void afterDelete(Produit produit) {
        logAction(produit, TypeAction.DELETE);
    }

    private void logAction(Produit produit, TypeAction actionType) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Users user) {
            ProduitUserAction action = new ProduitUserAction();
            action.setProduit(produit);
            action.setUser(user);
            action.setTypeAction(actionType);
            action.setDateAction(LocalDateTime.now());

            String description = String.format("%s %s produit %s",
                    user.getNom(),
                    actionType.toString(),
                    produit.getNumeroSerie());
            action.setDescription(description);

            // Get the repository bean and save the action
            SpringContext.getBean(RepoProduitUserAction.class).save(action);
        }
    }
}
