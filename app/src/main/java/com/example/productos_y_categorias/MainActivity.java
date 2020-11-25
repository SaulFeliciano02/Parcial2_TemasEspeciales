package com.example.productos_y_categorias;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.productos_y_categorias.entities.Categorias;
import com.example.productos_y_categorias.entities.Productos;
import com.example.productos_y_categorias.utilities.Utilidades;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomRecycleAdapter.ClickOnRowListener {

    private static RecyclerView listaProductos;
    private ArrayList<Productos> arregloProductos;
    private static ArrayList<String> nombresDeProductos;
    private ConexionSQLiteHelper conn;
    private static CustomRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_main);
        conn = new ConexionSQLiteHelper(this, "bd_app", null, 1);

        listaProductos = findViewById(R.id.listaProductos);
        rellenarLista();

        adapter = new CustomRecycleAdapter(this, nombresDeProductos, this);
        listaProductos.setAdapter(adapter);
        listaProductos.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent activity2Intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(activity2Intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void rellenarLista() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Productos producto = null;
        Categorias categoria = null;
        arregloProductos = new ArrayList<>();
        String[] parametros = new String[1];

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.nombreTablaProductos, null);

        while (cursor.moveToNext())
        {
            producto = new Productos();
            producto.setId(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setPrecio(cursor.getFloat(2));
            parametros[0] = cursor.getString(3);
            Cursor cursor2 = db.rawQuery("SELECT * FROM " + Utilidades.nombreTablaCategoria + " WHERE " +Utilidades.idCategoria+ " = ?", parametros);
            categoria = new Categorias();
            while (cursor2.moveToNext())
            {
                categoria.setNombre(cursor2.getString(1));
            }
            producto.setCategoria(categoria);
            cursor2.close();
            arregloProductos.add(producto);
        }

        obtenerListaNombresProductos();
    }

    private void obtenerListaNombresProductos() {
        nombresDeProductos = new ArrayList<>();

        for (Productos producto : arregloProductos) {
            nombresDeProductos.add(producto.getId() +". Nombre: "+ producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Categoria: " + producto.getCategoria().getNombre());
        }
    }

    @Override
    public void ClickOnRow(int position) {
        Intent intent = new Intent(this, RegisterActivity.class);
        System.out.println("la posicion es: " + position);
        intent.putExtra("Update", true);
        intent.putExtra("Selected", nombresDeProductos.get(position));
        intent.putExtra("Position", position);
        startActivity(intent);
    }

    public static void borrarEnElRecyclerView(int position) {
        nombresDeProductos.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public static void actualizarEnElRecyclerView(int position, Productos producto) {
        nombresDeProductos.set(position, producto.getId() +". Nombre: "+ producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Categoria: " + producto.getCategoria().getNombre());
        adapter.notifyDataSetChanged();
    }

    public static void insertarEnElRecyclerView(Productos producto) {
        nombresDeProductos.add(producto.getId() +". Nombre: "+ producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Categoria: " + producto.getCategoria().getNombre());
        adapter.notifyItemInserted(nombresDeProductos.size());
    }
}