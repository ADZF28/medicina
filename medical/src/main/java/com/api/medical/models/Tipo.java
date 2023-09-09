package com.api.medical.models;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tipos")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipo;

    @Column(name="nombre_tipo")
    private String nombre;
}
