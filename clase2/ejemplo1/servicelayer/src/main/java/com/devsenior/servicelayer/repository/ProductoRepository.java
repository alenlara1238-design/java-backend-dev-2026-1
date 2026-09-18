package com.devsenior.servicelayer.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devsenior.servicelayer.model.Producto;

@Repository 
public class ProductoRepository {
    private List<Producto> productos = new ArrayList<>();
    private Long contador = 1L;


    public List<Producto> findAll(){
        return productos;
    }

    public Optional<Producto> findById(Long id){
       return productos.stream()
            .filter(producto -> producto.getId().equals(id))
            .findFirst();
    }

    public Producto save(Producto producto){
        producto.setId(contador++);
        productos.add(producto);
        return producto;
    }

    public void deleteById(Long id){
        productos.removeIf(producto -> producto.getId().equals(id));
    }

}
