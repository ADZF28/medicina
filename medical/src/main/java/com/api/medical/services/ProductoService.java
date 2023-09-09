package com.api.medical.services;

import com.api.medical.models.Producto;
import com.api.medical.repositories.ProductoRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> filtrarProductos(String nombre_producto, String grupo, String tipo) {
        // Verificar si los parámetros de filtro son nulos o vacíos
        if (nombre_producto == null) nombre_producto = "";
        if (grupo == null) grupo = "";
        if (tipo == null) tipo = "";

        return productoRepository.findByNombreContainingAndGrupoContainingAndTipoContaining(nombre_producto, grupo, tipo);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);
        if (productoExistente != null) {
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setTipo(producto.getTipo());
            productoExistente.setGrupo(producto.getGrupo());
            productoExistente.setObservacion(producto.getObservacion());
            productoExistente.setStock(producto.getStock());
            productoExistente.setCantidadStock(producto.getCantidadStock());
            productoExistente.setFechaVencimiento(producto.getFechaVencimiento());
            return productoRepository.save(productoExistente);
        }
        return null;
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public boolean eliminarProducto(Long id) {
        try {
            productoRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    public String procesarArchivoExcel(InputStream excelFile) {
        try (Workbook workbook = WorkbookFactory.create(excelFile)) {
            List<Producto> data = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0); // Suponiendo que los datos están en la primera hoja

            for (Row row : sheet) {
                Producto newData = new Producto();

                Cell grupoCell = row.getCell(0);
                if (grupoCell != null) {
                    newData.setGrupo(grupoCell.getStringCellValue());
                }

                Cell tipoCell = row.getCell(1);
                if (tipoCell != null) {
                    newData.setTipo(tipoCell.getStringCellValue());
                }

                Cell nombreProductoCell = row.getCell(2);
                if (nombreProductoCell != null) {
                    newData.setNombre(nombreProductoCell.getStringCellValue());
                }

                Cell stockCell = row.getCell(3);
                if (stockCell != null) {
                    newData.setStock((long) stockCell.getNumericCellValue());
                }

                Cell cantidadStockCell = row.getCell(4);
                if (cantidadStockCell != null) {
                    newData.setCantidadStock((long) cantidadStockCell.getNumericCellValue());
                }

                Cell observacionCell = row.getCell(5);
                if (observacionCell != null) {
                    newData.setObservacion(observacionCell.getStringCellValue());
                }

                Cell fechaVencimientoCell = row.getCell(6);
                if (fechaVencimientoCell != null) {
                    newData.setFechaVencimiento(fechaVencimientoCell.getDateCellValue());
                }

                data.add(newData);
            }

            productoRepository.saveAll(data);
            return "OK";
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo Excel", e);
        }
    }

    public boolean sumarCantidadStock(Long id, Long cantidad) {
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {
            producto.setStock(producto.getStock() + cantidad);
            productoRepository.save(producto);
            return true;
        }
        return false;

    }

    public int restarCantidadStock(Long id, Long cantidad) {
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto == null) {
            return 1;
        }

        if (producto.getStock() <= 0 || producto.getStock() == 0) {
            return 2;
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
        return 3;

    }

}