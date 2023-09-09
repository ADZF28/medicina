package com.api.medical.repositories;

import com.api.medical.models.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {

    List<Tipo> findByNombre(String nombreTipo);
}