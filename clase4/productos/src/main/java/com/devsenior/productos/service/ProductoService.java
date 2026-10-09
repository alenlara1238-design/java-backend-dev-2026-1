package com.devsenior.productos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsenior.productos.exception.ProductoNoEncontradoException;
import com.devsenior.productos.model.Producto;
import com.devsenior.productos.repository.ProductoRepository;

@Service 
public class ProductoService {
    private ProductoRepository repository;

    public ProductoService(ProductoRepository repository){
        this.repository = repository;
    }

    public List<Producto> listarProductos(){
        return repository.findAll();
    }

    public Producto buscarProducto(Long id){
        Producto producto = repository.findById(id);

        if(producto == null){
            throw new ProductoNoEncontradoException("Producto con id: " + id + " no encontrado");
        }

        return producto;
    }

    public Producto crearProducto(Producto producto){
        return repository.save(producto);
    }

}
