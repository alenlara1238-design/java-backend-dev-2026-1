package com.devsenior.productos.exception;

public class ProductoNoEncontradoException extends RuntimeException{
    
    
    public ProductoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
