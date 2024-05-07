package edu.uob.Entities;

import java.util.ArrayList;

public class Location extends GameEntity {
    public static ArrayList<Location> locationList = new ArrayList<>();
    public static ArrayList<String> theMap = new ArrayList<>();

    public static ArrayList<Artefacts> artefactsList = new ArrayList<>();
    public static ArrayList<Characters> characterList = new ArrayList<>();
    public static ArrayList<Furniture> furnitureList = new ArrayList<>();
    public Location(String name, String description, String location){
        super(name, description, location);
    }
}
