package com.dh.clinica.repository;

import com.dh.clinica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u where u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);
}
