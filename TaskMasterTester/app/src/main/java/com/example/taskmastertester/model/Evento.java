package com.example.taskmastertester.model;


import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import java.util.Date;

public class Evento {

    private int id;
    private String titulo;
    private String descripcion;

    private EventDateTime fechaInicio;

    private EventDateTime fechaFinal;


    public Evento(String titulo, String descripcion, EventDateTime fechaInicio,EventDateTime fechaFinal) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public EventDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(EventDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public EventDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(EventDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String password) {
        this.descripcion = descripcion;
    }
}