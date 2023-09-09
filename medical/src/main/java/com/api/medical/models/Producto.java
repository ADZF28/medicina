package com.api.medical.models;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name="nombre_producto")
    private String nombre;

    @Column(name="stock")
    private Long stock;

    @Column(name="cantidad_stock")
    private Long cantidadStock;

    @Column(name="observacion")
    private String observacion;

    @Column(name="fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
}
