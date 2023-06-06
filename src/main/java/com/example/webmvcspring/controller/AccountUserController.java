package com.example.webmvcspring.controller;

import com.example.webmvcspring.Dto.RegisterDto;
import com.example.webmvcspring.model.entity.AccountEntity;
import com.example.webmvcspring.service.impl.AccountUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountUserController {
    @Lazy
    private final AccountUserServiceImpl service;
    @Autowired
    public AccountUserController(AccountUserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String regis(Model model){
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("register",registerDto);
        return "from/register";
    }
    @PostMapping("/register/user")
    public String saveUser(@Valid @ModelAttribute("register")
                           RegisterDto registerDto, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("register",registerDto);
            return "from/register";
        }
        AccountEntity existingUser = service.findByEmail(registerDto.getEmail());
        if (existingUser != null){
            bindingResult.rejectValue("email",null,"User already use!!" + existingUser);
        }
        service.save(registerDto);
        return "redirect:/login";
    }
}
