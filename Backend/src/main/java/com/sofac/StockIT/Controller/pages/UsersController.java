package com.sofac.StockIT.Controller.pages;

import com.sofac.StockIT.model.dto.AdminsDto;
import com.sofac.StockIT.model.dto.TechnicianDto;
import com.sofac.StockIT.model.dto.UsersDto;
import com.sofac.StockIT.model.mapper.UsersMapper;
import com.sofac.StockIT.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final UsersMapper usersMapper;

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

    @GetMapping("/by-email")
    @Operation(summary="get user by email")
    public UsersDto getUserByEmail(@RequestParam("email") String email) {
        return usersService.getUserByEmail(email);
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

//    @PostMapping("/admins")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<AdminsDto> createAdmin(@RequestBody AdminsDto adminDto) {
//        return ResponseEntity.ok(usersService.createAdmin(adminDto));
//    }
//
    // Create Technician
    @PostMapping("/technicians")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new technician",
            description = "Requires ADMIN role. Password must be at least 8 chars with uppercase, lowercase, number and special char")
    public ResponseEntity<TechnicianDto> createTechnician(
            @Valid @RequestBody TechnicianDto technicianDto) {
        TechnicianDto createdTech = usersService.createTechnician(technicianDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTech);
    }


//    @PostMapping("/create")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto userDto) {
//        if (userDto instanceof AdminsDto) {
//            AdminsDto adminDto = (AdminsDto) userDto;
//            return ResponseEntity.ok(usersService.createAdmin(adminDto));
//        } else if (userDto instanceof TechnicianDto) {
//            TechnicianDto technicianDto = (TechnicianDto) userDto;
//            return ResponseEntity.ok(usersService.createTechnician(technicianDto));
//        } else {
//            throw new IllegalArgumentException("try anther");
//        }
//    }

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
