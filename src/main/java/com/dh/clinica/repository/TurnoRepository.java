package com.dh.clinica.repository;

import com.dh.clinica.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {

    @Query("SELECT t FROM Turno t where t.odontologo.id = :oId")
    List<Turno> buscarPorOdontologo(@Param("oId") Integer odontologoId);

    @Query("SELECT t FROM Turno t where t.paciente.id = :pId")
    List<Turno> buscarPorPaciente(@Param("pId") Integer pacienteId);

    @Query("SELECT t FROM Turno t where t.odontologo.id = :oId AND t.paciente.id = :pId")
    List<Turno> buscarPorOdontologoYPaciente(@Param("oId") Integer odontologoId, @Param("pId") Integer pacienteId);
}
