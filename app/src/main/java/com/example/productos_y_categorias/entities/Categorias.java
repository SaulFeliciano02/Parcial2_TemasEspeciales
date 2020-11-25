package com.example.productos_y_categorias.entities;

public class Categorias {

    private String nombre;
    private int id;

    public Categorias(String nombre) {
        this.nombre = nombre;
    }

    public Categorias() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
