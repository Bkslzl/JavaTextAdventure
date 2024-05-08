package edu.uob.Tools;

import edu.uob.Entities.*;

public class Findings {
    public static GameEntity accordingToNameFindItem(String item){
        for (Artefacts artefact : Location.artefactsList) {
            if (artefact.getName().equalsIgnoreCase(item)) {
                return artefact;
            }
        }
        for (Characters character : Location.characterList) {
            if (character.getName().equalsIgnoreCase(item)) {
                return character;
            }
        }
        for (Furniture furniture : Location.furnitureList) {
            if (furniture.getName().equalsIgnoreCase(item)) {
                return furniture;
            }
        }
        for (Location theLocation : Location.locationList) {
            if (theLocation.getName().equalsIgnoreCase(item)) {
                return theLocation;
            }
        }
        return null;
    }
}
