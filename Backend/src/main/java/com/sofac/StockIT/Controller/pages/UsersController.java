package com.sofac.StockIT.Controller.pages;

import com.sofac.StockIT.model.dto.AdminsDto;
import com.sofac.StockIT.model.dto.TechnicianDto;
import com.sofac.StockIT.model.dto.UsersDto;
import com.sofac.StockIT.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersDto>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }
    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminsDto>> getAllAdmins() {
        return ResponseEntity.ok(usersService.getAllAdmins());
    }
    @GetMapping("/technicians")
    public ResponseEntity<List<TechnicianDto>> getAllTechnicians() {
        return ResponseEntity.ok(usersService.getAllTechnicians());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getUserById(id));
    }

    @GetMapping("/admins/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminsDto> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getAdminById(id));
    }

    @GetMapping("/technicians/{id}")
    public ResponseEntity<TechnicianDto> getTechnicianById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getTechnicianById(id));
    }

    @PostMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminsDto> createAdmin(@RequestBody AdminsDto adminDto) {
        return ResponseEntity.ok(usersService.createAdmin(adminDto));
    }

    @PostMapping("/technicians")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TechnicianDto> createTechnician(@RequestBody TechnicianDto technicianDto) {
        return ResponseEntity.ok(usersService.createTechnician(technicianDto));
    }

    @PutMapping("/admins/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminsDto> updateAdmin(
            @PathVariable Long id,
            @RequestBody AdminsDto adminDto) {
        return ResponseEntity.ok(usersService.updateAdmin(id, adminDto));
    }

    @PutMapping("/technicians/{id}")
    public ResponseEntity<TechnicianDto> updateTechnician(
            @PathVariable Long id,
            @RequestBody TechnicianDto technicianDto) {
        return ResponseEntity.ok(usersService.updateTechnician(id, technicianDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersDto>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(usersService.searchUsers(query));
    }


}
