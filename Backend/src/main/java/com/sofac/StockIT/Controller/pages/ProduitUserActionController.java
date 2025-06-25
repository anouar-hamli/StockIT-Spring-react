package com.sofac.StockIT.Controller.pages;

import com.sofac.StockIT.model.dto.ProduitUserActionDto;
import com.sofac.StockIT.model.entity.TypeAction;
import com.sofac.StockIT.service.ProduitUserActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
@Tag(name = "Product User Actions", description = "API for tracking user actions on products")
public class ProduitUserActionController {

    private final ProduitUserActionService actionService;

    @PostMapping("/log")
    @Operation(summary = "Log a new user action on a product")
    public ResponseEntity<Void> logUserAction(
            @RequestParam Long userId,
            @RequestParam Long produitId,
            @RequestParam TypeAction actionType) {

        actionService.logUserAction(userId, produitId, actionType);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all product user actions for display in table")
    public ResponseEntity<List<ProduitUserActionDto>> getAllActionsForTable() {
        return ResponseEntity.ok(actionService.getAllActionsForTable());
    }

    @GetMapping("/by-serial/{serialNumber}")
    @Operation(summary = "Get all actions by product serial number")
    public ResponseEntity<List<ProduitUserActionDto>> getActionsByProductSerial(
            @PathVariable String serialNumber) {

        return ResponseEntity.ok(actionService.findByProductSerialNumber(serialNumber));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter actions by user name, serial number, and/or action type")
    public ResponseEntity<List<ProduitUserActionDto>> filterActions(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) TypeAction actionType) {

        if (userName != null && serialNumber != null && actionType != null) {
            return ResponseEntity.ok(
                    actionService.findByUserNameAndSerialNumberAndActionType(
                            userName, serialNumber, actionType));
        } else if (userName != null && serialNumber != null) {
            return ResponseEntity.ok(
                    actionService.findByUserNameAndSerialNumber(userName, serialNumber));
        } else if (serialNumber != null) {
            return ResponseEntity.ok(
                    actionService.findByProductSerialNumber(serialNumber));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
