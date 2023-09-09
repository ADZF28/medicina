package com.api.medical.repositories;

import com.api.medical.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingAndGrupoContainingAndTipoContaining(String nombre_producto, String grupo, String tipo);
}