package com.api.medical.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "grupos")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    @Column(name="nombre_grupo")
    private String nombre;
}
