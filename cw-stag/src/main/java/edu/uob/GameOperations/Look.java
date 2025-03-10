package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;
import edu.uob.Players;

public class Look {
    public static void printAllThingsInCurrentLocation(Players player){
        for(Location location : Location.locationList){
            if(location.getName().equalsIgnoreCase(player.currentLocation)){
                System.out.println("You are in location: " + location.getName() + "  Description: " + location.getDescription());
            }
        }

        for(Artefacts artefact : Location.artefactsList){
            if(artefact.getLocation().equalsIgnoreCase(player.currentLocation)){
                System.out.println("Artefact: " + artefact.getName() + "  Description: " + artefact.getDescription());
            }
        }
        for(Characters character : Location.characterList){
            if(character.getLocation().equalsIgnoreCase(player.currentLocation)){
                System.out.println("Character: " + character.getName() + "  Description: " + character.getDescription());
            }
        }
        for(Furniture furniture : Location.furnitureList){
            if(furniture.getLocation().equalsIgnoreCase(player.currentLocation)){
                System.out.println("Furniture: " + furniture.getName() + "  Description: " + furniture.getDescription());
            }
        }
        for(Players otherPlayer : Players.playersList){
            if((!otherPlayer.name.equalsIgnoreCase(player.name)) &&
                    otherPlayer.currentLocation.equalsIgnoreCase(player.currentLocation)){
                System.out.println("Other player: " + otherPlayer.name);
            }
        }
        System.out.println("Here are the paths:");
        int i =0;
        for(String map : Location.theMap) {
            String[] twoLocations = map.split("\\s+");
            if(twoLocations[0].equalsIgnoreCase(player.currentLocation)){
                System.out.println("There is a way to: " + twoLocations[1]);
                i++;
            }
        }
        if(i == 0){
            System.out.println("There is no path.");
        }
    }
}
