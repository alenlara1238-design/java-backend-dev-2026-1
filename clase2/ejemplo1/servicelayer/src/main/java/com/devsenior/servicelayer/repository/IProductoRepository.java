package com.devsenior.servicelayer.repository;

import java.util.List;
import java.util.Optional;

import com.devsenior.servicelayer.model.Producto;

public interface IProductoRepository {
    List<Producto> findAll();
    Optional<Producto> findById(Long id);
    Producto save(Producto producto);
    void deleteById(Long id);
}
