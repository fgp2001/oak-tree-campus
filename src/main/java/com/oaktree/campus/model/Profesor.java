package com.oaktree.campus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profesores")
@Getter @Setter
public class Profesor extends Usuario {
    private String departamento;
}
