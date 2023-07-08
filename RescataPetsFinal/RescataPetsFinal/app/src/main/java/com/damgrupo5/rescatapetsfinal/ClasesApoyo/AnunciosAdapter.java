package com.damgrupo5.rescatapetsfinal.ClasesApoyo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damgrupo5.rescatapetsfinal.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AnunciosAdapter extends RecyclerView.Adapter<AnunciosAdapter.MyViewHolder>{

    Context context;
    ArrayList<Anuncios> anunciosArrayList;

    public AnunciosAdapter(Context context, ArrayList<Anuncios> anunciosArrayList ) {
        this.context =  context;
        this.anunciosArrayList = anunciosArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.lista_anuncios, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Anuncios anuncios = anunciosArrayList.get(position);
        holder.tituloAnuncio.setText(anuncios.getDescTipoAnun());
        Log.e("jose", anuncios.getDescTipoAnun());
        holder.nomMascota.setText(anuncios.getNomMascota());
        holder.shImagen.setImageResource(anuncios.imagenAnuncio);
    }

    @Override
    public int getItemCount() {
        return anunciosArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ////hasta aqui he llegado con este video https://www.youtube.com/watch?v=UBgXVGgTaHk

        ShapeableImageView shImagen;
        TextView tituloAnuncio;
        TextView nomMascota;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            shImagen = itemView.findViewById(R.id.shImagen);
            tituloAnuncio = itemView.findViewById(R.id.txtTituloAnuncio);
            nomMascota = itemView.findViewById(R.id.txtNomMascotaAnun);

        }


    }


}
