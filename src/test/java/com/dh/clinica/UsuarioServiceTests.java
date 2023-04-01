package com.dh.clinica;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UsuarioServiceTests {

    @Autowired
    private UsuarioService usuarioService;

    private int idToDelete = 0;

    @BeforeEach
    public void cargarDataSet() {
        idToDelete = usuarioService.registrarUsuario(new Usuario("f@r.com", "admin1234", "ROLE_ADMIN")).getId();
    }

    @Test
    public void testGuardarUsuario() {
        Usuario usuario = usuarioService.registrarUsuario(new Usuario("e@g.com", "1234", "ROLE_USER"));
        Assertions.assertTrue(usuario.getId() != null);

    }

    @Test
    public void testEliminarPacienteTest() {
        usuarioService.eliminar(idToDelete);
        Assertions.assertTrue(usuarioService.buscar(idToDelete) == null);

    }

    @Test
    public void testTraerTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        Assertions.assertTrue(!usuarios.isEmpty());
        System.out.println(usuarios);
    }

}
