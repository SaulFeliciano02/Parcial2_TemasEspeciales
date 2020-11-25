package com.example.productos_y_categorias;

import android.content.ContentValues;
import android.content.Intent;
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
 * Use the {@link FragmentoProductos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoProductos extends Fragment {

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
    private Button btnSalvarProducto;
    private Productos producto;
    private Categorias categoria;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoProductos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoProductos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoProductos newInstance(String param1, String param2) {
        FragmentoProductos fragment = new FragmentoProductos();
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
        return inflater.inflate(R.layout.fragment_fragmento_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
        spnCategorias = Objects.requireNonNull(getView()).findViewById(R.id.spnCategorias);
        txtNombre = getView().findViewById(R.id.txtNombre);
        txtPrecio = getView().findViewById(R.id.txtPrecio);
        btnSalvarProducto = getView().findViewById(R.id.btnSalvarProducto);
        btnSalvarProducto.setOnClickListener(v -> registrarProducto());
        rellenarSpinner();

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, nombresCategorias);

        spnCategorias.setAdapter(adapter);
    }

    private void registrarProducto() {
        if(!txtNombre.getText().toString().matches("") && !txtPrecio.getText().toString().matches("") && spnCategorias.getSelectedItemPosition() != 0)
        {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Utilidades.nombreProductos, txtNombre.getText().toString());
            values.put(Utilidades.precioProducto, Float.parseFloat(txtPrecio.getText().toString()));
            values.put(Utilidades.foreignCategoria, Integer.parseInt(spnCategorias.getSelectedItem().toString().substring(0, spnCategorias.getSelectedItem().toString().indexOf("."))));
            long idResultado = db.insert(Utilidades.nombreTablaProductos, null, values);
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "RESULT: " + idResultado, Toast.LENGTH_SHORT).show();
            producto = new Productos();
            categoria = new Categorias();
            producto.setId(Integer.parseInt(Long.toString(idResultado)));
            producto.setNombre(txtNombre.getText().toString());
            producto.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
            categoria.setNombre(spnCategorias.getSelectedItem().toString().substring(spnCategorias.getSelectedItem().toString().indexOf(". ") + 2));
            producto.setCategoria(categoria);
            txtNombre.setText("");
            txtPrecio.setText("");
            spnCategorias.setSelection(0);
            MainActivity.insertarEnElRecyclerView(producto);
        }
        else {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Llenar todos los campos correctamente", Toast.LENGTH_SHORT).show();
        }
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
}