package com.kelompok8.Bookuku.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kelompok8.Bookuku.DetailPost;
import com.kelompok8.Bookuku.R;
import com.kelompok8.Bookuku.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<Post> postList;

    public PostAdapter(Context context, ArrayList<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mUsername;
        public TextView mKontak;
        public ImageView mImagePost;
        public TextView mStatus;
        public TextView mJudul;
        public TextView mGenre;
        public TextView mDeskripsi;

        public CardView cardViewPost;

        public ViewHolder(View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.tv_username);
            mKontak = itemView.findViewById(R.id.tv_kontak);
            mImagePost=itemView.findViewById(R.id.img_post);
            mStatus = itemView.findViewById(R.id.statusBuku);
            mJudul = itemView.findViewById(R.id.tv_judul);
            mGenre = itemView.findViewById(R.id.tv_genre);
            mDeskripsi = itemView.findViewById(R.id.tv_deskripsi);

            cardViewPost= itemView.findViewById(R.id.cardViewPost);

        }
    }
    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Post post= postList.get(position);

        holder.mUsername.setText(post.getUsername());

        holder.mKontak.setText(post.getKontak());

        Glide.with(context)
                .load(post.getImagePost())
                .into(holder.mImagePost);

        holder.mStatus.setText(post.getStatus());

        holder.mJudul.setText(post.getJudul());

        holder.mGenre.setText(post.getGenre());

        holder.mDeskripsi.setText(post.getDeskripsi());

        holder.cardViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPost.class);
                intent.putExtra("id",post.getId());
                intent.putExtra("Username",post.getUsername());
                intent.putExtra("Kontak",post.getKontak());
                intent.putExtra("image",post.getImagePost());
                intent.putExtra("Judul",post.getJudul());
                intent.putExtra("Genre",post.getGenre());
                intent.putExtra("Status",post.getStatus());
                intent.putExtra("Deskripsi",post.getDeskripsi());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

}
