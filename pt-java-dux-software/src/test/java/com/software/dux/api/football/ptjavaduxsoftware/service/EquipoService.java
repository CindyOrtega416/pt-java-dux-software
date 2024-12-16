package com.software.dux.api.football.ptjavaduxsoftware.service;

import com.software.dux.api.football.ptjavaduxsoftware.exception.NotFoundException;
import com.software.dux.api.football.ptjavaduxsoftware.model.Equipo;
import com.software.dux.api.football.ptjavaduxsoftware.repository.EquipoRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipoServiceImpTest {

    private static final String EQUIPO_1 = "Equipo1";

    private static final String EQUIPO_2 = "Equipo2";

    private static final String LIGA_1 = "Liga1";

    private static final String LIGA_2 = "Liga2";

    private static final String PAIS_1 = "Pais1";

    private static final String PAIS_2 = "Pais2";

    private static final String EQUIPO_MODIFICADO = "EquipoModificado";

    private static final String LIGA_MODIFICADO = "LigaModificada";

    private static final String PAIS_MODIFICADO = "PaisModificado";

    private static final String EQUIPO_NOT_FOUND = "Equipo no encontrado";
    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoServiceImp equipoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosLosEquipos_devuelveListaDeEquipos() {
        List<Equipo> equipos = Arrays.asList(
                new Equipo(1L, EQUIPO_1, LIGA_1, PAIS_1),
                new Equipo(2L, EQUIPO_2, LIGA_2, PAIS_2)
        );
        when(equipoRepository.findAll()).thenReturn(equipos);

        List<Equipo> resultado = equipoService.obtenerTodosLosEquipos();

        assertEquals(2, resultado.size());
        assertEquals(EQUIPO_1, resultado.get(0).getNombre());
        verify(equipoRepository, times(1)).findAll();
    }

    @Test
    void obtenerEquipoPorId_devuelveEquipoSiExiste() {
        Equipo equipo = new Equipo(1L, EQUIPO_1, LIGA_1, PAIS_1);
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));

        Equipo resultado = equipoService.obtenerEquipoPorId(1L);

        assertNotNull(resultado);
        assertEquals(EQUIPO_1, resultado.getNombre());
        verify(equipoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerEquipoPorId_lanzaExcepcionSiNoExiste() {
        when(equipoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> equipoService.obtenerEquipoPorId(1L));

        assertEquals(EQUIPO_NOT_FOUND, exception.getMessage());
        verify(equipoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerEquiposPorNombre_devuelveListaDeEquiposSiCoinciden() {
        List<Equipo> equipos = Arrays.asList(new Equipo(1L, EQUIPO_1, LIGA_1, PAIS_1));
        when(equipoRepository.findByNombreContainingIgnoreCase(EQUIPO_1)).thenReturn(equipos);

        List<Equipo> resultado = equipoService.obtenerEquiposPorNombre(EQUIPO_1);

        assertEquals(1, resultado.size());
        assertEquals(EQUIPO_1, resultado.get(0).getNombre());
        verify(equipoRepository, times(1)).findByNombreContainingIgnoreCase(EQUIPO_1);
    }

    @Test
    void obtenerEquiposPorNombre_lanzaExcepcionSiNoHayResultados() {
        when(equipoRepository.findByNombreContainingIgnoreCase(EQUIPO_1)).thenReturn(List.of());

        Exception exception = assertThrows(NotFoundException.class, () -> equipoService.obtenerEquiposPorNombre(EQUIPO_1));

        assertEquals("No se encontraron equipos con el nombre: Equipo1", exception.getMessage());
        verify(equipoRepository, times(1)).findByNombreContainingIgnoreCase(EQUIPO_1);
    }

    @Test
    void crearEquipo_guardaEquipoCorrectamente() {
        Equipo equipo = new Equipo(1L, EQUIPO_1, LIGA_1, PAIS_1);
        Equipo equipoGuardado = new Equipo(1L, EQUIPO_2, LIGA_1, PAIS_1);
        when(equipoRepository.save(equipo)).thenReturn(equipoGuardado);

        Equipo resultado = equipoService.crearEquipo(equipo);

        assertNotNull(resultado);
        assertEquals(EQUIPO_2, resultado.getNombre());
        verify(equipoRepository, times(1)).save(equipo);
    }

    @Test
    void crearEquipo_lanzaExcepcionSiHayViolacionDeRestricciones() {
        Equipo equipo = new Equipo(1L, null, LIGA_1, PAIS_1);
        when(equipoRepository.save(equipo)).thenThrow(ConstraintViolationException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> equipoService.crearEquipo(equipo));

        assertEquals("La solicitud contiene datos inválidos", exception.getMessage());
        verify(equipoRepository, times(1)).save(equipo);
    }

    @Test
    void actualizarEquipo_actualizaEquipoSiExiste() {
        Equipo equipoExistente = new Equipo(1L, EQUIPO_1, LIGA_1, PAIS_1);
        Equipo equipoActualizado = new Equipo(1L, EQUIPO_MODIFICADO, LIGA_MODIFICADO, PAIS_MODIFICADO); // ID no se utiliza aquí
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoExistente);

        Equipo resultado = equipoService.actualizarEquipo(1L, equipoActualizado);

        assertNotNull(resultado);
        assertEquals(EQUIPO_MODIFICADO, resultado.getNombre());
        assertEquals(LIGA_MODIFICADO, resultado.getLiga());
        assertEquals(PAIS_MODIFICADO, resultado.getPais());
        assertEquals(1L, resultado.getId()); // Asegura que el ID no cambió
        verify(equipoRepository, times(1)).findById(1L);
        verify(equipoRepository, times(1)).save(equipoExistente);
    }


    @Test
    void actualizarEquipo_lanzaExcepcionSiNoExiste() {
        Equipo equipoActualizado = new Equipo(1L, EQUIPO_MODIFICADO, LIGA_MODIFICADO, PAIS_MODIFICADO);
        when(equipoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> equipoService.actualizarEquipo(1L, equipoActualizado));

        assertEquals(EQUIPO_NOT_FOUND, exception.getMessage());
        verify(equipoRepository, times(1)).findById(1L);
        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    @Test
    void eliminarEquipo_eliminaEquipoSiExiste() {
        when(equipoRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> equipoService.eliminarEquipo(1L));

        verify(equipoRepository, times(1)).existsById(1L);
        verify(equipoRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarEquipo_lanzaExcepcionSiNoExiste() {
        when(equipoRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(NotFoundException.class, () -> equipoService.eliminarEquipo(1L));

        assertEquals(EQUIPO_NOT_FOUND, exception.getMessage());
        verify(equipoRepository, times(1)).existsById(1L);
        verify(equipoRepository, never()).deleteById(anyLong());
    }
}
