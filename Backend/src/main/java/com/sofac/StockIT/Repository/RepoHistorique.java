package com.sofac.StockIT.Repository;

import com.sofac.StockIT.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoHistorique extends JpaRepository<Historique, Long> {
}
