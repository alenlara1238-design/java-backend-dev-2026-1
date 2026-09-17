package com.devsenior.Spotify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.Spotify.service.CancionService;

@RestController
public class CancionController {

    private final CancionService cancionService;

    public CancionController(CancionService cancionService) {
        this.cancionService = cancionService;
    }

    @GetMapping("/cancion")
    public String obtenerCancionDestacada() {
        return cancionService.obtenerCancionDestacada().getTitulo();
    }
}
