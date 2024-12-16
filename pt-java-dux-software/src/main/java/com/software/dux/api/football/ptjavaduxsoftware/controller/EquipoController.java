package com.software.dux.api.football.ptjavaduxsoftware.controller;

import com.software.dux.api.football.ptjavaduxsoftware.exception.ErrorResponse;
import com.software.dux.api.football.ptjavaduxsoftware.exception.NotFoundException;
import com.software.dux.api.football.ptjavaduxsoftware.model.Equipo;
import com.software.dux.api.football.ptjavaduxsoftware.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoService.obtenerTodosLosEquipos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEquipoPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(equipoService.obtenerEquipoPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Equipo no encontrado", 404));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> obtenerEquiposPorNombre(@RequestParam String nombre) {
        try{
            return new ResponseEntity<>(equipoService.obtenerEquiposPorNombre(nombre), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Equipo no encontrado", 404));

        }

    }

    @PostMapping
    public ResponseEntity<?> crearEquipo(@RequestBody Equipo equipo) {
        try{
            Equipo nuevoEquipo = equipoService.crearEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            response.put("codigo", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        try{
            return new ResponseEntity<>(equipoService.actualizarEquipo(id, equipo), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Equipo no encontrado", 404));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEquipo(@PathVariable Long id) {
        try{
            equipoService.eliminarEquipo(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage(), e.getCodigo())); // Respuesta 404 Not Found
        }
    }
}
