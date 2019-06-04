package com.angel.proyectofincurso;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.BookViewHolder>{
    Context context;
    ArrayList<Pelicula> books;
    int resource;

    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public PeliculaAdapter(Context context, ArrayList<Pelicula> books, int resource) {
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
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Esta línea siempre es igual
        View itemView = LayoutInflater.from(context).inflate(resource, viewGroup, false);

        BookViewHolder bookViewHolder = new BookViewHolder(itemView);

        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        //Saco el libro de la lista que está en la posición "i"
        Pelicula book = books.get(i);
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


    public Pelicula getItemFromView(View view) {
        BookViewHolder viewHolder = (BookViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        return books.get(position);
    }

    public int getPositionFromView(View view) {
        BookViewHolder viewHolder = (BookViewHolder) view.getTag();
        return viewHolder.getAdapterPosition();
    }


    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePelicula;
        ImageView ivPelicula;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombrePelicula = itemView.findViewById(R.id.tvNombrePelicula);
            ivPelicula = itemView.findViewById(R.id.ivPelicula);
            itemView.setTag(this);
        }

        public void bindBook(Pelicula book) {
            tvNombrePelicula.setText(book.getTittulo());
            Glide.with(context).load(book.getPortada()).into(ivPelicula);

        }
    }
}