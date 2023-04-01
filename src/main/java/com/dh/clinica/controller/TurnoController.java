package com.dh.clinica.controller;

import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Turno;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.TurnoService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {


    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) {
        ResponseEntity<Turno> response;
        if (pacienteService.buscar(turno.getPaciente().getId()) != null && odontologoService.buscar(turno.getOdontologo().getId()) != null)
            response = ResponseEntity.ok(turnoService.registrarTurno(turno));

        else
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return response;


    }

    @GetMapping
    public ResponseEntity<List<Turno>> listar(@RequestParam(value = "odontologo_id", required = false) String oId,
                                              @RequestParam(value = "paciente_id", required = false) String pId){
        if (!Strings.isBlank(oId)) {
            if (!Strings.isBlank(pId)) {
                return ResponseEntity.ok(turnoService.listarPorOdontologoYPaciente(oId, pId));
            } else {
                return ResponseEntity.ok(turnoService.listarPorOdontologo(oId));
            }

        } else if (!Strings.isBlank(pId)) {
            return ResponseEntity.ok(turnoService.listarPorPacienteo(pId));
        } else {
            return ResponseEntity.ok(turnoService.listar());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ResponseEntity<String> response = null;

        if (turnoService.buscar(id) != null) {
            turnoService.eliminarTurno(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscar(@PathVariable Integer id) {
        Turno turno = turnoService.buscar(id);

        return ResponseEntity.ok(turno);
    }

    @PutMapping()
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno) {
        ResponseEntity<Turno> response = null;

        if (turno.getId() != null && turnoService.buscar(turno.getId()) != null)
            response = ResponseEntity.ok(turnoService.registrarTurno(turno));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


}
