package com.dev.senior.model;

public class Reserva {
    private Long id;
    private String estudiante;
    private String sala;
    private String hora;

    public Reserva(){}

    public Reserva(Long id, String estudiante, String sala, String hora) {
        this.id = id;
        this.estudiante = estudiante;
        this.sala = sala;
        this.hora = hora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    

}
