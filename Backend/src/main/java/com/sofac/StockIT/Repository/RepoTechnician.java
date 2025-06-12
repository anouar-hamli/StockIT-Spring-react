package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoTechnician extends JpaRepository<Technician, Integer> {
}
