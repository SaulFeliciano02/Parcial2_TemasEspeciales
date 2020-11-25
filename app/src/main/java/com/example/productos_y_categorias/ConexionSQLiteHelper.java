package com.example.productos_y_categorias;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.productos_y_categorias.utilities.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.createCategoryTable);
        db.execSQL(Utilidades.createProductTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categorias");
        db.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(db);
    }
}
