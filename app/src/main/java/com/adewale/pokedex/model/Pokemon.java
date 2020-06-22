package com.adewale.pokedex.model;

public class Pokemon {
    private String name;
    private int number;
    private String title;

    public Pokemon(String name, int number, String title) {
        this.name = name;
        this.number = number;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }
}
