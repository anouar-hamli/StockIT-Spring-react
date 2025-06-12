package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoAdmins extends JpaRepository<Admins, Long> {
}
