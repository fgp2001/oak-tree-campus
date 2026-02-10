package com.oaktree.campus.model;


import com.oaktree.campus.model.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy =InheritanceType.JOINED)
@Getter@Setter
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean activo;

    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}
