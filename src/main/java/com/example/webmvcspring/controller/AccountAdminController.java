package com.example.webmvcspring.controller;

import com.example.webmvcspring.Dto.RegisterDto;
import com.example.webmvcspring.model.entity.AccountEntity;
import com.example.webmvcspring.service.impl.AccountAdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountAdminController {
    private final AccountAdminServiceImpl adminService;

    public AccountAdminController(AccountAdminServiceImpl adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/register/admin")
    public String regisAdmin(Model model){
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("registerAdmin", registerDto);
        return "from/registerAdmin";
    }
    @PostMapping("/register/admin/save")
    public String registerAdminSave(@Valid @ModelAttribute("registerAdmin")RegisterDto registerDto,
                                    Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("registerAdmin", registerDto);
            return "from/registerAdmin";
        }
        AccountEntity existingAdmin = adminService.findByEmail(registerDto.getEmail());
        if (existingAdmin != null){
            bindingResult.rejectValue("email",null,"User already use!!" + existingAdmin);
        }
        adminService.saveAdmin(registerDto);
        return "redirect:/login";
    }


}
