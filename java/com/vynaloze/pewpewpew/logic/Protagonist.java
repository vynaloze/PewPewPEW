package com.vynaloze.pewpewpew.logic;


public class Protagonist {
    private final int imageID;
    private final String name;
    private final String description;

    public Protagonist(int imageID, String name, String description) {
        this.imageID = imageID;
        this.name = name;
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
