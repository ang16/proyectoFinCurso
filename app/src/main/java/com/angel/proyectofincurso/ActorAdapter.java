package com.angel.proyectofincurso;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.proyectofincurso.Data.RestClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {
    Context context;
    ArrayList<Actor> books;
    int resource;

    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public ActorAdapter(Context context, ArrayList<Actor> books, int resource) {
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
    public ActorAdapter.ActorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Esta línea siempre es igual
        View itemView = LayoutInflater.from(context).inflate(resource, viewGroup, false);

        ActorAdapter.ActorViewHolder bookViewHolder = new ActorAdapter.ActorViewHolder(itemView);

        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActorAdapter.ActorViewHolder bookViewHolder, int i) {
        //Saco el libro de la lista que está en la posición "i"
        Actor book = books.get(i);
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


    public Actor getItemFromView(View view) {
        ActorAdapter.ActorViewHolder viewHolder = (ActorAdapter.ActorViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        return books.get(position);
    }

    public int getPositionFromView(View view) {
        ActorAdapter.ActorViewHolder viewHolder = (ActorAdapter.ActorViewHolder) view.getTag();
        return viewHolder.getAdapterPosition();
    }


    class ActorViewHolder extends RecyclerView.ViewHolder {
        TextView tvPersonaje;
        TextView tvActor;
        ImageView ivActor;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPersonaje = itemView.findViewById(R.id.tvPersonaje);
            tvActor = itemView.findViewById(R.id.tvActor);
            ivActor = itemView.findViewById(R.id.ivActor);
            itemView.setTag(this);
        }

        public void bindBook(Actor book) {
            tvPersonaje.setText(book.getCharacter());
            tvActor.setText(book.getName());
            Glide.with(context).load(RestClient.imageBaseUrl + book.profile_path).into(ivActor);

        }
    }
}
