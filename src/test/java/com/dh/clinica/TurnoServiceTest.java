package com.dh.clinica;


import com.dh.clinica.model.*;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.TurnoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TurnoServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private TurnoService turnoService;

    Turno toDelete;
    Odontologo o;
    Paciente p;

    @BeforeEach
    public void cargarDataSet() {
        o = odontologoService.registrarOdontologo(new Odontologo("Javier", "Lopez", 12345));
        Domicilio domicilio = new Domicilio("Av Libertador", "222", "CABA", "Buenos Aires");
        p = pacienteService.guardar(new Paciente("Lucia", "Benitez", "12456788", new Date(), domicilio));
        toDelete = turnoService.registrarTurno(new Turno(p, o, new Date()));

    }

    @Test
    public void testAgregarYBuscarTurnoTest() {
        Date tomorrow = new Date();
        tomorrow.setTime(tomorrow.getTime()+24*60*60*1000);
        Turno t = turnoService.registrarTurno(new Turno(p, o, tomorrow));

        Assertions.assertNotNull(turnoService.buscar(t.getId()));
    }

    @Test
    public void testEliminarTurnoTest() {
        turnoService.eliminarTurno(toDelete.getId());
        Assertions.assertTrue(turnoService.buscar(toDelete.getId()) == null);

    }

    @Test
    public void testTraerTodos() {
        List<Turno> turnos = turnoService.listar();
        Assertions.assertTrue(!turnos.isEmpty());
        Assertions.assertTrue(turnos.size() > 0);
        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }


}
