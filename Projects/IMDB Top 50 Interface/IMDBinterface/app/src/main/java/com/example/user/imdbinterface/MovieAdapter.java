package com.example.user.imdbinterface;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

/**
 * Created by user on 2/4/2020.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    LayoutInflater layoutInflater;

    ArrayList<Movie> theMovies;

    Context theContext;

    ItemClickListener theListener;

    public interface ItemClickListener
    {
        void onItemClick(int position);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        private ImageView ImageViewCover;
        private TextView TextViewTitle;

        public MovieViewHolder(View theView, final ItemClickListener theListener){

            super(theView);

            this.ImageViewCover = theView.findViewById(R.id.Image);
            this.TextViewTitle = theView.findViewById(R.id.Title);

            // SET ON-CLICK LISTENERS

            theView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(theListener != null){
                        // get the position of the recycler view
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            theListener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }

    public void setItemClickListener(ItemClickListener aListener){
        this.theListener = aListener;
    }

    public MovieAdapter(ArrayList<Movie> mMovies, Context mContext){

        this.theMovies = mMovies;

        this.theContext = mContext;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View theMovieRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent , false);

        MovieViewHolder theMovieViewHolder = new MovieViewHolder(theMovieRow, this.theListener);


        return theMovieViewHolder;

    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position){

        Movie theMovie = theMovies.get(position);

        viewHolder.TextViewTitle.setText(theMovie.getTitle());

        String theURL = theMovie.getImage();

        Picasso.with(theContext)
                .load(theURL)
                .into(viewHolder.ImageViewCover);
        // LOAD IMAGE FROM A URL


    }

    @Override
    public int getItemCount(){
        return theMovies.size();
    }

}
