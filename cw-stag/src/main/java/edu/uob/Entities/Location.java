package edu.uob.Entities;

import com.alexmerz.graphviz.objects.Edge;
import edu.uob.GameEntity;

import java.util.ArrayList;

public class Location extends GameEntity {
    public static ArrayList<Location> locationList = new ArrayList<>();
    public static ArrayList<String> theMap = new ArrayList<>();

    public static ArrayList<Artefacts> artefactsList = new ArrayList<>();
    public static ArrayList<Characters> characterList = new ArrayList<>();
    public static ArrayList<Furniture> furnitureList = new ArrayList<>();
    public Location(String name, String description){
        super(name, description);
    }
}
