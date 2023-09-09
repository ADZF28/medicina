package com.api.medical.controllers;

import com.api.medical.models.Tipo;
import com.api.medical.services.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tipos")
public class TipoController {

    private final TipoService tipoService;

    @Autowired
    public TipoController(TipoService tipoService) {
        this.tipoService = tipoService;
    }

    @GetMapping("/obtener")
    public ResponseEntity<List<Tipo>> obtenerTodosLosTipos() {
        List<Tipo> tipos = tipoService.obtenerTodosLosTipos();
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Tipo> obtenerTipoPorId(@PathVariable Long id) {
        Tipo tipo = tipoService.obtenerTipoPorId(id);
        return new ResponseEntity<>(tipo, HttpStatus.OK);
    }

    @PostMapping("/buscarNombre")
    public ResponseEntity<List<Tipo>> obtenerTipoPorNombre(@RequestParam String nombre) {
        List<Tipo> tipo = tipoService.buscarPorNombre(nombre);
        return new ResponseEntity<>(tipo, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Tipo> guardarTipo(@RequestBody Tipo tipo) {
        Tipo nuevoTipo = tipoService.guardarTipo(tipo);
        return new ResponseEntity<>(nuevoTipo, HttpStatus.CREATED);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Tipo> actualizarTipo(@PathVariable Long id, @RequestBody Tipo tipo) {
        Tipo tipoActualizado = tipoService.actualizarTipo(id, tipo);
        if (tipoActualizado != null) {
            return new ResponseEntity<>(tipoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTipo(@PathVariable Long id) {
        boolean eliminado = tipoService.eliminarTipo(id);
        if (eliminado) {
            return new ResponseEntity<>("Tipo eliminado exitosamente", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("No se encontró el Tipo con ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/excel")
    public ResponseEntity<String> subirArchivoExcel(@RequestParam("file") MultipartFile archivoExcel) {
        if (archivoExcel.isEmpty()) {
            return new ResponseEntity<>("El archivo Excel está vacío", HttpStatus.BAD_REQUEST);
        }

        try {
            tipoService.procesarArchivoExcel(archivoExcel.getInputStream());
            return new ResponseEntity<>("Archivo Excel procesado y datos guardados exitosamente", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar el archivo Excel", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
