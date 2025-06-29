package com.sofac.StockIT.service;

import com.sofac.StockIT.Repository.RepoAdmins;
import com.sofac.StockIT.Repository.RepoTechnician;
import com.sofac.StockIT.Repository.RepoUsers;
import com.sofac.StockIT.model.dto.AdminsDto;
import com.sofac.StockIT.model.dto.ProduitUserActionDto;
import com.sofac.StockIT.model.dto.TechnicianDto;
import com.sofac.StockIT.model.dto.UsersDto;
import com.sofac.StockIT.model.entity.Admins;
import com.sofac.StockIT.model.entity.Technician;
import com.sofac.StockIT.model.entity.Users;
import com.sofac.StockIT.model.mapper.UsersMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class UsersService {
    private final RepoUsers userRepository;
    private final UsersMapper usersMapper;

    public List<UsersDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(usersMapper::toDto)
                .toList();
    }
    public UsersDto getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return usersMapper.toDto(user);
    }
    public AdminsDto getAdminById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!(user instanceof Admins)) {
            throw new RuntimeException("User is not an admin");
        }
        return usersMapper.toAdminsDto((Admins) user);
    }
    public TechnicianDto getTechnicianById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!(user instanceof Technician)) {
            throw new RuntimeException("User is not a technician");
        }
        return usersMapper.toTechnicianDto((Technician) user);
    }
    public AdminsDto createAdmin(AdminsDto adminDto) {
        if (userRepository.existsByEmail(adminDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Admins admin = usersMapper.toAdminsEntity(adminDto);
        admin.setDateAction(LocalDateTime.now());
        Admins savedAdmin = userRepository.save(admin);
        return usersMapper.toAdminsDto(savedAdmin);
    }
    public TechnicianDto createTechnician(TechnicianDto technicianDto) {
        if (userRepository.existsByEmail(technicianDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Technician technician = usersMapper.toTechnicianEntity(technicianDto);
        technician.setDateAction(LocalDateTime.now());
        Technician savedTechnician = userRepository.save(technician);
        return usersMapper.toTechnicianDto(savedTechnician);
    }
    public AdminsDto updateAdmin(Long id, AdminsDto adminDto) {
        Admins existingAdmin = (Admins) userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        usersMapper.updateFromDto(adminDto, existingAdmin);
        existingAdmin.setDateAction(LocalDateTime.now());
        Admins updatedAdmin = userRepository.save(existingAdmin);
        return usersMapper.toAdminsDto(updatedAdmin);
    }
    public TechnicianDto updateTechnician(Long id, TechnicianDto technicianDto) {
        Technician existingTechnician = (Technician) userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Technician not found"));

        usersMapper.updateFromDto(technicianDto, existingTechnician);
        existingTechnician.setDateAction(LocalDateTime.now());
        Technician updatedTechnician = userRepository.save(existingTechnician);
        return usersMapper.toTechnicianDto(updatedTechnician);
    }
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
    public List<UsersDto> searchUsers(String query) {
        return userRepository.searchByNameOrEmail(query).stream()
                .map(usersMapper::toDto)
                .toList();
    }
    public List<AdminsDto> getAllAdmins() {
        return userRepository.findAllAdmins().stream()
                .map(user -> usersMapper.toAdminsDto((Admins) user))
                .toList();
    }
    public List<TechnicianDto> getAllTechnicians() {
        return userRepository.findAllTechnicians().stream()
                .map(user -> usersMapper.toTechnicianDto((Technician) user))
                .toList();
    }


}
