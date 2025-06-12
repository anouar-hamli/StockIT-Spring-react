package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUsers extends JpaRepository<Users, Long> {
}
