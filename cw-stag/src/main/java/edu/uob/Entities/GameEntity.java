package edu.uob.Entities;

import java.util.ArrayList;

public abstract class GameEntity
{
    private String name;
    private String description;
    private String location;

    public GameEntity(String name, String description, String location)
    {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
    public String getLocation()
    {
        return location;
    }

    public void changeLocation(String newLocation){
        this.location = newLocation;
    }
}
