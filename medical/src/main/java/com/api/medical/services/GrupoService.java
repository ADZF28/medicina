package com.api.medical.services;

import com.api.medical.models.Grupo;
import com.api.medical.repositories.GrupoRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;

    @Autowired
    public GrupoService(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public List<Grupo> obtenerTodosLosGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo obtenerGrupoPorId(Long id) {
        return grupoRepository.findById(id).orElse(null);
    }

    public List<Grupo> buscarPorNombre(String nombreGrupo) {
        return grupoRepository.findByNombre(nombreGrupo);
    }

    public Grupo actualizarGrupo(Long id, Grupo grupo) {
        Grupo grupoExistente = grupoRepository.findById(id).orElse(null);
        if (grupoExistente != null) {
            grupoExistente.setNombre(grupo.getNombre());
            return grupoRepository.save(grupoExistente);
        }
        return null;
    }

    public Grupo guardarGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public boolean eliminarGrupo(Long id) {
        try {
            grupoRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    public String procesarArchivoExcel(InputStream excelFile) {
        try (Workbook workbook = WorkbookFactory.create(excelFile)) {
            List<Grupo> data = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0); // Suponiendo que los datos están en la primera hoja

            for (Row row : sheet) {
                Grupo newData = new Grupo();

                Cell grupoCell = row.getCell(0);
                if (grupoCell != null) {
                    newData.setNombre(grupoCell.getStringCellValue());
                }

                data.add(newData);
            }

            grupoRepository.saveAll(data);
            return "OK";
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo Excel", e);
        }

    }
}
