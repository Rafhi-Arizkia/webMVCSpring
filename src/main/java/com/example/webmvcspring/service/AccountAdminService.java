package com.example.webmvcspring.service;

import com.example.webmvcspring.Dto.RegisterDto;
import com.example.webmvcspring.model.entity.AccountEntity;

import java.util.List;

public interface AccountAdminService {
    AccountEntity saveAdmin(RegisterDto registerDto);
    AccountEntity findByEmail(String email);
    List<AccountEntity> findAllAdmin();
}
