package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoUsers extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM Users u WHERE " +
            "LOWER(u.nom) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.prenom) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Users> searchByNameOrEmail(@Param("query") String query);
    // Find all admins
    @Query("SELECT u FROM Users u WHERE TYPE(u) = Admins")
    List<Users> findAllAdmins();

    // Find all technicians
    @Query("SELECT u FROM Users u WHERE TYPE(u) = Technician")
    List<Users> findAllTechnicians();

}
