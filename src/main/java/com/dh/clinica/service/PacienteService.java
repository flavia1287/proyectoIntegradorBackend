package com.dh.clinica.service;

import com.dh.clinica.model.Paciente;
import com.dh.clinica.model.PacienteDTO;
import com.dh.clinica.model.Turno;
import com.dh.clinica.repository.DomicilioRepository;
import com.dh.clinica.repository.PacienteRepository;
import com.dh.clinica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private TurnoRepository turnoRepository;


    public Paciente guardar(Paciente p) {
        p.setDomicilio(domicilioRepository.save(p.getDomicilio()));
        p.setFechaIngreso(new Date());
        return pacienteRepository.save(p);
    }

    public Paciente buscar(Integer id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.isPresent() ? paciente.get() : null;
    }

    public List<PacienteDTO> buscarTodos() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> pacienteDTOS = new ArrayList<>();
        for (Paciente p : pacientes) {
            PacienteDTO dto = new PacienteDTO();
            dto.setId(p.getId());
            dto.setNombre(p.getApellido() + ", " + p.getNombre());
            pacienteDTOS.add(dto);
        }
        return pacienteDTOS;
    }

    public void eliminar(Integer id) {
        //buscar turnos del odontólogo y eliminarlos
        List<Turno> turnos = turnoRepository.buscarPorPaciente(id);
        for (Turno t : turnos) {
            turnoRepository.delete(t);
        }
        pacienteRepository.delete(buscar(id));
    }

    public Paciente actualizar(Paciente p) {
        Paciente pDB = buscar(p.getId());
        //actualizamos solo los campos que permitimos cambiar
        pDB.setApellido(p.getApellido());
        pDB.setNombre(p.getNombre());
        pDB.setDni(p.getDni());
        if(p.getDomicilio() != null) {
            pDB.getDomicilio().setCalle(p.getDomicilio().getCalle());
            pDB.getDomicilio().setNumero(p.getDomicilio().getNumero());
            pDB.getDomicilio().setLocalidad(p.getDomicilio().getLocalidad());
            pDB.getDomicilio().setProvincia(p.getDomicilio().getProvincia());
        }
        pDB.setDomicilio(domicilioRepository.save(pDB.getDomicilio()));
        return pacienteRepository.save(pDB);
    }
}
