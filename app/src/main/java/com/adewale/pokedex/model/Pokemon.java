package com.adewale.pokedex.model;

public class Pokemon {
    private String name;
    private String url;
    private String title;

    public Pokemon(String name, String url, String title) {
        this.name = name;
        this.url = url;
        this.title = title;
    }

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
