package com.angel.proyectofincurso;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.proyectofincurso.Data.PeliculaDTO;
import com.angel.proyectofincurso.Data.RestClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PeliculaActorAdapter extends RecyclerView.Adapter<PeliculaActorAdapter.PeliculaActorViewHolder>{
    Context context;
    ArrayList<PeliculaDTO> books;
    int resource;

    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public PeliculaActorAdapter(Context context, ArrayList<PeliculaDTO> books, int resource) {
        this.context = context;
        this.books = books;
        this.resource = resource;
    }

    public void setLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public PeliculaActorAdapter.PeliculaActorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Esta línea siempre es igual
        View itemView = LayoutInflater.from(context).inflate(resource, viewGroup, false);

        PeliculaActorAdapter.PeliculaActorViewHolder bookViewHolder = new PeliculaActorAdapter.PeliculaActorViewHolder(itemView);

        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaActorAdapter.PeliculaActorViewHolder bookViewHolder, int i) {
        //Saco el libro de la lista que está en la posición "i"
        PeliculaDTO book = books.get(i);
        //lo uso para rellenar el viewholder
        bookViewHolder.bindBook(book);

        if (clickListener != null) {
            bookViewHolder.itemView.setOnClickListener(clickListener);
        }

        if (longClickListener != null) {
            bookViewHolder.itemView.setOnLongClickListener(longClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public PeliculaDTO getItemFromView(View view) {
        PeliculaActorAdapter.PeliculaActorViewHolder viewHolder = (PeliculaActorAdapter.PeliculaActorViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        return books.get(position);
    }

    public int getPositionFromView(View view) {
        PeliculaActorAdapter.PeliculaActorViewHolder viewHolder = (PeliculaActorAdapter.PeliculaActorViewHolder) view.getTag();
        return viewHolder.getAdapterPosition();
    }


    class PeliculaActorViewHolder extends RecyclerView.ViewHolder {
        TextView tvPeliculaActoNombre;
        TextView PeliculaActorNombrePersonaje;
        ImageView ivPeliculaActor;

        public PeliculaActorViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPeliculaActoNombre = itemView.findViewById(R.id.tvPeliculaActoNombre);
            ivPeliculaActor = itemView.findViewById(R.id.ivPeliculaActor);
            PeliculaActorNombrePersonaje = itemView.findViewById(R.id.PeliculaActorNombrePersonaje);
            itemView.setTag(this);
        }

        public void bindBook(PeliculaDTO book) {
            System.out.println("El nombre de la pelicula es "+ book.getTitle());
            tvPeliculaActoNombre.setText(book.getTitle());
            PeliculaActorNombrePersonaje.setText(book.getCharacter());
            Glide.with(context).load(RestClient.imageBaseUrl+book.getPoster_path()).into(ivPeliculaActor);

        }
    }
}
