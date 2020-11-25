package com.example.productos_y_categorias;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.productos_y_categorias.utilities.Utilidades;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoCategorias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoCategorias extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText txtNombreCategorias;
    private Button btnSalvarCategoria;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoCategorias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoCategorias.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoCategorias newInstance(String param1, String param2) {
        FragmentoCategorias fragment = new FragmentoCategorias();
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
        return inflater.inflate(R.layout.fragment_fragmento_categorias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNombreCategorias = Objects.requireNonNull(getView()).findViewById(R.id.txtNombreCategoria);
        btnSalvarCategoria = getView().findViewById(R.id.btnSalvarCategoria);
        btnSalvarCategoria.setOnClickListener(v -> registrarCategoria());
    }

    public void registrarCategoria(){
        if(!txtNombreCategorias.getText().toString().matches("")){
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "bd_app", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Utilidades.nombreCategoria, txtNombreCategorias.getText().toString());
            long idResultado = db.insert(Utilidades.nombreTablaCategoria, null, values);
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "RESULT: " + idResultado, Toast.LENGTH_SHORT).show();
            txtNombreCategorias.setText("");
        }
        else {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Llenar todos los campos correctamente", Toast.LENGTH_SHORT).show();
        }
    }


}