package com.example.webmvcspring.model.repository;

import com.example.webmvcspring.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity,Long> {
    AccountEntity findByEmail(String email);
}
