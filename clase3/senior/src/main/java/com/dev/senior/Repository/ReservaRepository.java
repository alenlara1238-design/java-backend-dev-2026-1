package com.dev.senior.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dev.senior.model.Reserva;

@Repository 
public class ReservaRepository {

    // Atributo
    private List<Reserva> reservas = new ArrayList<>();


    // Métodos
    public List<Reserva> findAll(){
        return reservas;
    }

    public Reserva findById(Long id){
        
        for(Reserva  reserva: reservas){
            
        }
    }

}
