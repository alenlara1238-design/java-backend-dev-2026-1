package com.devsenior.Spotify.service;

import org.springframework.stereotype.Service;

import com.devsenior.Spotify.model.Cancion;
import com.devsenior.Spotify.repository.CancionRepository;

@Service
public class CancionService {

    private final CancionRepository cancionRepository;

    public CancionService(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    public Cancion obtenerCancionDestacada(){
        return cancionRepository.obtenerCanciones().get(0);
    }
}
