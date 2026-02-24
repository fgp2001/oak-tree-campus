package com.oaktree.campus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void enviarEmail(String para, String asunto, String contenido) {

        System.out.println("\n===============================");
        System.out.println("📧 EMAIL SIMULADO");
        System.out.println("Para: " + para);
        System.out.println("Asunto: " + asunto);
        System.out.println("Contenido: ");
        System.out.println(contenido);
        System.out.println("===============================\n");
    }
}
