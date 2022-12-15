package com.example.habitmanager.data.category.model;

import java.util.Objects;

public class Category implements Comparable<Category>{
    private int id;
    private String name;
    private int picture;

    public Category(){

    }

    public Category(int id, String name, int picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Category category) {
        if(this.getId() < category.getId()){
            return -1;
        }else if(this.getId() > category.getId()){
            return 1;
        }
        return 0;
    }
}
