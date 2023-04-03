package com.dh.clinica.service;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> o = usuarioRepository.findByEmail(email);
        Usuario appUser = null;
        if(o.isPresent()) {
            appUser = o.get();
        }
        if(appUser != null) {
            Set<GrantedAuthority> list = new HashSet<>();
            list.add(new SimpleGrantedAuthority(appUser.getRol()));
            UserDetails user = (UserDetails) new User(appUser.getEmail(), "{noop}"+appUser.getPassword(), list);
            return user;
        }
        return null;

    }
}
