package edu.uob.Entities;

import edu.uob.GameEntity;

public class Artefacts extends GameEntity {
    public String location;
    public Artefacts(String name, String description, String location){
        super(name, description);
        this.location = location;
    }
}
