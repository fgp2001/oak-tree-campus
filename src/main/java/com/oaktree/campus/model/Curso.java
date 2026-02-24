package com.oaktree.campus.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cursos")
@Getter
@Setter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column (length = 1000)
    private String descripcion;
    private String linkMeet;
    private boolean activo = true;
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "curso")
    private List<AlumnoCurso> alumnos;

    @OneToMany(mappedBy = "curso")
    private List<ProfesorCurso> profesores;

    @PrePersist
    public void prePersist(){
        this.fechaCreacion = LocalDateTime.now();
    }
}
