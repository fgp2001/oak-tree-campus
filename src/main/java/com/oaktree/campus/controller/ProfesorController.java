package com.oaktree.campus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "profesor/dashboard";
    }
}
