package com.dh.clinica;

import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.OdontologoDTO;
import com.dh.clinica.service.OdontologoService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OdontologoServiceTests {

    @Autowired
    private OdontologoService odontologoService;

    Odontologo toDelete;

    @BeforeEach
    public void cargarDataSet() {
        toDelete = odontologoService.registrarOdontologo(new Odontologo("Santiago", "Paz", 3455647));


    }

    @Test
    public void guardarOdontologo() {
        Odontologo odontologo = odontologoService.registrarOdontologo(new Odontologo("Juan", "Ramirez", 348971960));
        Assertions.assertTrue(odontologo.getId() != null);

    }

    @Test
    public void eliminarOdontologoTest() {
        odontologoService.eliminar(toDelete.getId());
        Assertions.assertTrue(odontologoService.buscar(toDelete.getId()) == null);

    }

    @Test
    public void traerTodos() {
        List<OdontologoDTO> odontologos = odontologoService.buscarTodos();
        Assertions.assertTrue(!odontologos.isEmpty());
        Assertions.assertTrue(odontologos.size() > 0);
        System.out.println(odontologos);
    }

}
