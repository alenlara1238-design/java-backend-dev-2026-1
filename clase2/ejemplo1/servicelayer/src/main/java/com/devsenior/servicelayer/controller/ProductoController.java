package com.devsenior.servicelayer.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.devsenior.servicelayer.model.Producto;
import com.devsenior.servicelayer.service.ProductoService;


@RestController 
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service){
        this.service = service;
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto){
        return service.crear(producto);
    }

    @GetMapping
    public List<Producto> listar(){
        return service.listar();
    }

    @GetMapping("/caros")
    public List<Producto> caros(){
        return service.productosCaros();
    }

    @GetMapping("/buscar")
    public List<Producto> buscar(@RequestParam String texto){
        return service.buscarPorNombre(texto);
    }
}
