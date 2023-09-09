package com.api.medical.controllers;

import com.api.medical.models.Grupo;
import com.api.medical.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoService grupoService;

    @Autowired
    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping("/obtener")
    public ResponseEntity<List<Grupo>> obtenerTodosLosGrupos() {
        List<Grupo> grupos = grupoService.obtenerTodosLosGrupos();
        return new ResponseEntity<>(grupos, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Grupo> obtenerGrupoPorId(@PathVariable Long id) {
        Grupo grupo = grupoService.obtenerGrupoPorId(id);
        return new ResponseEntity<>(grupo, HttpStatus.OK);
    }

    @PostMapping("/buscarNombre")
    public ResponseEntity<List<Grupo>> obtenerGrupoPorNombre(@RequestParam String nombre) {
        List<Grupo> grupo = grupoService.buscarPorNombre(nombre);
        return new ResponseEntity<>(grupo, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Grupo> guardarGrupo(@RequestBody Grupo grupo) {
        Grupo nuevoGrupo = grupoService.guardarGrupo(grupo);
        return new ResponseEntity<>(nuevoGrupo, HttpStatus.CREATED);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Grupo> actualizarGrupo(@PathVariable Long id, @RequestBody Grupo grupo) {
        Grupo grupoActualizado = grupoService.actualizarGrupo(id, grupo);
        if (grupoActualizado != null) {
            return new ResponseEntity<>(grupoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarGrupo(@PathVariable Long id) {
        boolean eliminado = grupoService.eliminarGrupo(id);
        if (eliminado) {
            return new ResponseEntity<>("Grupo eliminado exitosamente", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("No se encontró el grupo con ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/excel")
    public ResponseEntity<String> subirArchivoExcel(@RequestParam("file") MultipartFile archivoExcel) {
        if (archivoExcel.isEmpty()) {
            return new ResponseEntity<>("El archivo Excel está vacío", HttpStatus.BAD_REQUEST);
        }

        try {
            grupoService.procesarArchivoExcel(archivoExcel.getInputStream());
            return new ResponseEntity<>("Archivo Excel procesado y datos guardados exitosamente", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar el archivo Excel", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
