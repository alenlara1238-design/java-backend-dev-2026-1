package com.devsenior.servicelayer.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.devsenior.servicelayer.model.Producto;
import com.devsenior.servicelayer.repository.ProductoRepository;

@Service 
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository){
        this.repository = repository;
    }

    public Producto crear(Producto producto){
        //es una validación...
        if(producto.getPrecio() <= 0){
            throw new RuntimeException("El precio debe ser mayor a cero");
        }
         //verificamos si el producto existe previamente... (true or false)
        boolean existe = repository.findAll().stream()
                            .anyMatch(p -> p.getNombre().equalsIgnoreCase(producto.getNombre()));

        if(existe){
            throw new RuntimeException("Ya existe un producto con ese nombre");
        }
        
        //si pasó las validaciones entonces, se crea el producto.
        return repository.save(producto);
    }

    public List<Producto> listar(){
        return repository.findAll();
    }

    public List<Producto> productosCaros(){
       return repository.findAll().stream()
                    .filter(p -> p.getPrecio()>100)
                    .toList();
    }

    public List<Producto> buscarPorNombre(String texto){
        return repository.findAll().stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(texto.toLowerCase()))
                    .toList();
    }

    // Promedio de precios.


    // Eliminar un producto (validación de existencia)


}
