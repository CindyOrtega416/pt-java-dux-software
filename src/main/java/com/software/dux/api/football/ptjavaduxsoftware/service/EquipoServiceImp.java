package com.software.dux.api.football.ptjavaduxsoftware.service;

import com.software.dux.api.football.ptjavaduxsoftware.exception.NotFoundException;
import com.software.dux.api.football.ptjavaduxsoftware.model.Equipo;
import com.software.dux.api.football.ptjavaduxsoftware.repository.EquipoRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoServiceImp implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo obtenerEquipoPorId(long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
    }

    @Override
    public List<Equipo> obtenerEquiposPorNombre(String nombre) {
        List<Equipo> equipos = equipoRepository.findByNombreContainingIgnoreCase(nombre);
        if (equipos.isEmpty()) {
            throw new NotFoundException("No se encontraron equipos con el nombre: " + nombre, 404);
        }
        return equipos;
    }

    @Override
    public Equipo crearEquipo(Equipo equipo) {
        try {
            return equipoRepository.save(equipo);
        } catch (ConstraintViolationException ex) {
            throw new IllegalArgumentException("La solicitud contiene datos inválidos", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Violación de integridad de datos: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Equipo actualizarEquipo(long id, Equipo equipoActualizado) {
        return equipoRepository.findById(id)
                        .map(equipo -> {
                            equipo.setNombre(equipoActualizado.getNombre());
                            equipo.setLiga(equipoActualizado.getLiga());
                            equipo.setPais(equipoActualizado.getPais());
                            return equipoRepository.save(equipo);
                        })
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado", 404));

    }

    @Override
    public void eliminarEquipo(long id) {
        if(!equipoRepository.existsById(id)) {
            throw new NotFoundException("Equipo no encontrado", 404);
        }
        equipoRepository.deleteById(id);
    }
}
