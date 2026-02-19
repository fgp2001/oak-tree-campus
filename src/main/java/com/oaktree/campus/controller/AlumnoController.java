package com.oaktree.campus.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "alumno/dashboard";
    }
}
