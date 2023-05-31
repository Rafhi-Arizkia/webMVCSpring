package com.example.webmvcspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("/login")
    public String showLogin(){
       return "login";
    }
    @GetMapping("/user/index")
    public String userIndex(Model model){
        String message = "Hallow Sayang";
        model.addAttribute("msg",message);
        return "index";
    }
    @GetMapping("/admin/index")
    public String adminIndex(Model model){
        String message = "Hallow Admin";
        model.addAttribute("msg",message);
        return "adminIndex";
    }
    @GetMapping("/denied")
    public String deniedLogin(){
        return "denied";
    }
}
