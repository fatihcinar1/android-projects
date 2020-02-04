package com.example.user.imdbinterface;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/4/2020.
 */

public class Movie {

    @SerializedName("image")
    private String Image;

    @SerializedName("title")
    private String Title;

    public Movie(String mTitle, String mImage){

        this.Title = mTitle;
        this.Image = mImage;
    }

    public Movie(){

    }

    public String getImage(){
        return this.Image;
    }

    public String getTitle(){
        return this.Title;
    }

    public void setTitle(String newTitle){
        this.Title = newTitle;
        return;
    }

    public void setImage(String newImage){
        this.Image = newImage;
        return;
    }

    @Override
    public String toString(){

        String stringified = this.Title + " " + this.Image + "\n";
        return stringified;

    }

}
