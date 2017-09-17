package com.example.neha.hairapp;

/**
 * Created by Neha on 5/15/2017.
 */

public class HairStyle{

    String name;
    int imageResource;
    boolean favorite;
    int tag;

    public HairStyle(String name, int imageResource, int tag){
        this.name=name;
        this.imageResource=imageResource;
        favorite=false;
        this.tag=tag;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return imageResource;
    }

    public boolean getFavorite() { return favorite; }

    public void changeFavorite(){
        if (favorite){
            favorite=false;
        } else favorite=true;
    }

    public boolean isFavorite(){
        return favorite;
    }

    public int getTag(){
        return tag;
    }

}
