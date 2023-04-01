package com.dh.clinica.service;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);

    }

    public Usuario loginUsuario(Usuario usuario) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuarioEnDB : usuarios) {
            if (usuarioEnDB.getEmail().equals(usuario.getEmail())
            && usuarioEnDB.getPassword().equals(usuario.getPassword())) {
                return usuarioEnDB;
            }
        }

        return null;

    }

    public void eliminar(Integer id) {
        usuarioRepository.delete(buscar(id));
    }

    public Usuario buscar(Integer id) {
        return usuarioRepository.findById(id).isPresent() ? usuarioRepository.findById(id).get() : null;
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


}
