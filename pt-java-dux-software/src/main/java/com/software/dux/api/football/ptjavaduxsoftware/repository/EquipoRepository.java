package com.software.dux.api.football.ptjavaduxsoftware.repository;

import com.software.dux.api.football.ptjavaduxsoftware.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByNombreContainingIgnoreCase(String nombre);
}
