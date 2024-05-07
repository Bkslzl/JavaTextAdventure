package edu.uob.Entities;

import java.util.ArrayList;

public class NewEntities extends GameEntity {
    public static ArrayList<GameEntity> newEntitiesList = new ArrayList<>();

    public NewEntities(String name, String description) {
        super(name, description);
    }
}
