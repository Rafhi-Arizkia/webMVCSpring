package com.example.webmvcspring.service.impl;

import com.example.webmvcspring.Dto.RegisterDto;
import com.example.webmvcspring.model.entity.AccountEntity;
import com.example.webmvcspring.model.entity.Roles;
import com.example.webmvcspring.model.repository.AccountRepo;
import com.example.webmvcspring.model.repository.RolesRepo;
import com.example.webmvcspring.service.AccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service

public class AccountUserServiceImpl implements AccountUserService, UserDetailsService {
    private final AccountRepo accountRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountUserServiceImpl(AccountRepo accountRepo, RolesRepo rolesRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity account = accountRepo.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        List<GrantedAuthority> authorities = getAuthorities(account.getRoles());

        return new User(account.getEmail(), account.getPassword(), authorities);
    }
    private List<GrantedAuthority> getAuthorities(List<Roles> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRolesName()));
        }
        return authorities;
    }

    @Override
    public AccountEntity save(RegisterDto registerDto) {
        Roles roles = rolesRepo.findByRolesName("ROLE_USER");
        if (roles == null){
            checkUserRoleExist();
        }
        var account = AccountEntity.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(Collections.singletonList(roles))
                .build();
        return accountRepo.save(account);
    }

    private void checkUserRoleExist(){
        Roles roles = new Roles();
        roles.setRolesName("ROLE_USER");
        rolesRepo.save(roles);
    }
    @Override
    public AccountEntity findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }

    @Override
    public List<AccountEntity> findAllUser() {
        return accountRepo.findAll();
    }


}
