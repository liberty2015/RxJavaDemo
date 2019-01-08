package com.behavior.liberty.rxjavademo.bean;

import java.util.List;

/**
 * Created by liberty on 2019/1/8.
 */

public class CookMenu {

    private String id;
    private String title;
    private String tags;
    private String imtro;
    private String ingredients;
    private String burden;
    private List<String > albums;
    private List<Steps> steps;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTags() {
        return tags;
    }

    public String getImtro() {
        return imtro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getBurden() {
        return burden;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }
}
