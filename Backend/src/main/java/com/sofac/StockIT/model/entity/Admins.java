package com.sofac.StockIT.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id_User", foreignKey = @ForeignKey(name = "fk_admins_user",
        foreignKeyDefinition = "FOREIGN KEY (id_User) REFERENCES users(id_user) ON DELETE CASCADE"))
public class Admins extends Users {
    @Column(nullable = false)
    private boolean isSuperAdmin = true;


    public boolean canViewProducts() { return true; }
    public boolean canAddProducts() { return true; }
    public boolean canEditProducts() { return true; }
    public boolean canDeleteProducts() { return true; }

}
