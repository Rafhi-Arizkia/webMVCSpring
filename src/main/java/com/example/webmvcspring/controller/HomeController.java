package com.example.webmvcspring.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/login")
    public String showLogin(){
       return "from/login";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/user/index")
    public String userIndex(Model model){
        String message = "Hallow Sayang";
        model.addAttribute("msg",message);
        return "index";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/dashboard")
    public String admin(Model model){
        String message = "Hallow Admin";
        model.addAttribute("msg",message);
        return "dashboard";
    }
    @GetMapping("/denied")
    public String deniedLogin(){
        return "denied";
    }
}
