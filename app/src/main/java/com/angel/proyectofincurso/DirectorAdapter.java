package com.angel.proyectofincurso;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.proyectofincurso.Data.ActorDTO;
import com.angel.proyectofincurso.Data.DirectorDTO;
import com.angel.proyectofincurso.Data.RestClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DirectorAdapter extends RecyclerView.Adapter<DirectorAdapter.DirectorViewHolder> {
    Context context;
    ArrayList<DirectorDTO> books;
    int resource;

    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public DirectorAdapter(Context context, ArrayList<DirectorDTO> books, int resource) {
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
    public DirectorAdapter.DirectorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Esta línea siempre es igual
        View itemView = LayoutInflater.from(context).inflate(resource, viewGroup, false);

        DirectorAdapter.DirectorViewHolder bookViewHolder = new DirectorAdapter.DirectorViewHolder(itemView);

        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorAdapter.DirectorViewHolder bookViewHolder, int i) {
        //Saco el libro de la lista que está en la posición "i"
        DirectorDTO book = books.get(i);
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


    public DirectorDTO getItemFromView(View view) {
        DirectorAdapter.DirectorViewHolder viewHolder = (DirectorAdapter.DirectorViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        return books.get(position);
    }

    public int getPositionFromView(View view) {
        DirectorAdapter.DirectorViewHolder viewHolder = (DirectorAdapter.DirectorViewHolder) view.getTag();
        return viewHolder.getAdapterPosition();
    }


    class DirectorViewHolder extends RecyclerView.ViewHolder {
        TextView tvDirecto;
        ImageView ivDirector;

        public DirectorViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDirecto = itemView.findViewById(R.id.tvDirector);
            ivDirector = itemView.findViewById(R.id.ivDirector);
            itemView.setTag(this);
        }

        public void bindBook(DirectorDTO book) {
            tvDirecto.setText(book.getName());
           // tvActor.setText(book.getName());
            Glide.with(context).load(RestClient.imageBaseUrl + book.getProfile_path()).into(ivDirector);

        }
    }
}
