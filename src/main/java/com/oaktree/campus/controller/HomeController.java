package com.oaktree.campus.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()))) {
            return "redirect:/admin/dashboard";
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_PROFESOR".equals(a.getAuthority()))) {
            return "redirect:/profesor/dashboard";
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ALUMNO".equals(a.getAuthority()))) {
            return "redirect:/alumno/dashboard";
        }

        return "redirect:/login";

    }
}
