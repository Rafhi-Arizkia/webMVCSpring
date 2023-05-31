package com.example.webmvcspring.model.repository;

import com.example.webmvcspring.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles,Long> {
    Roles findByRolesName(String name);
}
