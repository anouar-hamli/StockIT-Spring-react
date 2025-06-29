package com.sofac.StockIT.Controller.pages;

import com.sofac.StockIT.model.dto.ProduitUserActionDto;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.service.ProduitUserActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
@Tag(name = "Product User Actions", description = "API for tracking user actions on products")
public class ProduitUserActionController {

    private final ProduitUserActionService actionService;

    @PostMapping("/log/{userId}/{produitId}/{actionType}")
    @Operation(summary = "Log a product action")
    public ResponseEntity<Void> logAction(
            @PathVariable Long userId,
            @PathVariable Long produitId,
            @PathVariable TypeAction actionType) {
        actionService.logUserAction(userId, produitId, actionType);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all product actions with pagination")
    public ResponseEntity<Page<ProduitUserActionDto>> getAllActions(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(actionService.getAllActions(pageable));
    }

    @GetMapping("/by-serial/{serialNumber}")
    @Operation(summary = "Get actions by product serial number")
    public ResponseEntity<List<ProduitUserActionDto>> getBySerialNumber(
            @PathVariable String serialNumber) {
        return ResponseEntity.ok(actionService.findByProductSerialNumber(serialNumber));
    }

    @GetMapping("/by-user-and-serial/{userName}/{serialNumber}")
    @Operation(summary = "Get actions by user name and product serial number")
    public ResponseEntity<List<ProduitUserActionDto>> getByUserAndSerial(
            @PathVariable String userName,
            @PathVariable String serialNumber) {
        return ResponseEntity.ok(actionService.findByUserNameAndSerialNumber(userName, serialNumber));
    }

    @GetMapping("/filtered/{userName}/{serialNumber}/{actionType}")
    @Operation(summary = "Get filtered actions by user, serial number and action type")
    public ResponseEntity<List<ProduitUserActionDto>> getFilteredActions(
            @PathVariable String userName,
            @PathVariable String serialNumber,
            @PathVariable TypeAction actionType) {
        return ResponseEntity.ok(actionService.findByUserNameAndSerialNumberAndActionType(
                userName, serialNumber, actionType));
    }

    @PostMapping("/bulk-log")
    @Operation(summary = "Log actions for multiple products")
    public ResponseEntity<Void> logBulkActions(
            @RequestParam List<Long> produitIds,
            @RequestParam TypeAction actionType) {
        actionService.logBulkActions(produitIds, actionType);
        return ResponseEntity.ok().build();
    }
}
