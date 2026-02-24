package com.oaktree.campus.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class TokenActivacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime fechaExpiracion;

    @OneToOne
    @JoinColumn(name ="usuario_id")
    private Usuario usuario;
}
