package com.dev.senior.service;

import com.dev.senior.Repository.*;
import com.dev.senior.model.*;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    //atributo principal del service:
    private final ReservaRepository repository;



    public ReservaService(ReservaRepository repository){
        this.repository = repository;
    }

    public List<Reserva> findAll(){
        //aqui podriamos aplicar reglas: verificar autenticación, rol del cliente...etc
        // El service solicita al repository todas las reservas.
        return repository.findAll();
    }

    public Reserva findById(Long id){
       return repository.findById(id);
    }

    public boolean save(Reserva reserva){
        boolean salaOcupada = repository.existsBySalaAndHora(reserva.getSala(), reserva.getHora());

        //aqui aplicamos la lógica del negocio.
        if(salaOcupada){
            return false;
        }
        repository.save(reserva);
        return true;
    }

    public void update(Reserva reserva){
        repository.update(reserva);
    }

    public void delete(Long id){
        repository.delete(id);
    }

}
