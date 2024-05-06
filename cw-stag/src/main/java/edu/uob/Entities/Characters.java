package edu.uob.Entities;

import edu.uob.GameEntity;

public class Characters extends GameEntity {
    public String location;
    public Characters(String name, String description, String location){
        super(name, description);
        this.location = location;
    }
}
