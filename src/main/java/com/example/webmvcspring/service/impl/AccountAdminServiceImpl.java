package com.example.webmvcspring.service.impl;

import com.example.webmvcspring.Dto.RegisterDto;
import com.example.webmvcspring.model.entity.AccountEntity;
import com.example.webmvcspring.model.entity.Roles;
import com.example.webmvcspring.model.repository.AccountRepo;
import com.example.webmvcspring.model.repository.RolesRepo;
import com.example.webmvcspring.service.AccountAdminService;
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
public class AccountAdminServiceImpl implements AccountAdminService,UserDetailsService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepo rolesRepo;
    @Autowired
    public AccountAdminServiceImpl(AccountRepo accountRepo, PasswordEncoder passwordEncoder, RolesRepo rolesRepo) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepo = rolesRepo;
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
    public AccountEntity saveAdmin(RegisterDto registerDto) {
        Roles roles = rolesRepo.findByRolesName("ROLE_ADMIN");
        if (roles == null){
            checkAdminRoleExist();
        }
        var account = AccountEntity.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(Collections.singletonList(roles))
                .build();
        return accountRepo.save(account);
    }

    private void checkAdminRoleExist(){
        Roles roles = new Roles();
        roles.setRolesName("ROLE_ADMIN");
        rolesRepo.save(roles);
    }

    @Override
    public AccountEntity findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }

    @Override
    public List<AccountEntity> findAllAdmin() {
        return accountRepo.findAll();
    }


}
