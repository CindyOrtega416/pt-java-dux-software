package com.software.dux.api.football.ptjavaduxsoftware.service;

import com.software.dux.api.football.ptjavaduxsoftware.model.Equipo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipoService {
    List<Equipo> obtenerTodosLosEquipos();
    Equipo obtenerEquipoPorId(long id);
    List<Equipo> obtenerEquiposPorNombre(String nombre);
    Equipo crearEquipo(Equipo equipo);
    Equipo actualizarEquipo(long id, Equipo equipoActualizado);
    void eliminarEquipo(long id);
}
