package com.example.productos_y_categorias.utilities;

public class Utilidades {

    //Constantes tabla categoria
    public static final String nombreTablaCategoria = "categorias";
    public static final String nombreCategoria = "nombre";
    public static final String idCategoria = "id";

    //Constantes tabla productos
    public static final String nombreTablaProductos = "productos";
    public static final String nombreProductos = "nombre";
    public static final String idProducto = "id";
    public static final String precioProducto = "precio";
    public static final String foreignCategoria = "idCategoria";

    public static final String createCategoryTable = "CREATE TABLE "+nombreTablaCategoria+" ("+idCategoria+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+nombreCategoria+" TEXT)";
    public static final String createProductTable = "CREATE TABLE "+nombreTablaProductos+" ("+idProducto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+nombreProductos+" TEXT, "+precioProducto+" REAL, "+foreignCategoria+" INTEGER, FOREIGN KEY ("+foreignCategoria+") REFERENCES "+nombreTablaCategoria+" (id))";

}
