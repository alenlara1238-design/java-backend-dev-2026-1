package com.devsenior.productos.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.devsenior.productos.model.Producto;

@Repository 
public class ProductoRepository {
    private List<Producto> productos = new ArrayList<>();

    public ProductoRepository(){
        productos.add(
            new Producto(1L, "Mouse", 8000)
        );

        productos.add(
            new Producto(2L, "Teclado", 5000)
        );

        productos.add(
            new Producto(1L, "Monitor", 16000)
        );
    }

    public List<Producto> findAll(){
        return this.productos;
    }


    public Producto findById(Long id){
        for(Producto producto: productos){
            if(producto.getId().equals(id)){
                return producto;
            }
        }
        return null;
    }

    public Producto save(Producto producto){
        productos.add(producto);
        return producto;
    }



}
