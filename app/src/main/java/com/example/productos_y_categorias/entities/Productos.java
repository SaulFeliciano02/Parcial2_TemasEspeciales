package com.example.productos_y_categorias.entities;

public class Productos {

    private int id;
    private String nombre;
    private float precio;
    private Categorias categoria;

    public Productos(String nombre, float precio, Categorias categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Productos(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
