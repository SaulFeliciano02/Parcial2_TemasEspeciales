package com.example.productos_y_categorias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomRecycleAdapter extends RecyclerView.Adapter<CustomRecycleAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> datosProductos;
    private ClickOnRowListener clickOnRowListener;

    CustomRecycleAdapter(Context context, ArrayList<String> datosProductos, ClickOnRowListener clickOnRowListener)
    {
        this.context = context;
        this.datosProductos = datosProductos;
        this.clickOnRowListener = clickOnRowListener;
    }

    @NonNull
    @NotNull
    @Override
    public CustomRecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view, clickOnRowListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomRecycleAdapter.MyViewHolder holder, int position) {
        holder.infoProducto.setText(String.valueOf(datosProductos.get(position)));
    }

    @Override
    public int getItemCount() {
        return datosProductos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView infoProducto;
        ClickOnRowListener clickOnRowListener;

        public MyViewHolder(@NonNull @NotNull View itemView, ClickOnRowListener clickOnRowListener) {
            super(itemView);
            infoProducto = itemView.findViewById(R.id.txtInfoProducto);
            this.clickOnRowListener = clickOnRowListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickOnRowListener.ClickOnRow(getAdapterPosition());
        }
    }

    public interface ClickOnRowListener {
        void ClickOnRow(int position);
    }
}
