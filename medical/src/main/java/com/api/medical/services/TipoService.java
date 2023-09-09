package com.api.medical.services;

import com.api.medical.models.Tipo;
import com.api.medical.repositories.TipoRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TipoService {

    private final TipoRepository tipoRepository;

    @Autowired
    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    public List<Tipo> obtenerTodosLosTipos() {
        return tipoRepository.findAll();
    }

    public Tipo obtenerTipoPorId(Long id) {
        return tipoRepository.findById(id).orElse(null);
    }

    public Tipo actualizarTipo(Long id, Tipo tipo) {
        Tipo tipoExistente = tipoRepository.findById(id).orElse(null);
        if (tipoExistente != null) {
            tipoExistente.setNombre(tipo.getNombre());
            return tipoRepository.save(tipoExistente);
        }
        return null;
    }

    public Tipo guardarTipo(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    public boolean eliminarTipo(Long id) {
        try {
            tipoRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    public String procesarArchivoExcel(InputStream excelFile) {
        try (Workbook workbook = WorkbookFactory.create(excelFile)) {
            List<Tipo> data = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0); // Suponiendo que los datos están en la primera hoja

            for (Row row : sheet) {
                Tipo newData = new Tipo();

                Cell grupoCell = row.getCell(0);
                if (grupoCell != null) {
                    newData.setNombre(grupoCell.getStringCellValue());
                }

                data.add(newData);
            }

            tipoRepository.saveAll(data);
            return "OK";
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo Excel", e);
        }

    }
}
