package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoUsers extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    List<Users> findByRole(String role);
}
