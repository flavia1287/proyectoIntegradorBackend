package com.dh.clinica;


import com.dh.clinica.model.Domicilio;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.model.PacienteDTO;
import com.dh.clinica.service.PacienteService;
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
public class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    Paciente toDelete;

    @BeforeEach
    public void cargarDataSet() {
        Domicilio domicilio = new Domicilio("Av Santa fe", "444", "CABA", "Buenos Aires");
        toDelete = pacienteService.guardar(new Paciente("Santiago", "Paz", "88888888", new Date(), domicilio));
        Domicilio domicilio1 = new Domicilio("Av Avellaneda", "333", "CABA", "Buenos Aires");
        Paciente p1 = pacienteService.guardar(new Paciente("Micaela", "Perez", "99999999", new Date(), domicilio1));

    }

    @Test
    public void testAgregarYBuscarPacienteTest() {
        Domicilio domicilio = new Domicilio("Calle", "123", "Temperley", "Buenos Aires");
        Paciente p = pacienteService.guardar(new Paciente("Tomas", "Pereyra", "12345678", new Date(), domicilio));

        Assertions.assertNotNull(pacienteService.buscar(p.getId()));
    }

    @Test
    public void testEliminarPacienteTest() {
        pacienteService.eliminar(toDelete.getId());
        Assertions.assertTrue(pacienteService.buscar(toDelete.getId()) == null);

    }

    @Test
    public void testTraerTodos() {
        List<PacienteDTO> pacientes = pacienteService.buscarTodos();
        Assertions.assertTrue(!pacientes.isEmpty());
        Assertions.assertTrue(pacientes.size() > 0);
        for (PacienteDTO paciente : pacientes) {
            System.out.println(paciente);
        }
    }


}
