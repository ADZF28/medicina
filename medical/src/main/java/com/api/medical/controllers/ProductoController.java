package com.api.medical.controllers;

import com.api.medical.models.Producto;
import com.api.medical.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/obtener")
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping("/buscarProducto")
    public ResponseEntity<List<Producto>> obtenerProducto(@RequestParam String nombre, @RequestParam String grupo, @RequestParam String tipo) {
        List<Producto> productos = productoService.filtrarProductos(nombre, grupo, tipo);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        if (productoActualizado != null) {
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return new ResponseEntity<>("Producto eliminado exitosamente", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("No se encontró el producto con ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/excel")
    public ResponseEntity<String> subirArchivoExcel(@RequestParam("file") MultipartFile archivoExcel) {
        if (archivoExcel.isEmpty()) {
            return new ResponseEntity<>("El archivo Excel está vacío", HttpStatus.BAD_REQUEST);
        }

        try {
            productoService.procesarArchivoExcel(archivoExcel.getInputStream());
            return new ResponseEntity<>("Archivo Excel procesado y datos guardados exitosamente", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar el archivo Excel", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sumar/{id}")
    public ResponseEntity<String> sumarValor(@PathVariable Long id, @RequestParam Long cantidad) {
        boolean response = productoService.sumarCantidadStock(id, cantidad);

        if (response) {
            return new ResponseEntity<>("Valor sumado exitosamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("No se encontró la entidad con ID: " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/restar/{id}")
    public ResponseEntity<String> restarValor(@PathVariable Long id, @RequestParam Long cantidad) {
        int response = productoService.restarCantidadStock(id, cantidad);

        if (response == 1) {
            return new ResponseEntity<>("404" + id, HttpStatus.NOT_FOUND);
        }

        if (response == 2) {
            return new ResponseEntity<>("400", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("200", HttpStatus.OK);
    }

}