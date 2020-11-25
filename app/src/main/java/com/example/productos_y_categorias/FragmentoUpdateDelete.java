package com.example.productos_y_categorias;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.productos_y_categorias.entities.Categorias;
import com.example.productos_y_categorias.entities.Productos;
import com.example.productos_y_categorias.utilities.Utilidades;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoUpdateDelete#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoUpdateDelete extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spnCategorias;
    private ConexionSQLiteHelper conn;
    private ArrayList<Categorias> categoriaList;
    private ArrayList<String> nombresCategorias;
    private EditText txtNombre;
    private EditText txtPrecio;
    private Button btnActualizar;
    private Button btnBorrar;
    private String nombreProducto;
    private String precioProducto;
    private String categoriaProducto;
    private String todaLaInfo;
    private ArrayAdapter adapter;
    private int posicion;
    private Productos producto;
    private Categorias categoria;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoUpdateDelete() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoUpdateDelete.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoUpdateDelete newInstance(String param1, String param2) {
        FragmentoUpdateDelete fragment = new FragmentoUpdateDelete();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        assert args != null;
        todaLaInfo = args.getString("SelectedProduct");
        posicion = args.getInt("Position");
        return inflater.inflate(R.layout.fragment_fragmento_update_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
        spnCategorias = Objects.requireNonNull(getView()).findViewById(R.id.spnCategorias);
        txtNombre = getView().findViewById(R.id.txtNombre);
        txtPrecio = getView().findViewById(R.id.txtPrecio);
        btnActualizar = getView().findViewById(R.id.btnActualizar);
        btnBorrar = getView().findViewById(R.id.btnBorrar);
        btnActualizar.setOnClickListener(v -> actualizarProducto());
        btnBorrar.setOnClickListener(v -> borrarProducto());

        rellenarSpinner();

        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, nombresCategorias);

        spnCategorias.setAdapter(adapter);

        rellenarTodosLosCampos();
    }

    private void rellenarSpinner() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Categorias categoria = null;
        categoriaList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.nombreTablaCategoria, null);

        while (cursor.moveToNext())
        {
            categoria = new Categorias();
            categoria.setId(cursor.getInt(0));
            categoria.setNombre(cursor.getString(1));
            categoriaList.add(categoria);
        }

        obtenerListaNombresCategorias();
    }

    private void obtenerListaNombresCategorias() {
        nombresCategorias = new ArrayList<>();
        nombresCategorias.add("Seleccione");

        for (Categorias categorias : categoriaList) {
            nombresCategorias.add(categorias.getId() +". "+ categorias.getNombre());
        }
    }

    private void rellenarTodosLosCampos(){
        nombreProducto = todaLaInfo.substring(todaLaInfo.indexOf("Nombre: ") + 8, todaLaInfo.indexOf(", Precio: "));
        txtNombre.setText(nombreProducto);

        precioProducto = todaLaInfo.substring(todaLaInfo.indexOf("Precio: ") + 8, todaLaInfo.indexOf(", Categoria: "));
        txtPrecio.setText(precioProducto);

        categoriaProducto = todaLaInfo.substring(todaLaInfo.indexOf("Categoria: ") + 11);
        for (Categorias categoria: categoriaList
             ) {
            if (categoria.getNombre().equalsIgnoreCase(categoriaProducto))
            {
                int position = adapter.getPosition(categoria.getId() +". "+ categoria.getNombre());
                spnCategorias.setSelection(position);
            }
        }
    }

    private void actualizarProducto(){
        if(!txtNombre.getText().toString().matches("") && !txtPrecio.getText().toString().matches("") && spnCategorias.getSelectedItemPosition() != 0) {
            conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Utilidades.nombreProductos, txtNombre.getText().toString());
            values.put(Utilidades.precioProducto, Float.parseFloat(txtPrecio.getText().toString()));
            values.put(Utilidades.foreignCategoria, Integer.parseInt(spnCategorias.getSelectedItem().toString().substring(0, spnCategorias.getSelectedItem().toString().indexOf("."))));
            String[] arreglohelper = new String[1];
            arreglohelper[0] = todaLaInfo.substring(0, todaLaInfo.indexOf("."));
            long idResultado = db.update(Utilidades.nombreTablaProductos, values, " id =? ", arreglohelper);
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "UPDATE: " + idResultado, Toast.LENGTH_SHORT).show();
            producto = new Productos();
            categoria = new Categorias();
            producto.setId(Integer.parseInt(arreglohelper[0]));
            producto.setNombre(txtNombre.getText().toString());
            producto.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
            categoria.setNombre(spnCategorias.getSelectedItem().toString().substring(spnCategorias.getSelectedItem().toString().indexOf(". ") + 2));
            producto.setCategoria(categoria);
            System.out.println("la posicion del otro lado es: " + posicion);
            MainActivity.actualizarEnElRecyclerView(posicion, producto);
        }
        else {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Llenar todos los campos correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void borrarProducto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Desea borrar este producto?")
                .setPositiveButton("Si", (dialog, id) -> {
                    conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] arreglohelper = new String[1];
                    arreglohelper[0] = todaLaInfo.substring(0, todaLaInfo.indexOf("."));
                    long idResultado = db.delete(Utilidades.nombreTablaProductos, " id =? ", arreglohelper);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "DELETE: " + idResultado, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "CANCELADO", Toast.LENGTH_SHORT).show();
                });
        builder.show();
        MainActivity.borrarEnElRecyclerView(posicion);
    }
}