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
            if(reserva.getId().equals(id)){
                return reserva;
            }
        }
        return null;
    }

    public Reserva save(Reserva reserva){
        reservas.add(reserva);
        return reserva;
    }

    public boolean existsReserva(Reserva reserva){
       return reservas.contains(reserva);
    }

    public boolean update(Reserva reservaActualizada){
       for(int i=0; i < reservas.size(); i++){
            if(reservas.get(i).getId().equals(reservaActualizada.getId())){
                reservas.set(i, reservaActualizada);
                return true; //si se consiguió el objeto reserva, detengase...
            }
       }

       return false;
       /*  Alternativa dada por: Miguel!
      int indice =  reservas.indexOf(reservaActualizada);
       if (indice != -1){
        reservas.set(indice, reservaActualizada);
       }*/

    }

    public void delete(Long id){
        for(int i = 0; i < reservas.size(); i++){
            if(reservas.get(i).getId().equals(id)){
                reservas.remove(i);
            }
        }
    }

    public boolean existsBySalaAndHora(String sala, String hora){
        for(Reserva reserva: reservas){
            if(reserva.getSala().equals(sala) && reserva.getHora().equals(hora)){
                return true;
            }
        }

        return false;
    }



}
