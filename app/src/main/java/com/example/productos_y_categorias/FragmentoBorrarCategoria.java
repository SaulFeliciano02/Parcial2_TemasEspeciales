package com.example.productos_y_categorias;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.productos_y_categorias.entities.Categorias;
import com.example.productos_y_categorias.utilities.Utilidades;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoBorrarCategoria#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoBorrarCategoria extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spnBorrarCategoria;
    private Button btnBorrarCategoria;
    private ConexionSQLiteHelper conn;
    private ArrayAdapter adapter;
    private ArrayList<Categorias> categoriaList;
    private ArrayList<String> nombresCategorias;
    private Categorias categorias;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoBorrarCategoria() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoBorrarCategoria.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoBorrarCategoria newInstance(String param1, String param2) {
        FragmentoBorrarCategoria fragment = new FragmentoBorrarCategoria();
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
        return inflater.inflate(R.layout.fragment_fragmento_borrar_categoria, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
        spnBorrarCategoria = Objects.requireNonNull(getView()).findViewById(R.id.spnBorrarCategoria);
        btnBorrarCategoria = getView().findViewById(R.id.btnBorrarCategoria);
        btnBorrarCategoria.setOnClickListener(v -> eliminarCategoria());

        rellenarSpinner();

        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, nombresCategorias);

        spnBorrarCategoria.setAdapter(adapter);
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

    private void eliminarCategoria() {
        if(spnBorrarCategoria.getSelectedItemPosition() != 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Desea borrar esta categoria?")
                    .setPositiveButton("Si", (dialog, id) -> {
                        String[] arreglohelper = new String[1];
                        arreglohelper[0] = spnBorrarCategoria.getSelectedItem().toString().substring(0, spnBorrarCategoria.getSelectedItem().toString().indexOf("."));
                        conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.nombreTablaProductos+ " WHERE " +Utilidades.foreignCategoria+ " =?", arreglohelper);
                        if(!cursor.moveToNext())
                        {
                            SQLiteDatabase db2 = conn.getWritableDatabase();
                            long idResultado = db2.delete(Utilidades.nombreTablaCategoria, " id =? ", arreglohelper);
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "DELETE: " + idResultado, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "No puede ser eliminado porque posee producto asociado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "CANCELADO", Toast.LENGTH_SHORT).show();
                    });
            builder.show();
        }
        else {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Seleccione categoria valida", Toast.LENGTH_SHORT).show();
        }
    }
}