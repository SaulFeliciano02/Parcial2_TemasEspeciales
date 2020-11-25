package com.example.productos_y_categorias;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TabItem tabProducto = findViewById(R.id.tabProductos);
        TabItem tabCategoria = findViewById(R.id.tabCategorias);
        TabLayout tabs = findViewById(R.id.tabs);
        FrameLayout fragmentoRegistro = findViewById(R.id.fragmentoRegistro);
        Fragment fragmentoProducto = new FragmentoProductos();
        Fragment fragmentoCategoria = new FragmentoCategorias();
        Fragment fragmentoBorrarActualizarProducto = new FragmentoUpdateDelete();
        Fragment fragmentoBorrarCategoria = new FragmentoBorrarCategoria();

        if (getIntent().hasExtra("Update")){
            FragmentManager fragmentManager = getSupportFragmentManager();;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentoRegistro.getId(), fragmentoBorrarActualizarProducto);
            Bundle info = new Bundle();
            info.putString("SelectedProduct", getIntent().getExtras().getString("Selected"));
            info.putInt("Position", getIntent().getExtras().getInt("Position"));
            fragmentoBorrarActualizarProducto.setArguments(info);
            fragmentTransaction.commit();
        }
        else
        {
            FragmentManager fragmentManager = getSupportFragmentManager();;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentoRegistro.getId(), fragmentoProducto);
            fragmentTransaction.commit();
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (getIntent().hasExtra("Update")) {
                    if (tab.getPosition() == 0) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        ;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(fragmentoRegistro.getId(), fragmentoBorrarActualizarProducto);
                        fragmentTransaction.commit();
                    }
                    if (tab.getPosition() == 1) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        ;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(fragmentoRegistro.getId(), fragmentoBorrarCategoria);
                        fragmentTransaction.commit();
                    }
                }
                else {
                    if (tab.getPosition() == 0) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        ;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(fragmentoRegistro.getId(), fragmentoProducto);
                        fragmentTransaction.commit();
                    }
                    if (tab.getPosition() == 1) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        ;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(fragmentoRegistro.getId(), fragmentoCategoria);
                        fragmentTransaction.commit();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }
}