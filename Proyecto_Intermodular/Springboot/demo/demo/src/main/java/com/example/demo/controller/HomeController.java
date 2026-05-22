package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/en")
    public String en() {
        return "en";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String plan, Model model) {
        if (plan != null) model.addAttribute("plan", plan);
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/auth")
    public String auth(@RequestParam String plan, Model model) {
        model.addAttribute("plan", plan);
        return "auth";
    }
}
