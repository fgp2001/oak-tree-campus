package com.oaktree.campus.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "profesores_cursos")
@Getter
@Setter
public class ProfesorCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="profesor_id", nullable = false)
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    private LocalDate fechaAsignacion;

    @PrePersist
    public void prePersist(){
        this.fechaAsignacion = LocalDate.now();
    }
}
