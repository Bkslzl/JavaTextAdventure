package edu.uob.Entities;

import edu.uob.GameEntity;

public class Furniture extends GameEntity {
    public String location;
    public Furniture(String name, String description, String location){
        super(name, description);
        this.location = location;
    }
}
