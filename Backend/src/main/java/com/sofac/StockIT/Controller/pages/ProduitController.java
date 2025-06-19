package com.sofac.StockIT.Controller.pages;

import com.sofac.StockIT.model.dto.ProduitDto;
import com.sofac.StockIT.model.entity.Category;
import com.sofac.StockIT.model.entity.StatusProduit;
import com.sofac.StockIT.service.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

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

    @PostMapping
    public ResponseEntity<ProduitDto> createProduit(@RequestBody ProduitDto produitDto) {
        ProduitDto createdProduit = produitService.createProduit(produitDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduit.getIdproduit())
                .toUri();
        return ResponseEntity.created(location).body(createdProduit);
    }

    @PutMapping("/{id}")
    public ProduitDto updateProduit(@PathVariable Long id, @RequestBody ProduitDto produitDto) {
        return produitService.updateProduit(id, produitDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<ProduitDto> searchProduits(
            @RequestParam(required = false) String numeroSerie,
            @RequestParam(required = false) StatusProduit status,
            @RequestParam(required = false) Category category) {
        return produitService.searchProduits(numeroSerie, status, category);
    }
    @GetMapping("/by-category")
    public ResponseEntity<List<ProduitDto>> getProduitsByCategory(@RequestParam Category typeMateriel) {
        List<ProduitDto> produits = produitService.findByTypeMateriel(typeMateriel);

        if(produits.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(produits);
    }
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getProduitsByCategory(){
        return ResponseEntity.ok(Arrays.asList(Category.values()));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ProduitDto>> getProduitsByStatus(@RequestParam StatusProduit status) {
        List<ProduitDto> produits=produitService.findByStatutProduit(status);

        if(produits.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produits);
    }
    @GetMapping("/status")
    public ResponseEntity<List<StatusProduit>> getProduitsByStatus(){
        return ResponseEntity.ok(Arrays.asList(StatusProduit.values()));
    }


    @GetMapping("/total")
    public long getTotalProductsCount() {
        return produitService.getTotalProduits();
    }
    @GetMapping("/actif")
    public long getActifProduits(){
        return produitService.getActifProduits();
    }

    @GetMapping("/inactif")
    public long getInactiveProductsCount() {
        return produitService.getInactifProduits();
    }

    @GetMapping("/en-reparation")
    public long getProductsInRepairCount() {
        return produitService.getEnReparationProduits();
    }

    @GetMapping("/hors-service")
    public long getOutOfServiceProductsCount() {
        return produitService.getHorsServiceProduits();
    }



}
