package com.dev.senior.controller;

import com.dev.senior.service.ReservaService;
import com.dev.senior.model.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/reservas")
public class ReservaController {

    // atributo (dependencia) de la clase
    private final ReservaService service;

    public ReservaController(ReservaService service){
        this.service = service;
    }

    @GetMapping
    public List<Reserva> obtenerReservas(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Reserva obtenerReserva(@PathVariable Long id){
        return this.service.findById(id);
    }

    @PostMapping
    public String crearReserva(@RequestBody Reserva reserva){
        boolean creada = service.save(reserva);
        if(creada) return "Reserva creada exitosamente";
        return "La sala ya está reservada para esa hora";

    }

    @PutMapping("/{id}")
    public Reserva actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva){
        reserva.setId(id);
        service.update(reserva);
        return reserva;
    }

    @DeleteMapping("/{id}")
    public String eliminarReserva(@PathVariable Long id){
        service.delete(id);
        return "reserva eliminada correctamente";
    }

}
