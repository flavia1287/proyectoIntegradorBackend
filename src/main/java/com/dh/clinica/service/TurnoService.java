package com.dh.clinica.service;

import com.dh.clinica.model.Turno;
import com.dh.clinica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    public Turno registrarTurno(Turno turno){
        return  turnoRepository.save(turno);
    }

    public Turno buscar(Integer id) {
        Optional<Turno> t = turnoRepository.findById(id);
        return t.isPresent() ? t.get() : null;
    }

    public void eliminarTurno(Integer id) {
        Turno t = buscar(id);
        if (t != null) {
            turnoRepository.delete(t);
        }
    }
    public List<Turno> listar(){
        return turnoRepository.findAll();
    }
    public List<Turno> listarPorOdontologo(String odontologoId){
        return turnoRepository.buscarPorOdontologo(Integer.parseInt(odontologoId));
    }
    public List<Turno> listarPorPacienteo(String pacienteId) {
        return turnoRepository.buscarPorPaciente(Integer.parseInt(pacienteId));
    }
    public List<Turno> listarPorOdontologoYPaciente(String odontologoId, String pacienteId){
        return turnoRepository.buscarPorOdontologoYPaciente(Integer.parseInt(odontologoId), Integer.parseInt(pacienteId));
    }


}
