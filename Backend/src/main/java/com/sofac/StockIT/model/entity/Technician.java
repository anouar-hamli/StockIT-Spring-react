package com.sofac.StockIT.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "technician")
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "id_User", foreignKey = @ForeignKey(name = "fk_technician_user",
        foreignKeyDefinition = "FOREIGN KEY (id_User) REFERENCES users(id_user) ON DELETE CASCADE"))
@NoArgsConstructor
@AllArgsConstructor
public class Technician extends Users {
    @Column(length = 255, nullable = false)
    private String department;
    private boolean canAccessAdminPanel = false;
}
