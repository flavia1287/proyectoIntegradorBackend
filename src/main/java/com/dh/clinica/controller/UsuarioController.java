package com.dh.clinica.controller;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.security.jwt.JwtUtils;
import com.dh.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos(){
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping()
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        if(usuarioService.loadUserByUsername(usuario.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));

    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> loginUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioEnDB = usuarioService.loginUsuario(usuario);
        if(usuarioEnDB != null) {
            String jwt = jwtUtils.generateTokenFromUsername(usuarioEnDB.getEmail());
            usuarioEnDB.setJwt(jwt);
            return ResponseEntity.ok(usuarioEnDB);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscar(id);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping()
    public ResponseEntity<Usuario> actualizar(@RequestBody Usuario usuario) {
        ResponseEntity<Usuario> response = null;

        if (usuario.getId() != null && usuarioService.buscar(usuario.getId()) != null)
            response = ResponseEntity.ok(usuarioService.actualizar(usuario));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ResponseEntity<String> response = null;

        if (usuarioService.buscar(id) != null) {
            usuarioService.eliminar(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }



}
