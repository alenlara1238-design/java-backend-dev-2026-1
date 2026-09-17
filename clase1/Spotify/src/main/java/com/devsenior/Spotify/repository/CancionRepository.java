package com.devsenior.Spotify.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.devsenior.Spotify.model.Cancion;

@Repository
public class CancionRepository {

    public List<Cancion> obtenerCanciones() {
        return List.of(new Cancion("Bohemian Rhapsody"), new Cancion("Don't Stop Me Now"), new Cancion("We Will Rock You"));
    }
}
