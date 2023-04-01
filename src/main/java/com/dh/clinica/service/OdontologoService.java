package com.dh.clinica.service;

import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.OdontologoDTO;
import com.dh.clinica.model.Turno;
import com.dh.clinica.repository.OdontologoRepository;
import com.dh.clinica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private TurnoRepository turnoRepository;



    public Odontologo registrarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);

    }

    public void eliminar(Integer id) {
        //buscar turnos del odontólogo y eliminarlos
        List<Turno> turnos = turnoRepository.buscarPorOdontologo(id);
        for (Turno t : turnos) {
            turnoRepository.delete(t);
        }
        odontologoRepository.delete(buscar(id));
    }

    public Odontologo buscar(Integer id) {
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        return odontologo.isPresent() ? odontologo.get() : null;
    }

    public List<OdontologoDTO> buscarTodos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        List<OdontologoDTO> odontologoDTOS = new ArrayList<>();
        for (Odontologo o : odontologos) {
            OdontologoDTO dto = new OdontologoDTO();
            dto.setId(o.getId());
            dto.setNombre(o.getApellido() + ", " + o.getNombre());
            odontologoDTOS.add(dto);
        }
        return odontologoDTOS;
    }

    public Odontologo actualizar(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }


}
