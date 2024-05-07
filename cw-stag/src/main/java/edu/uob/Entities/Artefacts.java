package edu.uob.Entities;

public class Artefacts extends GameEntity {
    public String owner;
    public Artefacts(String name, String description, String location){
        super(name, description, location);
        this.owner = null;
    }
}
