package com.sofac.StockIT.Controller;

import com.sofac.StockIT.model.dto.ProduitDto;
import com.sofac.StockIT.model.entity.Category;
import com.sofac.StockIT.model.entity.Produit;
import com.sofac.StockIT.model.entity.StatusProduit;
import com.sofac.StockIT.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public List<ProduitDto> getAllProduits() {
        return produitService.getAllProduits();
    }

    @GetMapping("/{id}")
    public ProduitDto getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id);
    }

    @GetMapping("/numero-serie/{numeroSerie}")
    public ProduitDto getProduitByNumeroSerie(@PathVariable String numeroSerie) {
        return produitService.getProduitByNumeroSerie(numeroSerie);
    }

    @PutMapping("/{id}")
    public ProduitDto updateProduit(@PathVariable Long id, @RequestBody ProduitDto produitDto) {
        return produitService.updateProduit(id, produitDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
    }

    @GetMapping("/search")
    public List<ProduitDto> searchProduits(
            @RequestParam(required = false) String numeroSerie,
            @RequestParam(required = false) StatusProduit status,
            @RequestParam(required = false) Category category) {
        return produitService.searchProduits(numeroSerie, status, category);
    }

}
