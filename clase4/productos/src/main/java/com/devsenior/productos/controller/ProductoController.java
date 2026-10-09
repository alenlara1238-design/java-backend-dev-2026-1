package com.devsenior.productos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.productos.model.Producto;
import com.devsenior.productos.service.ProductoService;

@RestController 
@RequestMapping("/api/productos")
public class ProductoController {

    private ProductoService service;

    public ProductoController(ProductoService service){
        this.service = service;
    }

    @GetMapping
    public List<Producto> listarProductos(){
        return service.listarProductos();
    }

    @GetMapping("/{id}")
    public Producto buscarProducto(@PathVariable Long id){
        return service.buscarProducto(id);
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto){
        return service.crearProducto(producto);
    }

}
