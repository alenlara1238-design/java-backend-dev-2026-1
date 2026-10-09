package com.devsenior.productos.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
        return producto;
    }

    public Producto crearProducto(Producto producto){
        return repository.save(producto);
    }

}
